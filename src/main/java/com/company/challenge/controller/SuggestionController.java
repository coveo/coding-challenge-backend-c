package com.company.challenge.controller;

import com.company.challenge.dto.SuggestionDto;
import com.company.challenge.dto.SuggestionsDto;
import com.company.challenge.service.SuggestionFacadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/suggestions")
public class SuggestionController {

    private final SuggestionFacadeService suggestionFacadeService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public SuggestionsDto getSuggestions(
            @RequestParam(value = "q") String query,
            @RequestParam(value = "latitude", required = false) String latitude,
            @RequestParam(value = "longitude", required = false) String longitude
    ) {
        List<SuggestionDto> suggestions = suggestionFacadeService.getSuggestions(query, latitude, longitude);
        return SuggestionsDto.builder()
                .suggestions(suggestions)
                .build();
    }
}
