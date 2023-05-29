package com.example.modulith.demo.catalogue.management.application.domain;

import java.math.BigDecimal;

import org.jmolecules.ddd.annotation.ValueObject;

/**
 * Груз
 *
 * @param type   тип груза
 * @param cost   стоимость
 * @param volume объем груза в кубических метрах
 * @param weight вес груза в тоннах
 */
@ValueObject
public record Cargo(String type, BigDecimal cost, Float volume, Float weight) {}

