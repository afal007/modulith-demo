package com.example.modulith.demo.cargo.management.application.infrastructure.web;

import java.math.BigDecimal;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.stream.Collectors;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.modulith.demo.cargo.management.api.model.CargoDTO;
import com.example.modulith.demo.cargo.management.api.model.TemperatureConditionsDTO;
import com.example.modulith.demo.cargo.management.api.model.TrailerRequirementsDTO;
import com.example.modulith.demo.cargo.management.api.model.TransportationRequestDTO;
import com.example.modulith.demo.cargo.management.api.spring.web.TransportationRequestV1Api;
import com.example.modulith.demo.cargo.management.application.domain.transportation.request.Cargo;
import com.example.modulith.demo.cargo.management.application.domain.transportation.request.Coordinates;
import com.example.modulith.demo.cargo.management.application.domain.transportation.request.Freight;
import com.example.modulith.demo.cargo.management.application.domain.transportation.request.Route;
import com.example.modulith.demo.cargo.management.application.domain.transportation.request.TemperatureConditions;
import com.example.modulith.demo.cargo.management.application.domain.transportation.request.TrailerRequirements;
import com.example.modulith.demo.cargo.management.application.domain.transportation.request.TransportationRequest;
import com.example.modulith.demo.cargo.management.application.domain.transportation.request.Waypoint;
import com.example.modulith.demo.cargo.management.application.usecase.command.createrequest.CreateTransportationRequestCommand;
import com.example.modulith.demo.cargo.management.application.usecase.query.getrequest.FindTransportationRequestByIdQuery;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TransportationRequestsController implements TransportationRequestV1Api {

    private final QueryGateway queryGateway;
    private final CommandGateway commandGateway;

    @Override
    public ResponseEntity<TransportationRequestDTO> getV1TransportationRequestsId(Long id) {
        return ResponseEntity.of(queryGateway.query(new FindTransportationRequestByIdQuery(id),
            ResponseTypes.optionalInstanceOf(TransportationRequestDTO.class)
        ).join());
    }

    @Override
    public ResponseEntity<Void> postV1TransportationRequests(TransportationRequestDTO transportationRequestDTO) {
        long id = commandGateway.sendAndWait(new CreateTransportationRequestCommand(map(transportationRequestDTO)));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(location).build();
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
