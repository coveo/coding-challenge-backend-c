package com.coveo.challenge.cities.config;

import com.coveo.challenge.cities.impl.database.DataLoader;
import com.coveo.challenge.cities.model.City;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.NavigableMap;

/**
 * Configuration for master data.
 */
@Configuration
@Slf4j
@RequiredArgsConstructor
public class DataConfig {

    private final ConfigProperties configProperties;

    private final DataLoader dataLoader;

    @Bean
    NavigableMap<String, City> loadData() throws IOException {
        return dataLoader.loadData(configProperties.getFilepath());
    }
}
