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

    /**
     * File name source.
     */
    private final ConfigProperties configProperties;

    /**
     * City loader.
     */
    private final DataLoader dataLoader;

    /**
     * Loads list of US and CA cities.
     *
     * @return city list
     * @throws IOException if troubled
     */
    @Bean
    NavigableMap<String, City> loadData() throws IOException {
        return dataLoader.loadData(configProperties.getFilepath());
    }
}
