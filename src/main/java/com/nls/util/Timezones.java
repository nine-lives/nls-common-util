package com.nls.util;

import org.joda.time.DateTimeUtils;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

import java.util.List;
import java.util.stream.Collectors;

public final class Timezones {

    private static final long CNOW = DateTimeUtils.currentTimeMillis();

    private static final DateTimeFormatter COFFSETFORMATTER = new DateTimeFormatterBuilder()
            .appendTimeZoneOffset(null, true, 2, 4)
            .toFormatter();

    private static final List<ZoneData> TIMEZONES = build();

    private Timezones() {

    }

    private static List<ZoneData> build() {
        return DateTimeZone.getAvailableIDs().stream()
                .map(id -> new ZoneData(id, DateTimeZone.forID(id)))
                .filter(ZoneData::isCanonical)
                .sorted()
                .collect(Collectors.toList());

    }

    public static List<ZoneData> getTimezones() {
        return TIMEZONES;
    }

    public static class ZoneData implements Comparable {
        private final String iID;
        private final DateTimeZone iZone;

        ZoneData(String id, DateTimeZone zone) {
            iID = id;
            iZone = zone;
        }

        public String getId() {
            return iID;
        }

        public String getCanonicalId() {
            return iZone.getID();
        }

        public boolean isCanonical() {
            return getId().equals(getCanonicalId());
        }

        public String getStandardOffsetStr() {
            long millis = CNOW;
            while (iZone.getOffset(millis) != iZone.getStandardOffset(millis)) {
                long next = iZone.nextTransition(millis);
                if (next == millis) {
                    break;
                }
                millis = next;
            }
            return COFFSETFORMATTER.withZone(iZone).print(millis);
        }

        public int compareTo(Object obj) {
            ZoneData other = (ZoneData) obj;

            int offsetA = iZone.getStandardOffset(CNOW);
            int offsetB = other.iZone.getStandardOffset(CNOW);

            if (offsetA < offsetB) {
                return -1;
            }
            if (offsetA > offsetB) {
                return 1;
            }

            int result = getCanonicalId().compareTo(other.getCanonicalId());

            if (result != 0) {
                return result;
            }

            if (isCanonical()) {
                if (!other.isCanonical()) {
                    return -1;
                }
            } else if (other.isCanonical()) {
                return 1;
            }

            return getId().compareTo(other.getId());
        }
    }
}
