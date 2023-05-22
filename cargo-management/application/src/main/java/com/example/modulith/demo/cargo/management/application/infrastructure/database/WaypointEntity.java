package com.example.modulith.demo.cargo.management.application.infrastructure.database;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "WAYPOINT", schema = "CARGO_MANAGEMENT")
@RequiredArgsConstructor
public class WaypointEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @CreationTimestamp
    private OffsetDateTime createdAt;

    private String type;

    private String address;

    private Double latitude;
    private Double longitude;

    private UUID organizationId;

    private OffsetDateTime dateTimeStart;
    private OffsetDateTime dateTimeEnd;

    @ManyToOne
    @JoinColumn(name = "transportation_request_id", nullable = false)
    private TransportationRequestEntity transportationRequest;

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        WaypointEntity that = (WaypointEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }
}
