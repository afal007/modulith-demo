package com.example.modulith.demo.cargo.management.application.infrastructure;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "TRANSPORTATION_REQUEST", schema = "CARGO_MANAGEMENT")
@RequiredArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class TransportationRequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private short version;

    @CreatedBy
    private UUID createdBy;

    @CreationTimestamp
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    private OffsetDateTime updatedAt;

    private BigDecimal freight;

    private String cargoType;
    private BigDecimal cargoCost;
    private Float cargoVolume;
    private Float cargoWeight;

    private String trailerType;
    private String trailerLoadingType;
    private Float trailerVolume;
    private Float trailerLoadCapacity;
    private Integer trailerTemperatureFrom;
    private Integer trailerTemperatureTo;

    private String transportationType;

    private String sourceNumber;
    private String comment;

    @OneToMany(mappedBy = "transportationRequest", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<WaypointEntity> waypointList = new ArrayList<>();

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
        TransportationRequestEntity that = (TransportationRequestEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }
}
