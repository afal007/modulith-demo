package com.example.modulith.demo.catalogue.management.application.domain;

import java.util.Optional;

import org.jmolecules.ddd.annotation.Repository;

@Repository
public interface TransportationRequestCardRepository {

    TransportationRequestCard add(TransportationRequestCard transportationRequestCard);

    TransportationRequestCard update(TransportationRequestCard transportationRequestCard);

    Optional<TransportationRequestCard> findById(long id);
}
