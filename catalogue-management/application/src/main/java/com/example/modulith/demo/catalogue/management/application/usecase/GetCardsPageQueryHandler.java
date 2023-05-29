package com.example.modulith.demo.catalogue.management.application.usecase;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.example.modulith.demo.catalogue.management.api.model.CargoDTO;
import com.example.modulith.demo.catalogue.management.api.model.CoordinatesDTO;
import com.example.modulith.demo.catalogue.management.api.model.RouteDTO;
import com.example.modulith.demo.catalogue.management.api.model.TrailerRequirementsDTO;
import com.example.modulith.demo.catalogue.management.api.model.TransportationRequestCardDTO;
import com.example.modulith.demo.catalogue.management.api.model.TransportationRequestCardPageDTO;
import com.example.modulith.demo.catalogue.management.api.model.WaypointDTO;
import com.example.modulith.demo.catalogue.management.application.domain.Cargo;
import com.example.modulith.demo.catalogue.management.application.domain.TrailerRequirements;
import com.example.modulith.demo.catalogue.management.application.domain.TransportationRequestCard;
import com.example.modulith.demo.catalogue.management.application.infrastructure.dao.TransportationRequestCardDAO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GetCardsPageQueryHandler {

    private final TransportationRequestCardDAO dao;

    @QueryHandler
    public TransportationRequestCardPageDTO handle(GetCardsPageQuery getCardsPageQuery) {
        Page<TransportationRequestCard> res;
        if (getCardsPageQuery.q() != null && !getCardsPageQuery.q().isBlank()) {
            res = dao.findByQuery(getCardsPageQuery.q(), getCardsPageQuery.pageable());
        } else {
            res = dao.findAll(getCardsPageQuery.pageable());
        }

        TransportationRequestCardPageDTO dto = new TransportationRequestCardPageDTO();
        dto.setSize(res.getSize());
        dto.setNumber(res.getNumber());
        dto.setContent(res.getContent().stream().map(c -> {
            TransportationRequestCardDTO cardDTO = new TransportationRequestCardDTO();

            cardDTO.setComment(c.getComment());
            cardDTO.setFreight(c.getFreight().value().toString());
            cardDTO.setSourceNumber(c.getSourceNumber());
            cardDTO.setTransportationType(
                TransportationRequestCardDTO.TransportationTypeEnum.fromValue(c.getTransportationType().name()));

            CargoDTO cargoDTO = new CargoDTO();
            Cargo cargo = c.getCargo();
            cargoDTO.setCargoType(cargo.type());
            cargoDTO.setCost(cargo.cost().toString());
            cargoDTO.setVolume(cargo.volume());
            cargoDTO.setWeight(cargo.weight());
            cardDTO.setCargo(cargoDTO);

            TrailerRequirementsDTO trailerRequirementsDTO = new TrailerRequirementsDTO();
            TrailerRequirements trailerRequirements = c.getTrailerRequirements();
            trailerRequirementsDTO.setType(
                TrailerRequirementsDTO.TypeEnum.fromValue(trailerRequirements.type().name()));
            trailerRequirementsDTO.setLoadingType(
                TrailerRequirementsDTO.LoadingTypeEnum.fromValue(trailerRequirements.loadingType().name()));
            trailerRequirementsDTO.setVolume(trailerRequirements.volume());
            trailerRequirementsDTO.setLoadCapacity(trailerRequirements.loadCapacity());
            cardDTO.setTrailerRequirements(trailerRequirementsDTO);

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

                if (w.dateTimeStart() != null) {
                    waypointDTO.setDateTimeStart(w.dateTimeStart().toString());
                }

                if (w.dateTimeEnd() != null) {
                    waypointDTO.setDateTimeEnd(w.dateTimeEnd().toString());
                }

                return waypointDTO;
            }).toList());
            cardDTO.setRoute(routeDTO);

            return cardDTO;
        }).toList());
        dto.setTotalPages(res.getTotalPages());
        dto.setTotalElements(res.getTotalElements());
        dto.setNumberOfElements(res.getNumberOfElements());

        return dto;
    }
}
