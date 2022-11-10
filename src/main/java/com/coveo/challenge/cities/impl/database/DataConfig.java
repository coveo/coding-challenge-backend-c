package com.coveo.challenge.cities.impl.database;

import com.coveo.challenge.cities.model.City;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
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

    /**
     * Canada country code.
     */
    public static final String CANADA = "CA";

    @Bean
    NavigableMap<String, City> loadData() throws IOException {
        // TODO move to app properties
        File file = new File("data/cities_canada-usa.tsv");
        final Collection<City> cities;
        try (var lines = Files.lines(file.toPath()).skip(1)) {
            cities = lines
                    .map(line -> line.split("\t"))
                    .map(DataConfig::buildUniqueCityName)
                    .collect(Collectors.toList());
        }

        // don't want to bother with treemap collector
        return new TreeMap<>(removeInvalidData(cities).stream().collect(Collectors.toMap(City::getName,
                Function.identity())));
    }

    private Collection<City> removeInvalidData(Collection<City> cities) {
        Map<String, City> uniqueCities = new HashMap<>();
        for (City city : cities) {
            if (city.getName() == null || !StringUtils.hasText(city.getName())) {
                log.warn("Skipping invalid city: empty name {}", city);
                continue;
            }

            final City old = uniqueCities.put(city.getName(), city);
            if (old != null) {
                log.warn("Skipping invalid city: duplicate {}", city);
            }
        }
        return uniqueCities.values();
    }

    /**
     * Build unique city name using country variations.
     * <p>
     * This should be really generalized, but for simplicity just hardcode special behavior for US and CA.
     *
     * @param input data
     * @return unique city name, can be used as a key for now.
     */
    private static City buildUniqueCityName(String[] input) {
        final long id = Long.parseLong(input[ID_IDX]);
        final String country = input[COUNTRY_IDX];
        final String name = input[NAME_IDX] + ", " +
                (country.equals(CANADA) ?
                        CanadianProvince.ofFipsCode(Integer.parseInt(input[STATE_IDX])).name() :
                        input[STATE_IDX])
                + ", " + country;
        return City.of(id, name, new BigDecimal(input[LATITUDE_IDX]), new BigDecimal(input[LONGITUDE_IDX]));
    }
}
