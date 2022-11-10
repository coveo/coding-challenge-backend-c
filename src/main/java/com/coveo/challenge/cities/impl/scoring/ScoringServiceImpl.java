package com.coveo.challenge.cities.impl.scoring;

import com.coveo.challenge.cities.api.ScoringService;
import com.coveo.challenge.cities.model.City;
import com.coveo.challenge.cities.model.Query;
import com.coveo.challenge.cities.model.Suggestion;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScoringServiceImpl implements ScoringService {

    /**
     * Scaled used in score calculations.
     */
    private static final int SCALE = 2;
    private static final BigDecimal MAX_DISTANCE = BigDecimal.valueOf(Math.sqrt(180 * 180 * 2));

    @Override
    public List<Suggestion> evaluate(Query query, List<City> cities) {
        return cities.stream().map(city -> new Suggestion(city, computeScore(query, city))).collect(Collectors.toList());
    }

    private BigDecimal computeScore(Query query, City city) {
        var nameScore = computeNameScore(query.getQueryString(), city.getName());
        if (query.getLatitude() == null || query.getLongitude() == null) {
            return nameScore;
        }
        var coordinateScore = computeCoordinateScore(query.getLatitude(), query.getLongitude(), city.getLatitude(),
                city.getLongitude());

        return nameScore.add(coordinateScore).divide(BigDecimal.valueOf(2L), 1, RoundingMode.CEILING);
    }

    /**
     * @param requestedLatitude  requested coordinate
     * @param requestedLongitude requested coordinate
     * @param actualLatitude     of the city
     * @param actualLongitude    of the city
     * @return value between 0 and 1
     */
    private BigDecimal computeCoordinateScore(@NotNull BigDecimal requestedLatitude,
                                              @NotNull BigDecimal requestedLongitude,
                                              @NotNull BigDecimal actualLatitude, @NotNull BigDecimal actualLongitude) {
        var distance = requestedLatitude.subtract(actualLatitude).pow(2).add(
                requestedLongitude.subtract(actualLongitude).pow(2)).sqrt(MathContext.DECIMAL32).setScale(1,
                RoundingMode.CEILING);
        return BigDecimal.ONE.subtract(distance.divide(MAX_DISTANCE, RoundingMode.FLOOR));
    }

    /**
     * @param queryString name queried
     * @param name        of the city
     * @return value between 0 and 1
     */
    private BigDecimal computeNameScore(String queryString, String name) {
        if (!name.startsWith(queryString)) {
            throw new IllegalStateException("Programming error: name is supposed to start with queryString here");
        }
        return BigDecimal.valueOf(queryString.length()).divide(BigDecimal.valueOf(name.length()), SCALE,
                RoundingMode.CEILING);
    }
}
