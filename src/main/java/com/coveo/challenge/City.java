package com.coveo.challenge;

/**
 * Class representing a city and it's attributes.
 */
public class City
{
    /**
     * The unique id of the city.
     */
    public Number id;

    /**
     * The city name.
     */
    public String name;

    /**
     * The city name in ascii (for foreign regions).
     */
    public String ascii;

    /**
     * The alternative name.
     */
    public String alt_name;

    /**
     * The latitude.
     */
    public Float latitude;

    /**
     * The longitude.
     */
    public Float longitude;

    /**
     * The country code.
     */
    public String country;

    /**
     * The state or province.
     */
    public String admin1;

    /**
     * The population.
     */
    public Number population;

    /**
     * The city elevation.
     */
    public Number elevation;

    /**
     * The timezone.
     */
    public String tz;

    /**
     * The date the data was updated.
     */
    public String modified_at;

    /**
     * The geoname class. (see: http://www.geonames.org/export/codes.html )
     */
    public String feat_class;

    /**
     * The geoname code. (see: http://www.geonames.org/export/codes.html )
     */
    public String feat_code;

    // Un-documented attributes
    public String cc2;
    public String dem;
    public String admin2;
    public String admin3;
    public String admin4;
}
