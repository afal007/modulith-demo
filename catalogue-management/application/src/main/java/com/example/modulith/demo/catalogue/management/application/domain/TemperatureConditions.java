package com.example.modulith.demo.catalogue.management.application.domain;

import org.jmolecules.ddd.annotation.ValueObject;

/**
 * Температурные условия
 *
 * @param temperatureFrom минимальная допустимая температура
 * @param temperatureTo   максимальная допустимая температура
 */
@ValueObject
public record TemperatureConditions(Integer temperatureFrom, Integer temperatureTo) {}

