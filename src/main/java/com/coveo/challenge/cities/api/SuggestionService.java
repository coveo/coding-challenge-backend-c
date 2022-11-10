package com.coveo.challenge.cities.api;

import com.coveo.challenge.cities.model.Query;
import com.coveo.challenge.cities.model.Suggestion;

import java.util.List;

/**
 * Provides suggestions to the user input.
 */
public interface SuggestionService {

    /**
     * Computes suggested cities .
     *
     * @param query string which city name must start with
     * @return list of suggested cities
     */
    List<Suggestion> getSuggestion(Query query);
}
