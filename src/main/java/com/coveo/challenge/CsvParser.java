package com.coveo.challenge;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import org.springframework.stereotype.Repository;

/**
 * Parse CSV files
 */
@Repository
public class CsvParser
{
    /**
     * Read and parse the cities from a TSV file.
     * @param file The file that contains all the cities separated by tabs
     * @return An HashMap with the key being the city id and the value the city instance.
     */
    public HashMap<Number, City> readCities(InputStream file)
    {
        BufferedReader reader;
        HashMap<Number, City> cities = new HashMap<>();
        try {
            reader = new BufferedReader(new InputStreamReader(file));

            // Skip the first line.
            reader.readLine();

            // Start reading fields.
            String line = reader.readLine();
            while (line != null) {
                // Because it's a tab separated file, split all the lines on the tab char.
                String[] fields = line.split("\t");

                // Populate the city object with the fields.
                City city = new City();
                city.id = Integer.parseInt(fields[0]);
                city.name = fields[1];
                city.ascii = fields[2];
                city.alt_name = fields[3];
                city.latitude = Float.parseFloat(fields[4]);
                city.longitude = Float.parseFloat(fields[5]);
                city.feat_class = fields[6];
                city.feat_code = fields[7];
                city.country = fields[8];
                city.cc2 = fields[9];
                city.admin1 = fields[10];
                city.admin2 = fields[11];
                city.admin3 = fields[12];
                city.admin4 = fields[13];
                city.population = Integer.parseInt(fields[14]);

                // The elevation is not always present.
                try {
                    city.elevation = Integer.parseInt(fields[15]);
                } catch (NumberFormatException e) {
                    city.elevation = -1;
                }

                city.dem = fields[16];
                city.tz = fields[17];
                city.modified_at = fields[18];

                // Add the city to the return value.
                cities.put(city.id, city);

                // read next line
                line = reader.readLine();
            }

            reader.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return cities;
    }
}
