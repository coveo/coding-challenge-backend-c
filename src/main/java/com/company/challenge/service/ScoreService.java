package com.company.challenge.service;

import com.company.challenge.entity.City;

import java.math.BigDecimal;

public interface ScoreService {
    BigDecimal getScore(String query, String latitude, String longitude, City city);
}
