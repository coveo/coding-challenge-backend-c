package com.coveo.challenge.cities.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Query {

    private final String queryString;

    private final BigDecimal latitude;

    private final BigDecimal longitude;
}
