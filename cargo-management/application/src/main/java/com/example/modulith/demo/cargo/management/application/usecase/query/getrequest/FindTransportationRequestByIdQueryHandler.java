package com.example.modulith.demo.cargo.management.application.usecase.query.getrequest;

import java.util.Optional;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import com.example.modulith.demo.cargo.management.application.infrastructure.TransportationRequestDAO;
import com.example.modulith.demo.cargo.management.application.infrastructure.TransportationRequestEntity;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FindTransportationRequestByIdQueryHandler {

    private final TransportationRequestDAO transportationRequestDAO;

    @QueryHandler
    public Optional<TransportationRequestEntity> handle(
        FindTransportationRequestByIdQuery findTransportationRequestByIdQuery
    ) {
        return transportationRequestDAO.findById(findTransportationRequestByIdQuery.id());
    }

}
