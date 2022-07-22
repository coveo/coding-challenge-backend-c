package com.company.challenge.service;

import com.company.challenge.dto.SuggestionDto;

import java.util.List;

public interface SuggestionFacadeService {
    List<SuggestionDto> getSuggestions(String query, String latitude, String longitude);
}
