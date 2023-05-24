package com.example.modulith.demo.cargo.management.application.domain.transportation.request;

import org.jmolecules.ddd.annotation.ValueObject;

/**
 * Температурные условия
 *
 * @param temperatureFrom минимальная допустимая температура
 * @param temperatureTo   максимальная допустимая температура
 */
@ValueObject
public record TemperatureConditions(Integer temperatureFrom, Integer temperatureTo) {
    public TemperatureConditions {
        if (temperatureFrom == null && temperatureTo == null) {
            throw new IllegalArgumentException("Должна быть задана хотя бы одна граница для температурных условий");
        }

        if (temperatureFrom != null && temperatureTo != null && temperatureFrom > temperatureTo) {
            throw new IllegalArgumentException(
                "Нижняя граница температурного режима [%d] не может быть больше верхней границы [%d]".formatted(
                    temperatureFrom, temperatureTo));
        }
    }
}

