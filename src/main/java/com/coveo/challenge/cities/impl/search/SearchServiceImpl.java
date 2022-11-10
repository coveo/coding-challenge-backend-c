package com.coveo.challenge.cities.impl.search;

import com.coveo.challenge.cities.api.SearchService;
import com.coveo.challenge.cities.model.City;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final NavigableMap<String, City> data;

    @Override
    public List<City> search(String queryString) {
        final City city = data.get(queryString);
        if (city != null) {
            return List.of(city);
        }
        final var toKey = buildToKey(queryString);
        return new ArrayList<>(data.subMap(queryString, toKey).values());
    }

    /**
     * Build exclusive to-key by incrementing last character by one,
     *
     * @param fromKey start of the interval
     * @return end of the interval
     */
    private String buildToKey(String fromKey) {
        final char[] chars = fromKey.toCharArray().clone();
        chars[chars.length - 1] = (char) (chars[chars.length - 1] + (byte) 1);
        return new String(chars);
    }
}
