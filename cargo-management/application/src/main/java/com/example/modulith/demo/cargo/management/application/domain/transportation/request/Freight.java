package com.example.modulith.demo.cargo.management.application.domain.transportation.request;

import java.math.BigDecimal;

public record Freight(BigDecimal value) {

    private static final BigDecimal ONE_THOUSAND = new BigDecimal(1000L);

    public Freight {
        if (value.compareTo(ONE_THOUSAND) < 0) {
            throw new IllegalArgumentException("Сумма фрахта не может быть меньше 1000");
        }
    }
}
