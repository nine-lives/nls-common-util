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

    private static final Map<Name, Region> REGION_MAP = build();

    private Regions() {

    }

    private static Map<Name, Region> build() {
        Map<Name, Region> map = new HashMap<>();
        map.put(Name.NorthAmerica, new Region(Name.NorthAmerica, "North America",
                "USA", "CAN"));
        map.put(Name.SouthAmerica, new Region(Name.SouthAmerica, "South America",
                "BRA"));
        map.put(Name.Europe, new Region(Name.Europe, "Europe",
                "GBR", "AUT", "BEL", "FRA", "DEU", "IRL", "ITA", "POL",
                "PRT", "ESP", "CHE"));
        map.put(Name.AfricaAndMiddleEast, new Region(Name.AfricaAndMiddleEast, "Africa & Middle East",
                "KEN", "MAR", "NGA", "SAU", "ZAF", "ARE", "MOZ", "NAM", "TZA", "ZWE", "ZMB"));
        map.put(Name.AsiaPacific, new Region(Name.AsiaPacific, "Asia Pacific",
                "AUS", "CHN", "HKG", "IDN", "MYS", "PHL", "SGP", "THA"));

        return Collections.unmodifiableMap(map);
    }

    public static Region get(Name name) {
        return REGION_MAP.get(name);
    }

}
