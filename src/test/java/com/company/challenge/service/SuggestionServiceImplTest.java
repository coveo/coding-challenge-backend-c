package com.company.challenge.service;

import com.company.challenge.dto.SuggestionDto;
import com.company.challenge.entity.City;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

class SuggestionServiceImplTest {

    private SuggestionService suggestionService;
    private ScoreService scoreService;

    @BeforeEach
    void setUp() {
        scoreService = mock(ScoreService.class);
        suggestionService = new SuggestionServiceImpl(scoreService);
    }

    @Test
    void getSuggestion() {
        City city = City.builder()
                .id(11111L)
                .name("444")
                .latitude(new BigDecimal("555"))
                .longitude(new BigDecimal("666"))
                .population(new BigDecimal("777"))
                .build();
        doReturn(new BigDecimal("0.7")).when(scoreService).getScore("111", "222", "333", city);

        SuggestionDto suggestion = suggestionService.getSuggestion("111", "222", "333", city);

        assertNotNull(suggestion);
        assertEquals("444", suggestion.getName());
        assertEquals("555", suggestion.getLatitude());
        assertEquals("666", suggestion.getLongitude());
        assertEquals(new BigDecimal("0.7"), suggestion.getScore());
    }
}