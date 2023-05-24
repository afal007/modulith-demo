package com.example.modulith.demo.cargo.management.application.domain.transportation.request;

import org.jmolecules.ddd.annotation.ValueObject;

/**
 * Требования к прицепу
 *
 * @param volume                объем тента в метрах кубических
 * @param loadCapacity          грузоподъемность в тоннах
 * @param type                  тип прицепа
 * @param loadingType           тип погрузки
 * @param temperatureConditions температурные условия
 */
@ValueObject
public record TrailerRequirements(
    Float volume, Float loadCapacity, TypeEnum type, LoadingTypeEnum loadingType,
    TemperatureConditions temperatureConditions
) {

    public TrailerRequirements {
        if (volume != null && volume <= 1.) {
            throw new IllegalArgumentException("Объем прицепа не может быть меньше 1 кубического метра");
        }

        if (loadCapacity != null && loadCapacity <= 0.5) {
            throw new IllegalArgumentException("Объем прицепа не может быть меньше 0.5 тонны");
        }

        if (type == TypeEnum.REFRIGERATOR && loadingType != LoadingTypeEnum.REAR) {
            throw new IllegalArgumentException(
                "Для типа прицепа 'Рефрижератор' тип погрузки может быть только 'Задняя'");
        }

        if (type == TypeEnum.AWNING && temperatureConditions != null) {
            throw new IllegalArgumentException("Для типа прицепа 'Тент' не могут быть указаны температурные условия");
        }
    }

    /**
     * Тип прицепа
     */
    public enum TypeEnum {
        /**
         * Рефрижератор
         */
        REFRIGERATOR,

        /**
         * Тент
         */
        AWNING,

        /**
         * Изотермический
         */
        ISOTHERMAL
    }

    /**
     * Тип погрузки
     */
    public enum LoadingTypeEnum {
        /**
         * Верхняя
         */
        UPPER,

        /**
         * Боковая
         */
        SIDE,

        /**
         * Задняя
         */
        REAR
    }
}

