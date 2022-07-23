package com.company.challenge.initializer;

import com.company.challenge.entity.City;
import com.company.challenge.repository.CityRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

@Service
@Slf4j
public class CityInitializerImpl implements CityInitializer {

    private final Resource csvFile;
    private final CityRepository cityRepository;

    public CityInitializerImpl(@Value("classpath:data/cities_canada-usa.csv") Resource csvFile,
                               CityRepository cityRepository) {
        this.csvFile = csvFile;
        this.cityRepository = cityRepository;
    }

    @SneakyThrows
    @Override
    public void initialize() {
        log.info("Move data from file to db");
        BufferedReader reader = new BufferedReader(new InputStreamReader(csvFile.getInputStream(),
                StandardCharsets.UTF_8));
        reader.lines()
                .skip(1)
                .map(this::toDto)
                .forEach(cityRepository::save);
    }

    private City toDto(String line) {
        String[] parts = line.split("\t");
        return City.builder()
                .id(escapedLong(parts[0]))
                .name(escaped(parts[1]))
                .latitude(escapedBigDecimal(parts[4]))
                .longitude(escapedBigDecimal(parts[5]))
                .population(escapedBigDecimal(parts[14]))
                .build();
    }

    private String escaped(String value) {
        return value.replaceAll("'", "''");
    }

    private Long escapedLong(String value) {
        return Long.parseLong(escaped(value));
    }

    private BigDecimal escapedBigDecimal(String value) {
        return new BigDecimal(escaped(value));
    }
}
