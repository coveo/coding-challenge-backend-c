package com.coveo.challenge.cities.api;

import com.coveo.challenge.cities.model.Query;
import com.coveo.challenge.cities.model.Suggestion;

import java.util.List;

public interface SuggestionService {
    List<Suggestion> getSuggestion(Query query);
}
