package com.example.modulith.demo.catalogue.management.application.domain;

import org.jmolecules.ddd.annotation.ValueObject;

/**
 * Координаты
 *
 * @param longitude долгота
 * @param latitude  широта
 */
@ValueObject
public record Coordinates(double longitude, double latitude) {}

