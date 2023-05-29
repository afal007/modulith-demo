package com.example.modulith.demo.cargo.management.application.usecase.command.createrequest;

import java.util.Optional;
import java.util.UUID;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.gateway.EventGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.jmolecules.event.annotation.DomainEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtClaimAccessor;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import com.example.modulith.demo.cargo.management.api.event.TransportationRequestCreatedIntegrationEvent;
import com.example.modulith.demo.cargo.management.api.model.TransportationRequestDTO;
import com.example.modulith.demo.cargo.management.application.domain.transportation.request.TransportationRequest;
import com.example.modulith.demo.cargo.management.application.domain.transportation.request.TransportationRequestRepository;
import com.example.modulith.demo.cargo.management.application.usecase.query.getrequest.FindTransportationRequestByIdQuery;
import com.example.modulith.demo.notifications.management.api.command.SendNotificationIntegrationCommand;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CreateTransportationRequestCommandHandler {

    private final EventGateway eventGateway;
    private final QueryGateway queryGateway;
    private final CommandGateway commandGateway;
    private final TransportationRequestRepository transportationRequestRepository;

    @CommandHandler
    @DomainEventPublisher
    public long handle(CreateTransportationRequestCommand createTransportationRequestCommand) {
        TransportationRequest add =
            transportationRequestRepository.add(createTransportationRequestCommand.transportationRequest());

        Long id = add.getId();
        commandGateway.send(GenericCommandMessage.asCommandMessage(
            new SendNotificationIntegrationCommand(getCurrentUserId().orElseThrow(),
                "Создана заявка №%d".formatted(id)
            )));

        eventGateway.publish(new TransportationRequestCreatedIntegrationEvent(
            queryGateway.query(new FindTransportationRequestByIdQuery(id),
                ResponseTypes.optionalInstanceOf(TransportationRequestDTO.class)
            ).join().orElseThrow()));

        return id;
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
