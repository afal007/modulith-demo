package com.example.modulith.demo.cargo.management.application.domain.transportation.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(builderMethodName = "startFilling", buildMethodName = "fill")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TransportationRequest {

    private Long id;

    private Freight freight;

    private Cargo cargo;

    private TrailerRequirements trailerRequirements;

    private Route route;

    private TransportationTypeEnum transportationType;

    private String sourceNumber;

    private String comment;

    public enum TransportationTypeEnum {
        INTERCITY,

        INTERNATIONAL;
    }
}

