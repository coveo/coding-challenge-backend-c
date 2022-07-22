package com.company.challenge.service;

import com.company.challenge.dto.SuggestionDto;
import com.company.challenge.entity.City;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class SuggestionServiceImpl implements SuggestionService {

    private final ScoreService scoreService;

    @Override
    public SuggestionDto getSuggestion(String query, String latitude, String longitude, City city) {
        BigDecimal score = scoreService.getScore(query, latitude, longitude, city);
        return SuggestionDto.builder()
                .name(city.getName())
                .latitude(String.valueOf(city.getLatitude()))
                .longitude(String.valueOf(city.getLongitude()))
                .score(score)
                .build();
    }
}
