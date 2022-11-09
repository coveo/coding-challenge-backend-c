package com.coveo.challenge.cities.api;

import com.coveo.challenge.cities.model.City;

import java.util.List;

public interface SearchService {

    List<City> search(String queryString);
}
