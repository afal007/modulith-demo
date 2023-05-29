package com.example.modulith.demo.catalogue.management.application.domain;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.jmolecules.ddd.annotation.ValueObject;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Точка маршрута
 *
 * @param type           тип работ
 * @param address        адрес
 * @param coordinates    координаты
 * @param organizationId идентификатор организации-грузодержателя
 * @param dateTimeStart  дата и время начала работ с часовым поясом
 * @param dateTimeEnd    дата и время окончания работ с часовым поясом
 */
@ValueObject
public record Waypoint(
    TypeEnum type, String address, Coordinates coordinates, UUID organizationId,
    @Field(type = FieldType.Date, format = DateFormat.date_time) OffsetDateTime dateTimeStart,
    @Field(type = FieldType.Date, format = DateFormat.date_time) OffsetDateTime dateTimeEnd
) {

    /**
     * Тип работ в точке
     */
    public enum TypeEnum {
        LOADING,

        UNLOADING
    }
}

