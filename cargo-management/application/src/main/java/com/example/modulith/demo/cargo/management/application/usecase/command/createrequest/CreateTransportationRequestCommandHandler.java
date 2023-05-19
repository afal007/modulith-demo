package com.example.modulith.demo.cargo.management.application.usecase.command.createrequest;

import java.util.Optional;
import java.util.UUID;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtClaimAccessor;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import com.example.modulith.demo.cargo.management.application.domain.transportation.request.TransportationRequest;
import com.example.modulith.demo.cargo.management.application.domain.transportation.request.TransportationRequestRepository;
import com.example.modulith.demo.notifications.management.application.events.SendNotificationCommand;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CreateTransportationRequestCommandHandler {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final TransportationRequestRepository transportationRequestRepository;

    public long handle(CreateTransportationRequestCommand createTransportationRequestCommand) {
        TransportationRequest add =
            transportationRequestRepository.add(createTransportationRequestCommand.transportationRequest());

        applicationEventPublisher.publishEvent(
            new SendNotificationCommand(add, getCurrentUserId().get(), "Создана заявка №%d".formatted(add.getId())));

        return add.getId();
    }

    private Optional<UUID> getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof JwtAuthenticationToken)) {
            return Optional.empty();
        }

        return Optional.of((JwtAuthenticationToken) authentication)
            .map(JwtAuthenticationToken::getToken)
            .map(JwtClaimAccessor::getSubject)
            .map(UUID::fromString);
    }

}
