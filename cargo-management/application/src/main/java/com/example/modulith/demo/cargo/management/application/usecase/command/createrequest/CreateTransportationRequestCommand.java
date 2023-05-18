package com.example.modulith.demo.cargo.management.application.usecase.command.createrequest;

import org.jetbrains.annotations.NotNull;

import com.example.modulith.demo.cargo.management.application.domain.transportation.request.TransportationRequest;

public record CreateTransportationRequestCommand(
    @NotNull("Заявка должна быть заполнена") TransportationRequest transportationRequest
) {}

