package com.example.modulith.demo.cargo.management.application.usecase.command.create.request;

import org.springframework.stereotype.Component;

import com.example.modulith.demo.cargo.management.application.domain.transportation.request.TransportationRequestRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CreateTransportationRequestCommandHandler {

    private final TransportationRequestRepository transportationRequestRepository;

    public long handle(CreateTransportationRequestCommand createTransportationRequestCommand) {
        return transportationRequestRepository.add(createTransportationRequestCommand.transportationRequest()).getId();
    }

}
