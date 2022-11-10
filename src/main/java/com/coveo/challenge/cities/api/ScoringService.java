package com.coveo.challenge.cities.api;

import com.coveo.challenge.cities.model.City;
import com.coveo.challenge.cities.model.Query;
import com.coveo.challenge.cities.model.Suggestion;

import java.util.List;

/**
 * Evaluates how good found cities match the query, i.e. computes matching score.
 */
public interface ScoringService {

    /**
     * Evaluate each found city against query.
     *
     * @param query  query
     * @param cities list of cities found
     * @return list of suggestions, i.e. each city is provided with a matching score
     */
    List<Suggestion> evaluate(Query query, List<City> cities);
}
