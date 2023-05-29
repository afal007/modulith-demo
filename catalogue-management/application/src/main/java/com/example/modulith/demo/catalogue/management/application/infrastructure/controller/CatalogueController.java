package com.example.modulith.demo.catalogue.management.application.infrastructure.controller;

import java.util.Collections;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.modulith.demo.catalogue.management.api.model.FilterDTO;
import com.example.modulith.demo.catalogue.management.api.model.PageableDTO;
import com.example.modulith.demo.catalogue.management.api.model.TransportationRequestCardDTO;
import com.example.modulith.demo.catalogue.management.api.model.TransportationRequestCardPageDTO;
import com.example.modulith.demo.catalogue.management.api.spring.web.TransportationRequestCardV1Api;
import com.example.modulith.demo.catalogue.management.application.usecase.FindCardByIdQuery;
import com.example.modulith.demo.catalogue.management.application.usecase.GetCardsPageQuery;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CatalogueController implements TransportationRequestCardV1Api {

    private final QueryGateway queryGateway;

    @Override
    public ResponseEntity<TransportationRequestCardDTO> getV1TransportationRequestCardsId(Long id) {
        return ResponseEntity.of(queryGateway.query(new FindCardByIdQuery(id),
            ResponseTypes.optionalInstanceOf(TransportationRequestCardDTO.class)
        ).join());
    }

    @Override
    public ResponseEntity<TransportationRequestCardPageDTO> getV1TransportationRequestsId(
        PageableDTO pageable, FilterDTO filter
    ) {
        return ResponseEntity.ok(queryGateway.query(
            new GetCardsPageQuery(Pageable.ofSize(pageable.getSize()).withPage(pageable.getPage()), pageable.getQ(),
                Collections.emptySet()
            ), ResponseTypes.instanceOf(TransportationRequestCardPageDTO.class)).join());
    }
}
