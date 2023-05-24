package com.example.modulith.demo.cargo.management.application.domain.transportation.request;

import org.jmolecules.ddd.annotation.ValueObject;

/**
 * Координаты
 *
 * @param longitude долгота
 * @param latitude  широта
 */
@ValueObject
public record Coordinates(double longitude, double latitude) {
    public Coordinates {
        if (longitude < 0. || longitude > 360.) {
            throw new IllegalArgumentException("Долгота должна быть в диапазоне от 0 до 360");
        }

        if (latitude < 0. || latitude > 180.) {
            throw new IllegalArgumentException("Широта должна быть в диапазоне от 0 до 180");
        }
    }
}

