package com.example.modulith.demo.catalogue.management.application.usecase;

import java.util.Optional;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import com.example.modulith.demo.catalogue.management.api.model.CargoDTO;
import com.example.modulith.demo.catalogue.management.api.model.CoordinatesDTO;
import com.example.modulith.demo.catalogue.management.api.model.RouteDTO;
import com.example.modulith.demo.catalogue.management.api.model.TrailerRequirementsDTO;
import com.example.modulith.demo.catalogue.management.api.model.TransportationRequestCardDTO;
import com.example.modulith.demo.catalogue.management.api.model.WaypointDTO;
import com.example.modulith.demo.catalogue.management.application.domain.Cargo;
import com.example.modulith.demo.catalogue.management.application.domain.TrailerRequirements;
import com.example.modulith.demo.catalogue.management.application.infrastructure.dao.TransportationRequestCardDAO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FindCardByIdQueryHandler {

    private final TransportationRequestCardDAO dao;

    @QueryHandler
    public Optional<TransportationRequestCardDTO> handle(FindCardByIdQuery findCardByIdQuery) {
        return dao.findById(findCardByIdQuery.id()).map(c -> {
            TransportationRequestCardDTO dto = new TransportationRequestCardDTO();

            dto.setComment(c.getComment());
            dto.setFreight(c.getFreight().value().toString());
            dto.setSourceNumber(c.getSourceNumber());
            dto.setTransportationType(
                TransportationRequestCardDTO.TransportationTypeEnum.fromValue(c.getTransportationType().name()));

            CargoDTO cargoDTO = new CargoDTO();
            Cargo cargo = c.getCargo();
            cargoDTO.setCargoType(cargo.type());
            cargoDTO.setCost(cargo.cost().toString());
            cargoDTO.setVolume(cargo.volume());
            cargoDTO.setWeight(cargo.weight());
            dto.setCargo(cargoDTO);

            TrailerRequirementsDTO trailerRequirementsDTO = new TrailerRequirementsDTO();
            TrailerRequirements trailerRequirements = c.getTrailerRequirements();
            trailerRequirementsDTO.setType(
                TrailerRequirementsDTO.TypeEnum.fromValue(trailerRequirements.type().name()));
            trailerRequirementsDTO.setLoadingType(
                TrailerRequirementsDTO.LoadingTypeEnum.fromValue(trailerRequirements.loadingType().name()));
            trailerRequirementsDTO.setVolume(trailerRequirements.volume());
            trailerRequirementsDTO.setLoadCapacity(trailerRequirements.loadCapacity());
            dto.setTrailerRequirements(trailerRequirementsDTO);

            RouteDTO routeDTO = new RouteDTO();
            routeDTO.setWaypointList(c.getRoute().waypointList().stream().map(w -> {
                WaypointDTO waypointDTO = new WaypointDTO();
                waypointDTO.setType(WaypointDTO.TypeEnum.fromValue(w.type().name()));
                waypointDTO.setAddress(w.address());

                if (w.coordinates() != null) {
                    CoordinatesDTO coordinatesDTO = new CoordinatesDTO();
                    coordinatesDTO.setLatitude(Double.toString(w.coordinates().latitude()));
                    coordinatesDTO.setLatitude(Double.toString(w.coordinates().longitude()));
                    waypointDTO.setCoordinates(coordinatesDTO);
                }
                waypointDTO.setOrganizationId(w.organizationId());
                waypointDTO.setDateTimeStart(w.dateTimeStart().toString());
                waypointDTO.setDateTimeEnd(w.dateTimeEnd().toString());

                return waypointDTO;
            }).toList());
            dto.setRoute(routeDTO);

            return dto;
        });
    }
}
