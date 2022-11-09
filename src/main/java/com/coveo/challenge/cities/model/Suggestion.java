package com.coveo.challenge.cities.model;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Suggestion {

    @JsonUnwrapped
    private final City city;
    private final BigDecimal score;

}
