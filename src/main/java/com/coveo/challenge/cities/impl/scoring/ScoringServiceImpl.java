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

    /**
     * Margin distance for a city to be relevant, 40 degrees.
     */
    private static final BigDecimal MAX_RELEVANT_DISTANCE = BigDecimal.valueOf(40);

    @Override
    public List<Suggestion> evaluate(Query query, List<City> cities) {
        return cities.stream().map(city -> new Suggestion(city, computeScore(query, city))).collect(Collectors.toList());
    }

    private BigDecimal computeScore(Query query, City city) {
        var nameScore = computeNameScore(query.getQueryString(), city.getShortName());
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
        // TODO normalize
        var latitudeDiffSquared = computeDiffSquared(requestedLatitude, actualLatitude);
        var longitudeDiffSquared = computeDiffSquared(requestedLongitude, actualLongitude);
        var distance = latitudeDiffSquared.add(longitudeDiffSquared)
                .sqrt(MathContext.DECIMAL32)
                .setScale(1, RoundingMode.CEILING);
        if (distance.compareTo(MAX_RELEVANT_DISTANCE) > 0) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.ZERO.max(BigDecimal.ONE.subtract(distance.divide(MAX_RELEVANT_DISTANCE, RoundingMode.FLOOR).sqrt(MathContext.DECIMAL32)));
    }

    private BigDecimal computeDiffSquared(BigDecimal requestedLatitude, BigDecimal actualLatitude) {
        var diff = requestedLatitude.subtract(actualLatitude).abs();
        if (diff.compareTo(BigDecimal.valueOf(180L)) > 0) {
            // add handling of cases like +179 and -177 (diff = 4, not 354)
            throw new UnsupportedOperationException("This diff in coordinates not yet implemented: " + requestedLatitude + " : " + actualLatitude);
        }
        return diff.pow(2);
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
