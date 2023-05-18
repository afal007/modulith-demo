package com.example.modulith.demo.cargo.management.application.domain.transportation.request;

import java.util.Optional;

public interface TransportationRequestRepository {

    TransportationRequest add(TransportationRequest transportationRequest);

    TransportationRequest update(TransportationRequest transportationRequest);

    Optional<TransportationRequest> findById(long id);
}
