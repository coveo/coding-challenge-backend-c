package com.coveo.challenge.cities.search;

import com.coveo.challenge.cities.model.City;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SearchServiceImplTest {

    @Test
    void searchExact() {
        // given a full map
        SearchServiceImpl searchService = prepareLondons();

        // when searching for the exact match
        final List<City> cities = searchService.search("London, KY, USA");

        // then just one result - the exact match
        assertNotNull(cities);
        assertEquals(1, cities.size());
        assertEquals(City.of(3L, "London, KY, USA", BigDecimal.valueOf(37.12898d), BigDecimal.valueOf(-84.08326d)),
                cities.get(0));
    }

    @Test
    void searchWildcardMultiple() {
        // given a full map
        SearchServiceImpl searchService = prepareLondons();

        // when searching for a wildcard and there are two matches
        final List<City> cities = searchService.search("London, O");

        // then the result is the two matches
        assertNotNull(cities);
        assertEquals(2, cities.size());
        final Set<String> cityNames = cities.stream().map(City::getName).collect(Collectors.toSet());
        assertEquals(Set.of("London, ON, Canada", "London, OH, USA"), cityNames);
    }

    @Test
    void searchWildcardSingle() {
        // given a full map
        SearchServiceImpl searchService = prepareLondons();

        // when searching for a wildcard and there is only one partly match
        final List<City> cities = searchService.search("London, ON");

        // then the result is the two matches
        assertNotNull(cities);
        assertEquals(1, cities.size());
        final Set<String> cityNames = cities.stream().map(City::getName).collect(Collectors.toSet());
        assertEquals(Set.of("London, ON, Canada"), cityNames);
    }

    private SearchServiceImpl prepareLondons() {
        NavigableMap<String, City> data = new DataBuilder()
                .add(City.of(1L, "London, ON, Canada", BigDecimal.valueOf(42.98339d), BigDecimal.valueOf(-81.23304d)))
                .add(City.of(2L, "London, OH, USA", BigDecimal.valueOf(39.88645d), BigDecimal.valueOf(-83.44825d)))
                .add(City.of(3L, "London, KY, USA", BigDecimal.valueOf(37.12898d), BigDecimal.valueOf(-84.08326d)))
                .add(City.of(4L, "Londontowne, MD, USA", BigDecimal.valueOf(38.93345d), BigDecimal.valueOf(-76.54941d)))
                .build();

        SearchServiceImpl searchService = new SearchServiceImpl(data);
        return searchService;
    }

    private class DataBuilder {

        private final TreeMap<String, City> data = new TreeMap<>();

        public DataBuilder add(City city) {
            data.put(city.getName(), city);
            return this;
        }

        public NavigableMap<String, City> build() {
            return Collections.unmodifiableNavigableMap(data);
        }
    }
}