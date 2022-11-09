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
    @EqualsAndHashCode.Include
    private final String name;
    private final BigDecimal latitude;
    private final BigDecimal longitude;
}
