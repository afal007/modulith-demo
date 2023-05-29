package com.example.modulith.demo.catalogue.management.application.domain;

import java.math.BigDecimal;
import java.util.Currency;

public record CarrierPrice(BigDecimal price, String currencyCode) {

    public static final BigDecimal MULTIPLICAND = new BigDecimal("0.95");

    public static CarrierPrice fromFreight(BigDecimal freight, Currency currency) {
        return new CarrierPrice(freight.multiply(MULTIPLICAND), currency.getCurrencyCode());
    }
}
