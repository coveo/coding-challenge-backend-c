package com.company.challenge.initializer;

import com.company.challenge.entity.City;
import com.company.challenge.repository.CityRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.core.io.Resource;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CityInitializerImplTest {

    private CityInitializer cityInitializer;

    private Resource csvFile;
    private CityRepository cityRepository;

    @BeforeEach
    void setUp() {
        csvFile = mock(Resource.class);
        cityRepository = mock(CityRepository.class);
        cityInitializer = new CityInitializerImpl(csvFile, cityRepository);
    }

    @SneakyThrows
    private void mockCsvResourceInputStream() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data/cities_canada-usa.csv");
        doReturn(inputStream).when(csvFile).getInputStream();
    }

    @Test
    void initialize() {
        mockCsvResourceInputStream();


        cityInitializer.initialize();


        ArgumentCaptor<City> captor = ArgumentCaptor.forClass(City.class);
        verify(cityRepository, times(2)).save(captor.capture());

        List<City> savedCities = captor.getAllValues();
        assertEquals(2, savedCities.size());
        assertEquals(Long.valueOf(5881791L), savedCities.get(0).getId());
        assertEquals("Abbotsford", savedCities.get(0).getName());
        assertEquals(new BigDecimal("49.05798"), savedCities.get(0).getLatitude());
        assertEquals(new BigDecimal("-122.25257"), savedCities.get(0).getLongitude());
        assertEquals(new BigDecimal("151683"), savedCities.get(0).getPopulation());
        assertEquals(Long.valueOf(5882142L), savedCities.get(1).getId());
        assertEquals("Acton Vale", savedCities.get(1).getName());
        assertEquals(new BigDecimal("45.65007"), savedCities.get(1).getLatitude());
        assertEquals(new BigDecimal("-72.56582"), savedCities.get(1).getLongitude());
        assertEquals(new BigDecimal("5135"), savedCities.get(1).getPopulation());
    }
}