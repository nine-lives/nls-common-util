package com.nls.util;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class PublicHolidays {
    private static final Logger LOGGER = LoggerFactory.getLogger(PublicHolidays.class);
    private static final Map<LocalDate, CSVRecord> LOOKUP = init();

    private PublicHolidays() {
    }

    public static boolean isBankHoliday(LocalDate date) {
        CSVRecord record = LOOKUP.get(date);
        return record != null && "1".equals(record.get("Bank Holiday"));
    }

    private static Map<LocalDate, CSVRecord> init() {
        URL data = PublicHolidays.class.getResource("/data/holidays-uk.csv");
        try (Reader in = new InputStreamReader(data.openStream())) {
            return CSVFormat.EXCEL.withFirstRecordAsHeader().withIgnoreSurroundingSpaces()
                    .parse(in)
                    .getRecords()
                    .stream()
                    .filter(o-> "1".equals(o.get("Bank Holiday")))
                    .collect(Collectors.toMap(o -> LocalDate.parse(o.get("Date")), Function.identity()));
        } catch (IOException e) {
            LOGGER.error("Couldn't load public holidays");
            return Collections.emptyMap();
        }
    }

    public static int getHolidayDaysInRange(LocalDate from, LocalDate to) {
        LocalDateRange range = new LocalDateRange(from, to);
        Set<LocalDate> dates = new HashSet<>(range.getDates());
        dates.retainAll(LOOKUP.keySet());
        return dates.size();
    }
}
