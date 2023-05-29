package com.example.modulith.demo.catalogue.management.application.event.handler;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Currency;
import java.util.Random;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import com.example.modulith.demo.cargo.management.api.event.TransportationRequestCreatedIntegrationEvent;
import com.example.modulith.demo.cargo.management.api.model.CargoDTO;
import com.example.modulith.demo.cargo.management.api.model.TrailerRequirementsDTO;
import com.example.modulith.demo.cargo.management.api.model.TransportationRequestDTO;
import com.example.modulith.demo.catalogue.management.application.domain.Cargo;
import com.example.modulith.demo.catalogue.management.application.domain.CarrierPrice;
import com.example.modulith.demo.catalogue.management.application.domain.Coordinates;
import com.example.modulith.demo.catalogue.management.application.domain.Freight;
import com.example.modulith.demo.catalogue.management.application.domain.Route;
import com.example.modulith.demo.catalogue.management.application.domain.TrailerRequirements;
import com.example.modulith.demo.catalogue.management.application.domain.TransportationRequestCard;
import com.example.modulith.demo.catalogue.management.application.domain.Waypoint;
import com.example.modulith.demo.catalogue.management.application.infrastructure.dao.TransportationRequestCardDAO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TransportationRequestCreatedIntegrationEventHandler {

    private final TransportationRequestCardDAO transportationRequestCardDAO;

    @EventHandler
    public void handle(TransportationRequestCreatedIntegrationEvent event) {
        TransportationRequestDTO dto = event.transportationRequestDTO();

        TransportationRequestCard entity = new TransportationRequestCard();
        entity.setId(dto.getId());
        entity.setTransportationType(
            TransportationRequestCard.TransportationTypeEnum.valueOf(dto.getTransportationType().name()));
        entity.setSourceNumber(dto.getSourceNumber());
        entity.setComment(dto.getComment());

        BigDecimal freightValue = new BigDecimal(dto.getFreight());
        Currency currency = Currency.getInstance(dto.getCurrencyCode());
        entity.setFreight(new Freight(freightValue, currency.getCurrencyCode()));
        entity.setCarrierPrice(CarrierPrice.fromFreight(freightValue, currency));

        CargoDTO cargoDTO = dto.getCargo();
        entity.setCargo(new Cargo(cargoDTO.getCargoType(), new BigDecimal(cargoDTO.getCost()), cargoDTO.getVolume(),
            cargoDTO.getWeight()
        ));

        TrailerRequirementsDTO trailerRequirementsDTO = dto.getTrailerRequirements();
        entity.setTrailerRequirements(
            new TrailerRequirements(trailerRequirementsDTO.getVolume(), trailerRequirementsDTO.getLoadCapacity(),
                TrailerRequirements.TypeEnum.valueOf(trailerRequirementsDTO.getType().name()),
                TrailerRequirements.LoadingTypeEnum.valueOf(trailerRequirementsDTO.getLoadingType().name()), null
            ));

        Route route = new Route(new Random().nextFloat(), new Random().nextFloat(), event.transportationRequestDTO()
            .getRoute()
            .getWaypointList()
            .stream()
            .map((w) -> new Waypoint(Waypoint.TypeEnum.valueOf(w.getType().name()), w.getAddress(),
                w.getCoordinates() != null ? new Coordinates(Double.parseDouble(w.getCoordinates().getLongitude()),
                    Double.parseDouble(w.getCoordinates().getLatitude())
                ) : null, w.getOrganizationId(), OffsetDateTime.parse(w.getDateTimeStart()),
                OffsetDateTime.parse(w.getDateTimeEnd())
            ))
            .toList());
        entity.setRoute(route);

        transportationRequestCardDAO.save(entity);
    }
}
