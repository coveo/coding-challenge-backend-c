package com.company.challenge.service;

import com.company.challenge.entity.City;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.SplittableRandom;

@Service
public class ScoreServiceImpl implements ScoreService {

    /**
     * Scoring logic is not implemented here, random for now
     */
    @Override
    public BigDecimal getScore(String query, String latitude, String longitude, City city) {
        SplittableRandom splittableRandom = new SplittableRandom();
        int random = splittableRandom.nextInt(0, 10);
        return new BigDecimal(random).setScale(1, RoundingMode.HALF_UP)
                .divide(BigDecimal.TEN, RoundingMode.DOWN);
    }
}
