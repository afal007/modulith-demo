package com.example.modulith.demo.cargo.management.application.usecase.command.createrequest;

import com.example.modulith.demo.cargo.management.application.domain.transportation.request.TransportationRequest;
import com.example.modulith.demo.cargo.management.application.domain.transportation.request.TransportationRequestRepository;
import com.example.modulith.demo.notifications.management.api.command.SendNotificationIntegrationCommand;
import lombok.RequiredArgsConstructor;
import org.jmolecules.event.annotation.DomainEventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtClaimAccessor;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CreateTransportationRequestCommandHandler {

    private final ApplicationEventPublisher eventPublisher;
    private final TransportationRequestRepository transportationRequestRepository;

    @DomainEventPublisher
    public long handle(CreateTransportationRequestCommand createTransportationRequestCommand) {
        TransportationRequest add =
            transportationRequestRepository.add(createTransportationRequestCommand.transportationRequest());

        eventPublisher.publishEvent(new SendNotificationIntegrationCommand(getCurrentUserId().get(),
                "Создана заявка №%d".formatted(add.getId())
            ));

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
