package com.coveo.challenge.cities.impl.scoring;

import com.coveo.challenge.cities.api.ScoringService;
import com.coveo.challenge.cities.model.City;
import com.coveo.challenge.cities.model.Query;
import com.coveo.challenge.cities.model.Suggestion;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScoringServiceImpl implements ScoringService {
    @Override
    public List<Suggestion> evaluate(Query query, List<City> cities) {
        return cities.stream().map(city -> new Suggestion(city, BigDecimal.ONE)).collect(Collectors.toList());
    }
}
