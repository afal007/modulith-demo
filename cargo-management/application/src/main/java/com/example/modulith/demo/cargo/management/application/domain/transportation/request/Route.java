package com.example.modulith.demo.cargo.management.application.domain.transportation.request;

import java.util.List;

/**
 * Маршрут
 *
 * @param waypointList список точек маршрута
 */
public record Route(List<Waypoint> waypointList) {}

