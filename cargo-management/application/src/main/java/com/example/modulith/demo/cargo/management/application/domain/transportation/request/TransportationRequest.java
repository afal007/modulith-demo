package com.example.modulith.demo.cargo.management.application.domain.transportation.request;

import org.jmolecules.ddd.annotation.AggregateRoot;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder(builderMethodName = "startFilling", buildMethodName = "fill")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@AggregateRoot
public class TransportationRequest {

    @Setter
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

        INTERNATIONAL
    }
}

