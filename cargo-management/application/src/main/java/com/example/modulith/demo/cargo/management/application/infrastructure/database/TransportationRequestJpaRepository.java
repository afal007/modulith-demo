package com.example.modulith.demo.cargo.management.application.infrastructure.database;

import com.example.modulith.demo.cargo.management.application.domain.transportation.request.TemperatureConditions;
import com.example.modulith.demo.cargo.management.application.domain.transportation.request.TransportationRequest;
import lombok.RequiredArgsConstructor;
import org.jmolecules.ddd.annotation.Repository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TransportationRequestJpaRepository implements
    com.example.modulith.demo.cargo.management.application.domain.transportation.request.TransportationRequestRepository {

    private final TransportationRequestRepository transportationRequestDAO;

    @Override
    @Transactional
    public TransportationRequest add(TransportationRequest transportationRequest) {
        TransportationRequestEntity entity = new TransportationRequestEntity();
        entity.setFreight(transportationRequest.getFreight().value());
        entity.setCargoType(transportationRequest.getCargo().type());
        entity.setCargoCost(transportationRequest.getCargo().cost());
        entity.setCargoVolume(transportationRequest.getCargo().volume());
        entity.setCargoWeight(transportationRequest.getCargo().weight());
        entity.setTrailerType(transportationRequest.getTrailerRequirements().type().name());
        entity.setTrailerLoadingType(transportationRequest.getTrailerRequirements().loadingType().name());
        entity.setTrailerVolume(transportationRequest.getTrailerRequirements().volume());
        entity.setTrailerLoadCapacity(transportationRequest.getTrailerRequirements().loadCapacity());
        TemperatureConditions temperatureConditions =
            transportationRequest.getTrailerRequirements().temperatureConditions();
        if (temperatureConditions != null) {
            entity.setTrailerTemperatureFrom(temperatureConditions.temperatureFrom());
            entity.setTrailerTemperatureTo(temperatureConditions.temperatureTo());
        }
        entity.setTransportationType(transportationRequest.getTransportationType().name());
        entity.setSourceNumber(transportationRequest.getSourceNumber());
        entity.setComment(transportationRequest.getComment());
        transportationRequest.getRoute().waypointList().stream().map(w -> {
            WaypointEntity waypointEntity = new WaypointEntity();
            waypointEntity.setType(w.type().name());
            waypointEntity.setAddress(w.address());
            if (w.coordinates() != null) {
                waypointEntity.setLatitude(w.coordinates().latitude());
                waypointEntity.setLongitude(w.coordinates().longitude());
            }
            waypointEntity.setOrganizationId(w.organizationId());
            waypointEntity.setDateTimeStart(w.dateTimeStart());
            waypointEntity.setDateTimeEnd(w.dateTimeEnd());
            waypointEntity.setTransportationRequest(entity);

            return waypointEntity;
        }).forEach(entity.getWaypointList()::add);

        transportationRequestDAO.save(entity);
        transportationRequest.setId(entity.getId());
        return transportationRequest;
    }

    @Override
    @Transactional
    public TransportationRequest update(TransportationRequest transportationRequest) {
        return null;
    }

    @Override
    public Optional<TransportationRequest> findById(long id) {
        return Optional.empty();
    }
}
