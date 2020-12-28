package com.nls.util;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public final class PostcodeUtil {

    private static final String STANDARD = "^([A-Z][A-HJ-Y]?[0-9][A-Z0-9]?[0-9][A-Z]{2})$";
    private static final String BRITISH_FORCES = "^(BFPO[0-9]{1,4})$";
    private static final Map<String, String> SPECIAL = ImmutableMap.<String, String>builder()
            .put("BS981TL", "TV Licensing")
            .put("BX11LT", "Lloyds Bank formerly known as Lloyds TSB Bank")
            .put("BX21LB", "Bank of Scotland (part of Lloyds Banking Group)")
            .put("BX32BB", "Barclays Bank – non-geographic address")
            .put("BX47SB", "TSB Bank")
            .put("BX55AT", "VAT Central Unit of HM Revenue and Customs")
            .put("CF101BH", "Lloyds Banking Group (formerly Black Horse Finance)")
            .put("CF991NA", "National Assembly for Wales")
            .put("CV48UW", "University of Warwick")
            .put("CV350DB", "Aston Martin after their long line of iconic sports cars that bear the \"DB\" moniker")
            .put("DA11RT", "Dartford F.C. (nicknamed The Darts)")
            .put("DE993GG", "Egg Banking")
            .put("DE554SW", "Slimming World")
            .put("DH981BT", "British Telecom")
            .put("DH991NS", "National Savings certificates administration")
            .put("E145HQ", "HSBC headquarters at 8 Canada Square, Canary Wharf")
            .put("E145JP", "JP Morgan")
            .put("E161XL", "ExCeL London[51]")
            .put("E202AQ", "Olympic Aquatics Centre")
            .put("E202BB", "Olympic Basketball Arena")
            .put("E202ST", "Olympic Stadium")
            .put("E203BS", "Olympic Broadcast Centre")
            .put("E203EL", "Olympic Velodrome")
            .put("E203ET", "Olympic Eton Manor Tennis Courts")
            .put("E203HB", "Olympic Handball Arena (now the Copper Box)")
            .put("E203HY", "Olympic Hockey Stadium")
            .put("E981SN", "The Sun newspaper")
            .put("E981ST", "The Sunday Times newspaper")
            .put("E981TT", "The Times newspaper")
            .put("EC2N2DB", "Deutsche Bank")
            .put("EC4Y0HQ", "Royal Mail Group Ltd headquarters")
            .put("EH121HQ", "Royal Bank of Scotland headquarters")
            .put("EH991SP", "Scottish Parliament[52] (founded in 1999)")
            .put("G581SB", "National Savings Bank (the district number 58 also approximates the outline of the initials SB)")
            .put("GIR0AA", "Girobank (now Santander Corporate Banking)")
            .put("IV212LR", "Two Lochs Radio")
            .put("L304GB", "Girobank (alternative geographic postcode)")
            .put("LS981FD", "First Direct bank")
            .put("M502BH", "BBC Bridge House")
            .put("M502QH", "BBC Quay House")
            .put("N19GU", "The Guardian newspaper")
            .put("N811ER", "Electoral Reform Services[42][53]")
            .put("NE14ST", "St James' Park Stadium, Newcastle United")
            .put("NG801EH", "Experian Embankment House")
            .put("NG801LH", "Experian Lambert House")
            .put("NG801RH", "Experian Riverleen House")
            .put("NG801TH", "Experian Talbot House")
            .put("PH15RB", "Royal Bank of Scotland Perth Chief Office")
            .put("PH12SJ", "St Johnstone Football Club")
            .put("S24SU", "Sheffield United Football Club")
            .put("S61SW", "Sheffield Wednesday Football Club")
            .put("S147UP", "The World Snooker Championships at the Crucible Theatre, Sheffield;[54] 147 UP refers to a " +
                    "maximum lead (from a maximum break) in snooker")
            .put("SA99", "Driver and Vehicle Licensing Agency All postcodes starting with SA99 are for the DVLA offices " +
                    "in the Morriston area of Swansea, the final part of the postcode relates to the specific office or department within the DVLA")
            .put("SE10NE", "One America Street, the London headquarters of architectural firm TP Bennett")
            .put("SE18UJ", "Union Jack Club")
            .put("SM60HB", "Homebase Limited")
            .put("SN381NW", "Nationwide Building Society")
            .put("SR51SU", "Stadium of Light, Sunderland AFC")
            .put("SW1A0AA", "House of Commons")
            .put("SW1A0PW", "House of Lords (Palace of Westminster; see above for House of Commons)")
            .put("SW1A1AA", "Buckingham Palace (the Monarch)")
            .put("SW1A2AA", "10 Downing Street (the Prime Minister)")
            .put("SW1A2AB", "11 Downing Street (Chancellor of the Exchequer)")
            .put("SW1H0TL", "Transport for London (Windsor House, 50 Victoria Street)")
            .put("SW1P3EU", "European Commission and European Parliament office (European Union)")
            .put("SW1W0DT", "The Daily Telegraph newspaper")
            .put("TW89GS", "GlaxoSmithKline")
            .put("W1A1AA", "BBC Broadcasting House")
            .put("W1D4FA", "Betgenius, the former address of The Football Association")
            .put("W1N4DJ", "BBC Radio 1 (disc jockey)")
            .put("W1T1FB", "Facebook")
            .put("CO43SQ", "University of Essex (Square 3)")
            .build();

    private PostcodeUtil() {
    }

    public static boolean valid(String postcodeRaw) {
        if (postcodeRaw == null) {
            return false;
        }

        String postcode = normalise(postcodeRaw);

        if (postcode.matches(STANDARD)) {
            return true;
        }
        if (postcode.matches(BRITISH_FORCES)) {
            return true;
        }

        return SPECIAL.containsKey(postcodeRaw);
    }

    public static String normalise(String postcodeRaw) {
        if (postcodeRaw == null) {
            return null;
        }

        return postcodeRaw.replaceAll(" ", "").toUpperCase();
    }

    public static String prettify(String postcodeRaw) {
        if (!valid(postcodeRaw)) {
            return postcodeRaw;
        }

        String postcode = normalise(postcodeRaw);

        if (postcode.matches(BRITISH_FORCES)) {
            return postcode.substring(0, 5) + " " + postcode.substring(5, postcode.length());
        }

        if (postcode.matches("SA99")) {
            return postcode;
        }

        int inwardStart = postcode.length() - 3;
        return postcode.substring(0, inwardStart) + " " + postcode.substring(inwardStart, postcode.length());
    }
}
