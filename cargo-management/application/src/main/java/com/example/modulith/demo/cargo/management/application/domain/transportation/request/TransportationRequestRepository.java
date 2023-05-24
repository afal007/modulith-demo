package com.example.modulith.demo.cargo.management.application.domain.transportation.request;

import java.util.Optional;

import org.jmolecules.ddd.annotation.Repository;

@Repository
public interface TransportationRequestRepository {

    TransportationRequest add(TransportationRequest transportationRequest);

    TransportationRequest update(TransportationRequest transportationRequest);

    Optional<TransportationRequest> findById(long id);
}
