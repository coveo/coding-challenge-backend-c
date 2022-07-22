package com.company.challenge.service;

import com.company.challenge.dto.SuggestionDto;
import com.company.challenge.entity.City;

public interface SuggestionService {
    SuggestionDto getSuggestion(String query, String latitude, String longitude, City city);
}
