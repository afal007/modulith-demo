package com.example.modulith.demo.catalogue.management.application.usecase;

import java.util.Set;
import java.util.UUID;

import org.springframework.data.domain.Pageable;

public record GetCardsPageQuery(Pageable pageable, String q, Set<UUID> authorIds) {}
