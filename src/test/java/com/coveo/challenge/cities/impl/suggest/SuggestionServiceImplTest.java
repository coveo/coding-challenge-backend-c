package com.coveo.challenge.cities.impl.suggest;

import com.coveo.challenge.cities.api.ScoringService;
import com.coveo.challenge.cities.api.SearchService;
import com.coveo.challenge.cities.model.City;
import com.coveo.challenge.cities.model.Query;
import com.coveo.challenge.cities.model.Suggestion;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SuggestionServiceImplTest {

    @InjectMocks
    private SuggestionServiceImpl suggestionService;

    @Mock
    private SearchService searchService;

    @Mock
    private ScoringService scoringService;

    @Test
    void bestMatchFirst() {
        // given multiple matches with diverse score
        Query query = new Query("Brigh", null, null);

        final City brighton = City.of(1L, "Brighton");
        final City bright = City.of(1L, "Bright");
        final City brigham_city = City.of(1L, "Brigham City");
        List<City> cities = List.of(brighton, bright, brigham_city);
        when(searchService.search(query.getQueryString())).thenReturn(cities);

        List<Suggestion> suggestions = List.of(
                new Suggestion(brighton, BigDecimal.ONE),
                new Suggestion(brigham_city, BigDecimal.ZERO),
                new Suggestion(bright, BigDecimal.TEN));
        when(scoringService.evaluate(query, cities)).thenReturn(suggestions);

        // when sorting
        suggestions = suggestionService.getSuggestion(query);

        // then higher score comes first
        assertEquals(3, suggestions.size());
        assertEquals(BigDecimal.TEN, suggestions.get(0).score());
        assertEquals(BigDecimal.ONE, suggestions.get(1).score());
        assertEquals(BigDecimal.ZERO, suggestions.get(2).score());
    }
}