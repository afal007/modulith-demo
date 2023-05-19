package com.example.modulith.demo.notifications.management.application.dao;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "NOTIFICATION", schema = "NOTIFICATIONS_MANAGEMENT")
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @CreationTimestamp
    private OffsetDateTime createdAt;

    private UUID userId;

    private String text;

    private boolean seen;

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
        NotificationEntity that = (NotificationEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }
}
