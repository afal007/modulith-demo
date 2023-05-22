package com.example.modulith.demo.cargo.management.application.infrastructure.database;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransportationRequestDAO extends JpaRepository<TransportationRequestEntity, Long> {}
