package com.coveo.challenge.cities.model;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.math.BigDecimal;

/**
 * @param city  City found.
 * @param score Match evaluation, from 0 to 1 (best match).
 */
public record Suggestion(@JsonUnwrapped City city, BigDecimal score) {

}
