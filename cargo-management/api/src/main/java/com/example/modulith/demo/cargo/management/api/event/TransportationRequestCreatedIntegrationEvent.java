package com.example.modulith.demo.cargo.management.api.event;

import com.example.modulith.demo.cargo.management.api.model.TransportationRequestDTO;

public record TransportationRequestCreatedIntegrationEvent(TransportationRequestDTO transportationRequestDTO) {}
