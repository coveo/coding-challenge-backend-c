package com.company.challenge.integration;

import com.company.challenge.dto.SuggestionDto;
import com.company.challenge.dto.SuggestionsDto;
import com.company.challenge.entity.City;
import com.company.challenge.repository.CityRepository;
import com.company.challenge.service.ScoreService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@IntegrationTest
@AutoConfigureMockMvc
class SuggestionControllerIntTest {

    public static final String QUERY_LATITUDE = "32.23";
    public static final String QUERY_LONGITUDE = "44.12";
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CityRepository cityRepository;

    @MockBean
    private ScoreService scoreService;

    @SneakyThrows
    @Test
    void getSuggestions() {
        List<City> cities = Arrays.asList(
                city("London"),
                city("abc"),
                city("12345")
        );
        doReturn(cities).when(cityRepository).findByNameContaining("Lon");
        doReturn(new BigDecimal("0.7"))
                .when(scoreService).getScore("Lon", QUERY_LATITUDE, QUERY_LONGITUDE, city("abc"));
        doReturn(new BigDecimal("0.4"))
                .when(scoreService).getScore("Lon", QUERY_LATITUDE, QUERY_LONGITUDE, city("London"));
        doReturn(new BigDecimal("0.2"))
                .when(scoreService).getScore("Lon", QUERY_LATITUDE, QUERY_LONGITUDE, city("12345"));


        MvcResult mvcResult = mockMvc.perform(
                get("/suggestions")
                        .queryParam("q", "Lon")
                        .queryParam("latitude", QUERY_LATITUDE)
                        .queryParam("longitude", QUERY_LONGITUDE)
        ).andReturn();


        String response = mvcResult.getResponse().getContentAsString();
        SuggestionsDto suggestionsDto = suggestionsDto(response);

        assertNotNull(suggestionsDto);
        List<SuggestionDto> suggestions = suggestionsDto.getSuggestions();
        assertEquals(3, suggestions.size());
        assertEquals("abc", suggestions.get(0).getName());
        assertEquals(new BigDecimal("0.7"), suggestions.get(0).getScore());
        assertEquals("London", suggestions.get(1).getName());
        assertEquals(new BigDecimal("0.4"), suggestions.get(1).getScore());
        assertEquals("12345", suggestions.get(2).getName());
        assertEquals(new BigDecimal("0.2"), suggestions.get(2).getScore());
    }

    private City city(String name) {
        return City.builder()
                .name(name)
                .build();
    }

    @SneakyThrows
    @Test
    void getSuggestionsQueryOnlyNoCitiesFound() {
        doReturn(new ArrayList<>()).when(cityRepository).findByNameContaining("Abc");


        MvcResult mvcResult = mockMvc.perform(
                get("/suggestions")
                        .queryParam("q", "Abc")
        ).andReturn();


        String response = mvcResult.getResponse().getContentAsString();
        SuggestionsDto suggestionsDto = suggestionsDto(response);

        assertNotNull(suggestionsDto);
        List<SuggestionDto> suggestions = suggestionsDto.getSuggestions();
        assertEquals(0, suggestions.size());
        verify(cityRepository).findByNameContaining("Abc");
    }

    @SneakyThrows
    private SuggestionsDto suggestionsDto(String response) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response, SuggestionsDto.class);
    }
}