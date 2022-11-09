package com.coveo.challenge.cities.controller;

import com.coveo.challenge.cities.api.SuggestionService;
import com.coveo.challenge.cities.model.Query;
import com.coveo.challenge.cities.model.Suggestion;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CitySuggestionController {

    private final SuggestionService suggestionService;

    @GetMapping("/suggestions")
    public List<Suggestion> getSuggestion(@RequestParam(name = "q") String query,
                                          @RequestParam(required = false) BigDecimal latitude,
                                          @RequestParam(required = false) BigDecimal longitude) {
        return suggestionService.getSuggestion(new Query(query, latitude, longitude));
    }
}
