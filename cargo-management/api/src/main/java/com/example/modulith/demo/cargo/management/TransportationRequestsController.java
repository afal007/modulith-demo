package com.example.modulith.demo.cargo.management;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.stream.Collectors;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import com.example.modulith.demo.cargo.management.api.model.CargoDTO;
import com.example.modulith.demo.cargo.management.api.model.CoordinatesDTO;
import com.example.modulith.demo.cargo.management.api.model.RouteDTO;
import com.example.modulith.demo.cargo.management.api.model.TemperatureConditionsDTO;
import com.example.modulith.demo.cargo.management.api.model.TrailerRequirementsDTO;
import com.example.modulith.demo.cargo.management.api.model.TransportationRequestDTO;
import com.example.modulith.demo.cargo.management.api.model.WaypointDTO;
import com.example.modulith.demo.cargo.management.api.spring.web.TransportationRequestV1Api;
import com.example.modulith.demo.cargo.management.application.domain.transportation.request.Cargo;
import com.example.modulith.demo.cargo.management.application.domain.transportation.request.Coordinates;
import com.example.modulith.demo.cargo.management.application.domain.transportation.request.Freight;
import com.example.modulith.demo.cargo.management.application.domain.transportation.request.Route;
import com.example.modulith.demo.cargo.management.application.domain.transportation.request.TemperatureConditions;
import com.example.modulith.demo.cargo.management.application.domain.transportation.request.TrailerRequirements;
import com.example.modulith.demo.cargo.management.application.domain.transportation.request.TransportationRequest;
import com.example.modulith.demo.cargo.management.application.domain.transportation.request.Waypoint;
import com.example.modulith.demo.cargo.management.application.infrastructure.TransportationRequestEntity;
import com.example.modulith.demo.cargo.management.application.usecase.command.createrequest.CreateTransportationRequestCommand;
import com.example.modulith.demo.cargo.management.application.usecase.query.getrequest.FindTransportationRequestByIdQuery;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TransportationRequestsController implements TransportationRequestV1Api {

    private final QueryGateway queryGateway;
    private final CommandGateway commandGateway;

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<TransportationRequestDTO> getV1TransportationRequestsId(Long id) {
        return ResponseEntity.of(queryGateway.query(new FindTransportationRequestByIdQuery(id),
            ResponseTypes.optionalInstanceOf(TransportationRequestEntity.class)
        ).join().map(e -> {
            TransportationRequestDTO dto = new TransportationRequestDTO();
            CargoDTO cargoDTO = new CargoDTO();
            cargoDTO.setCargoType(e.getCargoType());
            cargoDTO.setCost(e.getCargoCost().toString());
            cargoDTO.setVolume(e.getCargoVolume());
            cargoDTO.setWeight(e.getCargoWeight());
            dto.setCargo(cargoDTO);

            dto.setTransportationType(
                TransportationRequestDTO.TransportationTypeEnum.fromValue(e.getTransportationType()));

            dto.setFreight(e.getFreight().toString());

            TrailerRequirementsDTO trailerRequirementsDTO = new TrailerRequirementsDTO();
            trailerRequirementsDTO.setType(TrailerRequirementsDTO.TypeEnum.fromValue(e.getTrailerType()));
            trailerRequirementsDTO.setLoadingType(
                TrailerRequirementsDTO.LoadingTypeEnum.fromValue(e.getTrailerLoadingType()));
            trailerRequirementsDTO.setVolume(e.getTrailerVolume());
            trailerRequirementsDTO.setLoadCapacity(e.getTrailerLoadCapacity());

            TemperatureConditionsDTO temperatureConditionsDTO = new TemperatureConditionsDTO();
            temperatureConditionsDTO.setTemperatureTo(e.getTrailerTemperatureTo());
            temperatureConditionsDTO.setTemperatureFrom(e.getTrailerTemperatureFrom());
            trailerRequirementsDTO.setTemperatureConditions(temperatureConditionsDTO);

            dto.setTrailerRequirements(trailerRequirementsDTO);

            RouteDTO routeDTO = new RouteDTO();
            routeDTO.setWaypointList(e.getWaypointList().stream().map(w -> {
                WaypointDTO waypointDTO = new WaypointDTO();
                waypointDTO.setType(WaypointDTO.TypeEnum.fromValue(w.getType()));
                waypointDTO.setAddress(w.getAddress());

                if (w.getLatitude() != null) {
                    CoordinatesDTO coordinates = new CoordinatesDTO();
                    coordinates.setLatitude(w.getLatitude().toString());
                    coordinates.setLongitude(w.getLongitude().toString());
                    waypointDTO.setCoordinates(coordinates);
                }

                waypointDTO.setOrganizationId(w.getOrganizationId());
                waypointDTO.setDateTimeStart(w.getDateTimeStart().toString());
                waypointDTO.setDateTimeEnd(w.getDateTimeEnd().toString());

                return waypointDTO;
            }).toList());
            dto.setRoute(routeDTO);

            dto.setComment(e.getComment());
            dto.setSourceNumber(e.getSourceNumber());
            return dto;
        }));
    }

    @Override
    public ResponseEntity<Void> postV1TransportationRequests(TransportationRequestDTO transportationRequestDTO) {
        commandGateway.sendAndWait(new CreateTransportationRequestCommand(map(transportationRequestDTO)));
        return ResponseEntity.ok().build();
    }

    private TransportationRequest map(TransportationRequestDTO dto) {
        CargoDTO cargoDTO = dto.getCargo();
        TrailerRequirementsDTO trailerRequirementsDTO = dto.getTrailerRequirements();
        TemperatureConditionsDTO temperatureConditionsDTO = trailerRequirementsDTO.getTemperatureConditions();
        return TransportationRequest.startFilling()
            .transportationType(
                TransportationRequest.TransportationTypeEnum.valueOf(dto.getTransportationType().name()))
            .comment(dto.getComment())
            .freight(new Freight(new BigDecimal(dto.getFreight())))
            .sourceNumber(dto.getSourceNumber())
            .cargo(new Cargo(cargoDTO.getCargoType(), new BigDecimal(cargoDTO.getCost()), cargoDTO.getVolume(),
                cargoDTO.getWeight()
            ))
            .trailerRequirements(
                new TrailerRequirements(trailerRequirementsDTO.getVolume(), trailerRequirementsDTO.getLoadCapacity(),
                    TrailerRequirements.TypeEnum.valueOf(trailerRequirementsDTO.getType().name()),
                    TrailerRequirements.LoadingTypeEnum.valueOf(trailerRequirementsDTO.getLoadingType().name()),
                    temperatureConditionsDTO != null ? new TemperatureConditions(
                        temperatureConditionsDTO.getTemperatureFrom(), temperatureConditionsDTO.getTemperatureTo())
                        : null
                ))
            .route(new Route(dto.getRoute()
                .getWaypointList()
                .stream()
                .map(w -> new Waypoint(Waypoint.TypeEnum.valueOf(w.getType().name()), w.getAddress(),
                    w.getCoordinates() != null ? new Coordinates(Double.parseDouble(w.getCoordinates().getLongitude()),
                        Double.parseDouble(w.getCoordinates().getLatitude())
                    ) : null, w.getOrganizationId(), OffsetDateTime.parse(w.getDateTimeStart()),
                    OffsetDateTime.parse(w.getDateTimeEnd())
                ))
                .collect(Collectors.toList())))
            .fill();
    }
}
