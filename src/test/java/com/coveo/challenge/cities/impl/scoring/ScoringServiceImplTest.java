package com.coveo.challenge.cities.impl.scoring;

import com.coveo.challenge.cities.api.ScoringService;
import com.coveo.challenge.cities.model.City;
import com.coveo.challenge.cities.model.Query;
import com.coveo.challenge.cities.model.Suggestion;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScoringServiceImplTest {

    private final ScoringService scoringService = new ScoringServiceImpl();

    @Test
    void testNoCities() {
        // given a query
        Query query = new Query("Londo", null, null);

        // when scoring empty list
        final List<Suggestion> suggestions = scoringService.evaluate(query, List.of());

        // then empty list
        assertNotNull(suggestions);
        assertTrue(suggestions.isEmpty());
    }

    @Test
    void testWrongName() {
        // given a query with a name
        Query query = new Query("Londo", null, null);

        // when scoring city with non-matching name
        // then exception thrown
        assertThrows(IllegalStateException.class, () ->
                scoringService.evaluate(query, List.of(City.of(1L, "St. Petersburg",
                        BigDecimal.ONE, BigDecimal.TEN))));
    }

    @Test
    void testPartialMatchNoCoordinates() {
        // given query without coordinates
        Query query = new Query("London", null, null);

        // when scoring exact match name
        final List<Suggestion> suggestions = scoringService.evaluate(query, List.of(City.of(1L, "Londonderry",
                BigDecimal.ONE, BigDecimal.TEN)));

        // then best score 1
        assertNotNull(suggestions);
        assertEquals(1, suggestions.size());
        final Suggestion suggestion = suggestions.get(0);
        final BigDecimal expected = BigDecimal.valueOf(6).divide(BigDecimal.valueOf(11), 2, RoundingMode.CEILING);
        assertEquals(expected, suggestion.getScore());
    }

    @Test
    void testExactMatchNoCoordinates() {
        // given query without coordinates
        Query query = new Query("St. Petersburg", null, null);

        // when scoring exact match name
        final List<Suggestion> suggestions = scoringService.evaluate(query, List.of(City.of(1L, "St. Petersburg",
                BigDecimal.ONE, BigDecimal.TEN)));

        // then best score 1
        assertNotNull(suggestions);
        assertEquals(1, suggestions.size());
        final Suggestion suggestion = suggestions.get(0);
        assertEquals(0, BigDecimal.ONE.compareTo(suggestion.getScore()));
    }

    @Test
    void testExactMatchNeighborhood() {
        // given query without coordinates
        Query query = new Query("St. Petersburg", BigDecimal.valueOf(60), BigDecimal.valueOf(30));

        // when scoring exact match name and coordinate diff smaller than 0.1
        final List<Suggestion> suggestions = scoringService.evaluate(query, List.of(City.of(1L, "St. Petersburg",
                BigDecimal.valueOf(60.03), BigDecimal.valueOf(29.07))));

        // then best score 1
        assertNotNull(suggestions);
        assertEquals(1, suggestions.size());
        final Suggestion suggestion = suggestions.get(0);
        assertEquals(0, BigDecimal.ONE.compareTo(suggestion.getScore()));
    }

    @Test
    void testExactMatchAntipode() {
        // given query without coordinates
        Query query = new Query("St. Petersburg", BigDecimal.valueOf(60), BigDecimal.valueOf(30));

        // when scoring exact match name and coordinate diff smaller than 0.1
        final List<Suggestion> suggestions = scoringService.evaluate(query, List.of(City.of(1L, "St. Petersburg",
                BigDecimal.valueOf(60 - 180), BigDecimal.valueOf(30 - 180))));

        // then best score 1
        assertNotNull(suggestions);
        assertEquals(1, suggestions.size());
        final Suggestion suggestion = suggestions.get(0);
        assertEquals(BigDecimal.valueOf(5, 1), suggestion.getScore());
    }
}