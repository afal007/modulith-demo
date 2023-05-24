package com.example.modulith.demo.cargo.management.application.domain.transportation.request;

import java.util.List;

import org.jmolecules.ddd.annotation.ValueObject;

/**
 * Маршрут
 *
 * @param waypointList список точек маршрута
 */
@ValueObject
public record Route(List<Waypoint> waypointList) {}

