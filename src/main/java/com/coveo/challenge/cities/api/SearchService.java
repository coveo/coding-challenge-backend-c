package com.coveo.challenge.cities.api;

import com.coveo.challenge.cities.model.City;

import java.util.List;

/**
 * Search for cities.
 */
public interface SearchService {

    /**
     * Search for cities whose name matches the query string.
     */
    List<City> search(String queryString);
}
