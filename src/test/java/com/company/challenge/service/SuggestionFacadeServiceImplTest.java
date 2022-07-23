package com.company.challenge.service;

import com.company.challenge.dto.SuggestionDto;
import com.company.challenge.entity.City;
import com.company.challenge.repository.CityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

class SuggestionFacadeServiceImplTest {

    private static final String QUERY = "111";
    private static final String LATITUDE = "222";
    private static final String LONGITUDE = "333";
    private SuggestionFacadeService suggestionFacadeService;

    private CityRepository cityRepository;
    private SuggestionService suggestionService;


    @BeforeEach
    void setUp() {
        cityRepository = mock(CityRepository.class);
        suggestionService = mock(SuggestionService.class);
        suggestionFacadeService = new SuggestionFacadeServiceImpl(cityRepository, suggestionService);
    }

    public static Stream<Arguments> getSuggestionsArguments() {
        return Stream.of(
                Arguments.of(
                        Arrays.asList(
                                suggestion("a1", new BigDecimal("0.5")),
                                suggestion("a2", new BigDecimal("0.6")),
                                suggestion("b3", new BigDecimal("0.7"))
                        ),
                        Arrays.asList(
                                suggestion("b3", new BigDecimal("0.7")),
                                suggestion("a2", new BigDecimal("0.6")),
                                suggestion("a1", new BigDecimal("0.5"))
                        )
                ),
                Arguments.of(
                        Arrays.asList(
                                suggestion("a1", new BigDecimal("0.5")),
                                suggestion("b1", new BigDecimal("0.5")),
                                suggestion("c2", new BigDecimal("0.6")),
                                suggestion("a2", new BigDecimal("0.6")),
                                suggestion("b3", new BigDecimal("0.7")),
                                suggestion("b4", new BigDecimal("0.7"))
                        ),
                        Arrays.asList(
                                suggestion("b3", new BigDecimal("0.7")),
                                suggestion("b4", new BigDecimal("0.7")),
                                suggestion("a2", new BigDecimal("0.6")),
                                suggestion("c2", new BigDecimal("0.6")),
                                suggestion("a1", new BigDecimal("0.5")),
                                suggestion("b1", new BigDecimal("0.5"))
                        )
                )
        );
    }

    @ParameterizedTest
    @MethodSource("getSuggestionsArguments")
    void getSuggestions(List<SuggestionDto> suggestionsProvidedByService, List<SuggestionDto> expectedSuggestions) {
        doReturn(mockCities(suggestionsProvidedByService)).when(cityRepository).findByNameContaining(QUERY);

        List<SuggestionDto> suggestions = suggestionFacadeService.getSuggestions(QUERY, LATITUDE, LONGITUDE);

        assertEquals(expectedSuggestions, suggestions);
    }

    private static SuggestionDto suggestion(String name, BigDecimal score) {
        return SuggestionDto.builder()
                .name(name)
                .latitude("33.44")
                .longitude("35.42")
                .score(score)
                .build();
    }

    private City mockCity(SuggestionDto suggestionDto) {
        City city = mock(City.class);
        doReturn(suggestionDto).when(suggestionService).getSuggestion(QUERY, LATITUDE, LONGITUDE, city);
        return city;
    }

    private List<City> mockCities(List<SuggestionDto> suggestions) {
        return suggestions.stream()
                .map(this::mockCity)
                .collect(Collectors.toList());
    }
}