package com.challage.generation;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CsvToSqlInsertGenerator {

    private static final String PATTERN = "INSERT INTO cities (name, latitude, longitude, population) VALUES (%s, %s, %s, %s);";

    public static void main(String[] args) {
        new CsvToSqlInsertGenerator().generate();
    }

    /**
     * Generated result is used to create SQL inserts to populate initial data in db
     */
    public void generate() {
        InputStream stream = getClass().getClassLoader().getResourceAsStream("cities_canada-usa.tsv");
        System.out.println("******************************************");
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        reader.lines()
                .skip(1)
                .map(this::generateSqlInsert)
                .forEach(System.out::println);
        System.out.println("******************************************");
    }

    private String generateSqlInsert(String line) {
        String[] parts = line.split("\t");
        return String.format(PATTERN, parts[1], parts[4], parts[5], parts[14]);
    }
}
