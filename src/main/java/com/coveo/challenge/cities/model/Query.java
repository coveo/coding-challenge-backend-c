package com.coveo.challenge.cities.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Query to find cities.
 * <p>
 * Latitude and longitude are considered only it both provided.
 */
@Data
public class Query {

    /**
     * String city name must start with.
     */
    private final String queryString;

    /**
     * Optional latitude. Ignored if no latitude provided.
     */
    private final BigDecimal latitude;

    /**
     * Optional longitude. Ignored if no longitude provided.
     */
    private final BigDecimal longitude;
}
