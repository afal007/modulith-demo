package com.example.modulith.demo.catalogue.management.application.domain;

import java.math.BigDecimal;

import org.jmolecules.ddd.annotation.ValueObject;

@ValueObject
public record Freight(BigDecimal value, String currencyCode) {}
