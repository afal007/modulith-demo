package com.example.modulith.demo.cargo.management.application.domain.transportation.request;

import java.time.OffsetDateTime;
import java.util.UUID;

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
public record Waypoint(
    TypeEnum type, String address, Coordinates coordinates, UUID organizationId, OffsetDateTime dateTimeStart,
    OffsetDateTime dateTimeEnd
) {

    /**
     * Тип работ в точке
     */
    public enum TypeEnum {
        LOADING,

        UNLOADING
    }
}

