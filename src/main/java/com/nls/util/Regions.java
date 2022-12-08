package com.nls.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class Regions {
    public enum Name {
        NorthAmerica,
        SouthAmerica,
        Europe,
        AfricaAndMiddleEast,
        AsiaPacific
    }

    private static final Regions DEFAULT_REGION = new Regions();

    private final Map<Name, Region> regionMap;

    private Regions() {
        this(Countries.getDefault());
    }

    private Regions(Countries countries) {
        regionMap = build(countries);
    }

    private static Map<Name, Region> build(Countries countries) {
        Map<Name, Region> map = new HashMap<>();
        map.put(Name.NorthAmerica, new Region(countries, Name.NorthAmerica, "North America",
                "USA", "CAN"));
        map.put(Name.SouthAmerica, new Region(countries, Name.SouthAmerica, "South America",
                "BRA"));
        map.put(Name.Europe, new Region(countries, Name.Europe, "Europe",
                "GBR", "AUT", "BEL", "FRA", "DEU", "IRL", "ITA", "POL",
                "PRT", "ESP", "CHE", "GRC"));
        map.put(Name.AfricaAndMiddleEast, new Region(countries, Name.AfricaAndMiddleEast, "Africa & Middle East",
                "KEN", "MAR", "NGA", "SAU", "ZAF", "ARE", "MOZ", "NAM", "TZA", "ZWE", "ZMB"));
        map.put(Name.AsiaPacific, new Region(countries, Name.AsiaPacific, "Asia Pacific",
                "AUS", "CHN", "HKG", "IDN", "MYS", "PHL", "SGP", "THA"));

        return Collections.unmodifiableMap(map);
    }

    public Region getRegion(Name name) {
        return regionMap.get(name);
    }

    /**
     * @deprecated - use instance of Region and Regions.getRegion()
     */
    @Deprecated
    public static Region get(Name name) {
        return DEFAULT_REGION.getRegion(name);
    }

}
