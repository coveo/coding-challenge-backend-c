package com.coveo.challenge.cities.api;

import com.coveo.challenge.cities.model.City;
import com.coveo.challenge.cities.model.Query;
import com.coveo.challenge.cities.model.Suggestion;

import java.util.List;

public interface ScoringService {

    List<Suggestion> evaluate(Query query, List<City> cities);
}
