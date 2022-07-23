package com.company.challenge.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ScoreServiceImplTest {

    private ScoreService scoreService;

    @BeforeEach
    void setUp() {
        scoreService = new ScoreServiceImpl();
    }

    @RepeatedTest(100)
    void getScore() {
        BigDecimal score = scoreService.getScore(null, null, null, null);

        assertTrue(score.compareTo(BigDecimal.ONE) <= 0);
        assertTrue(score.compareTo(BigDecimal.ZERO) >= 0);
    }
}