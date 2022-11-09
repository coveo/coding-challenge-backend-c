package com.coveo.challenge.cities.config;

import com.coveo.challenge.cities.model.City;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
@Slf4j
public class DataConfig {

    private static final int ID_IDX = 0;
    private static final int NAME_IDX = 1;
    private static final int LATITUDE_IDX = 4;
    private static final int LONGITUDE_IDX = 5;
    private static final int COUNTRY_IDX = 8;
    private static final int STATE_IDX = 10;

    @Bean
    NavigableMap<String, City> loadData() throws IOException {
        // TODO move to app properties
        File file = new File("data/cities_canada-usa.tsv");
        final Set<City> cities;
        try (var lines = Files.lines(file.toPath()).skip(1)) {
            cities = lines
                    .map(line -> line.split("\t"))
                    .map(fields -> City.of(Long.parseLong(fields[ID_IDX]),
                            fields[NAME_IDX] + ", " +
                                    fields[STATE_IDX] + ", " +
                                    fields[COUNTRY_IDX]
                            , new BigDecimal(fields[LATITUDE_IDX]),
                            new BigDecimal(fields[LONGITUDE_IDX])))
                    .peek(city -> {
                        if (city.getName() == null) {
                            log.warn("Skipping invalid city data {}", city);
                        }
                    })
                    .filter(city -> StringUtils.hasText(city.getName()))
                    // TODO resolve collisions, at least report as warnings
                    .collect(Collectors.toSet());
        }

        // don't want to bother with treemap collector
        return new TreeMap<>(cities.stream().collect(Collectors.toMap(City::getName, Function.identity())));
    }
}
