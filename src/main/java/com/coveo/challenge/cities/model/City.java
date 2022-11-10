package com.coveo.challenge.cities.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data(staticConstructor = "of")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class City {
    @JsonIgnore
    private final long id;
    /**
     * Name which is used for search.
     */
    @JsonIgnore
    private final String shortName;
    /**
     * Full unique name to display.
     */
    @EqualsAndHashCode.Include
    private final String name;
    private final BigDecimal latitude;
    private final BigDecimal longitude;


    /**
     * Factory method to be used when short and full name are the same, e.g. in tests.
     */
    public static City of(long id, String name, BigDecimal latitude, BigDecimal longitude) {
        return of(id, name, name, latitude, longitude);
    }

    /**
     * Factory method to be used when short and full name are the same, e.g. in tests.
     */
    public static City of(long id, String name) {
        return of(id, name, null, null);
    }
}
