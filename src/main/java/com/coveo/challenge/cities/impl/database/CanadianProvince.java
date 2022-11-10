package com.coveo.challenge.cities.impl.database;

import java.util.Arrays;

/**
 * This should be really not hardcoded, but loaded from the data source, in our case from files.
 */
public enum CanadianProvince {

    AB("Alberta", 1),
    BC("British Columbia", 2),
    MB("Manitoba", 3),
    NB("New Brunswick", 4),
    NT("Northwest Territories", 13),
    NS("Nova Scotia", 7),
    NU("Nunavut", 14),
    ON("Ontario", 8),
    PE("Prince Edward Island", 9),
    QC("Quebec", 10),
    SK("Saskatchewan", 11),
    YK("Yukon", 12),
    NL("Newfoundland and Labrador", 5);

    /**
     * Human readable province name.
     */
    private final String name;

    /**
     * Aka admin1 code: number code of the province.
     */
    private final int fipsCode;

    CanadianProvince(String name, int fipsCode) {
        this.name = name;
        this.fipsCode = fipsCode;
    }

    static CanadianProvince ofFipsCode(int fipsCode) {
        return Arrays.stream(CanadianProvince.values())
                .filter(canadianProvince -> canadianProvince.fipsCode == fipsCode)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Invalid fips code " + fipsCode));
    }

}
