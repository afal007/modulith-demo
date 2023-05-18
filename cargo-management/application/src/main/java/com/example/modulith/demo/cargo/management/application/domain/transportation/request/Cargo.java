package com.example.modulith.demo.cargo.management.application.domain.transportation.request;

import java.math.BigDecimal;

/**
 * Груз
 *
 * @param type   тип груза
 * @param cost   стоимость
 * @param volume объем груза в кубических метрах
 * @param weight вес груза в тоннах
 */
public record Cargo(String type, BigDecimal cost, Float volume, Float weight) {
    public Cargo {
        if (volume <= 0.) {
            throw new IllegalArgumentException("Объем груза не может быть меньше нуля");
        }

        if (weight <= 0.) {
            throw new IllegalArgumentException("Вес груза не может быть меньше нуля");
        }

        if (cost.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Стоимость груза не может быть меньше нуля");
        }
    }
}

