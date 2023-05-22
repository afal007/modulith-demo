package com.example.modulith.demo.cargo.management.application.usecase.query.getrequest;

import java.util.Optional;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.modulith.demo.cargo.management.api.model.CargoDTO;
import com.example.modulith.demo.cargo.management.api.model.CoordinatesDTO;
import com.example.modulith.demo.cargo.management.api.model.RouteDTO;
import com.example.modulith.demo.cargo.management.api.model.TemperatureConditionsDTO;
import com.example.modulith.demo.cargo.management.api.model.TrailerRequirementsDTO;
import com.example.modulith.demo.cargo.management.api.model.TransportationRequestDTO;
import com.example.modulith.demo.cargo.management.api.model.WaypointDTO;
import com.example.modulith.demo.cargo.management.application.infrastructure.database.TransportationRequestDAO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FindTransportationRequestByIdQueryHandler {

    private final TransportationRequestDAO transportationRequestDAO;

    @QueryHandler
    @Transactional(readOnly = true)
    public Optional<TransportationRequestDTO> handle(
        FindTransportationRequestByIdQuery findTransportationRequestByIdQuery
    ) {
        return transportationRequestDAO.findById(findTransportationRequestByIdQuery.id()).map(e -> {
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
        });
    }

}
