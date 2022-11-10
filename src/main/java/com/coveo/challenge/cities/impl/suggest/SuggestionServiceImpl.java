package com.coveo.challenge.cities.impl.suggest;

import com.coveo.challenge.cities.api.ScoringService;
import com.coveo.challenge.cities.api.SearchService;
import com.coveo.challenge.cities.api.SuggestionService;
import com.coveo.challenge.cities.model.Query;
import com.coveo.challenge.cities.model.Suggestion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SuggestionServiceImpl implements SuggestionService {

    private final SearchService searchService;

    private final ScoringService scoringService;

    @Override
    public List<Suggestion> getSuggestion(Query query) {
        return scoringService.evaluate(query, searchService.search(query.getQueryString()))
                .stream()
                // from 1 to 0
                .sorted(Comparator.comparing(Suggestion::score).reversed())
                .collect(Collectors.toList());
    }
}
