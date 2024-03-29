package com.nls.util

import org.joda.time.DateTime
import spock.lang.Specification

class DateTimeRangeSetSpec extends Specification {

    def "I can add disconnected"() {
        when:
            DateTimeRangeSet g = new DateTimeRangeSet();
            g.add(new DateTimeRange(
                    DateTime.parse('2017-08-14T10:30:00'),
                    DateTime.parse('2017-08-14T11:30:00')));
            g.add(new DateTimeRange(
                    DateTime.parse('2017-08-14T11:30:01'),
                    DateTime.parse('2017-08-14T12:30:00')));
        then:
            g.ranges.size() == 2
            g.seconds.seconds == 3600 + 3599
    }

    def "I can merge abutting"() {
        when:
        DateTimeRangeSet g = new DateTimeRangeSet();
        g.add(new DateTimeRange(
                DateTime.parse('2017-08-14T10:30:00'),
                DateTime.parse('2017-08-14T11:30:00')));
        g.add(new DateTimeRange(
                DateTime.parse('2017-08-14T11:30:00'),
                DateTime.parse('2017-08-14T12:30:00')));
        then:
        g.ranges.size() == 1
        g.seconds.seconds == 7200
    }

    def "I can add instersected"() {
        when:
        DateTimeRangeSet g = new DateTimeRangeSet();
        g.add(new DateTimeRange(
                DateTime.parse('2017-08-14T10:30:00'),
                DateTime.parse('2017-08-14T11:30:00')));
        g.add(new DateTimeRange(
                DateTime.parse('2017-08-14T11:00:00'),
                DateTime.parse('2017-08-14T12:00:00')));
        then:
        g.ranges.size() == 1
        g.seconds.seconds == 5400
        g.ranges[0].from == DateTime.parse('2017-08-14T10:30:00')
        g.ranges[0].to == DateTime.parse('2017-08-14T12:00:00')
    }

    def "I can add overlap"() {
        when:
        DateTimeRangeSet g = new DateTimeRangeSet();
        g.add(new DateTimeRange(
                DateTime.parse('2017-08-14T11:00:00'),
                DateTime.parse('2017-08-14T12:00:00')));
        g.add(new DateTimeRange(
                DateTime.parse('2017-08-14T12:00:01'),
                DateTime.parse('2017-08-14T12:30:00')));
        g.add(new DateTimeRange(
                DateTime.parse('2017-08-14T10:30:00'),
                DateTime.parse('2017-08-14T12:30:01')));
        then:
        g.ranges.size() == 1
        g.seconds.seconds == 7201
        g.ranges[0].from == DateTime.parse('2017-08-14T10:30:00')
        g.ranges[0].to == DateTime.parse('2017-08-14T12:30:01')
    }

    def "I can add overlap reverse"() {
        when:
        DateTimeRangeSet g = new DateTimeRangeSet();
        g.add(new DateTimeRange(
                DateTime.parse('2017-08-14T10:30:00'),
                DateTime.parse('2017-08-14T12:30:01')));
        g.add(new DateTimeRange(
                DateTime.parse('2017-08-14T11:00:00'),
                DateTime.parse('2017-08-14T12:00:00')));
        g.add(new DateTimeRange(
                DateTime.parse('2017-08-14T12:00:01'),
                DateTime.parse('2017-08-14T12:30:00')));
        then:
        g.ranges.size() == 1
        g.seconds.seconds == 7201
        g.ranges[0].from == DateTime.parse('2017-08-14T10:30:00')
        g.ranges[0].to == DateTime.parse('2017-08-14T12:30:01')
    }

    def "I can merge graphs"() {
        when:
        DateTimeRangeSet g1 = new DateTimeRangeSet();
        g1.add(new DateTimeRange(
                DateTime.parse('2017-08-14T10:30:00'),
                DateTime.parse('2017-08-14T11:00:00')));
        g1.add(new DateTimeRange(
                DateTime.parse('2017-08-14T13:00:00'),
                DateTime.parse('2017-08-14T13:30:00')));
        DateTimeRangeSet g2 = new DateTimeRangeSet();
        g2.add(new DateTimeRange(
                DateTime.parse('2017-08-14T11:00:00'),
                DateTime.parse('2017-08-14T11:30:00')));
        g2.add(new DateTimeRange(
                DateTime.parse('2017-08-14T13:30:00'),
                DateTime.parse('2017-08-14T14:00:00')));
        DateTimeRangeSet g3 = g1.merge(g2);

        then:
        g3.ranges.size() == 2
        g3.seconds.seconds == 7200
        g3.ranges[0].from == DateTime.parse('2017-08-14T10:30:00')
        g3.ranges[0].to == DateTime.parse('2017-08-14T11:30:00')
        g3.ranges[1].from == DateTime.parse('2017-08-14T13:00:00')
        g3.ranges[1].to == DateTime.parse('2017-08-14T14:00:00')
    }

}
