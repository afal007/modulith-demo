package com.example.modulith.demo.catalogue.management.application.domain;

import java.util.List;

import org.jmolecules.ddd.annotation.ValueObject;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Маршрут
 *
 * @param distance      расстояние маршрута
 * @param waypointList  список точек маршрута
 * @param kilometerCost стоимость километра
 */
@ValueObject
public record Route(Float distance, Float kilometerCost, @Field(type = FieldType.Nested) List<Waypoint> waypointList) {}

