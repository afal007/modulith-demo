package com.example.modulith.demo.catalogue.management.application.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.Data;

@Data
@Document(indexName = "transportation_request_card")
public class TransportationRequestCard {

    @Id
    private long id;

    private Freight freight;

    private CarrierPrice carrierPrice;

    private Cargo cargo;

    private TrailerRequirements trailerRequirements;

    private Route route;

    private TransportationTypeEnum transportationType;

    private String sourceNumber;

    private String comment;

    public enum TransportationTypeEnum {
        INTERCITY,

        INTERNATIONAL
    }
}

