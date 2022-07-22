package com.company.challenge.service;

import com.company.challenge.dto.SuggestionDto;
import com.company.challenge.entity.City;
import com.company.challenge.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SuggestionFacadeServiceImpl implements SuggestionFacadeService {

    private final CityRepository cityRepository;
    private final SuggestionService suggestionService;

    /**
     * Gets list of cities by name and assigns a score based on the latitude/longitude.
     * City search is simplified - use only "containing" in this task
     */
    @Override
    public List<SuggestionDto> getSuggestions(String query, String latitude, String longitude) {
        log.info("Get suggestions by query: {}, latitude: {}, longitude: {}", query, latitude, longitude);
        List<City> cities = cityRepository.findByNameContaining(query);
        return cities.stream()
                .map(city -> suggestionService.getSuggestion(query, latitude, longitude, city))
                .sorted(Comparator.comparing(SuggestionDto::getScore).reversed().thenComparing(SuggestionDto::getName))
                .collect(Collectors.toList());
    }
}
