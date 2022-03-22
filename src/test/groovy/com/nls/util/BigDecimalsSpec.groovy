package com.nls.util

import spock.lang.Specification
import spock.lang.Unroll

import java.util.function.Function

class BigDecimalsSpec extends Specification {
    @Unroll("I can check a BigDecimal is zero for #value")
    def "I can check a BigDecimal is zero"() {
        when:
        BigDecimal bd = new BigDecimal(value)

        then:
        BigDecimals.isZero(bd) == expected
        BigDecimals.isNotZero(bd) != expected

        where:
        value     | expected
        '0'       | true
        '0.0000'  | true
        '0.0001'  | false
        '-0.0001' | false
    }

    @Unroll("I can test if two BigDecimals are equal for #left and #right")
    def "I can test if two BigDecimals are equal"() {
        when:
        boolean result = BigDecimals.equal(left == null ? null : new BigDecimal(left), right == null ? null : new BigDecimal(right))

        then:
        result == expected

        where:
        left      | right     | expected
        '0'       | '0.0000'  | true
        '1.1000'  | '1.1'     | true
        '-1.1'    | '-1.100'  | true
        '-0.0001' | '0.0001'  | false
        '0.0001'  | '0.00011' | false
        null      | null      | true
        null      | '1.1'     | false
        '1.1'     | null      | false
    }

    @Unroll("I can test if two BigDecimals are equal when scaled for #left and #right")
    def "I can test if two BigDecimals are equal when scaled"() {
        when:
        boolean result = BigDecimals.equal(new BigDecimal(left), new BigDecimal(right), 2)

        then:
        result == expected

        where:
        left     | right    | expected
        '0'      | '0.0000' | true
        '1.111'  | '1.112'  | true
        '-1.111' | '-1.112' | true
        '1.116'  | '1.114'  | false
    }

    def "I can try and parse a string to BigDecimal without an error for #value"() {
        when:
        BigDecimal result = BigDecimals.tryParse(value)

        then:
        (expected == null && result == null) || result.compareTo(expected) == 0

        where:
        value      | expected
        '12.45'    | new BigDecimal('12.45')
        ' 12.45 '  | new BigDecimal('12.45')
        ' -12.45 ' | new BigDecimal('-12.45')
        'xxx'      | null
        ''         | null
        null       | null
    }

    def "I can convert null to zero"() {
        when:
        BigDecimal d1 = BigDecimals.nullToZero(null)
        BigDecimal d2 = BigDecimals.nullToZero(BigDecimal.ONE)

        then:
        d1 == BigDecimal.ZERO
        d2 == BigDecimal.ONE
    }

    @Unroll("I can test if the value is positive #value")
    def "I can test if the value is positive"() {
        when:
        boolean result = BigDecimals.isPositive(new BigDecimal(value))

        then:
        result == expected

        where:
        value       | expected
        '0.000001'  | true
        '0.000000'  | false
        '-0.000001' | false
    }

    @Unroll("I can test if the value is negative #value")
    def "I can test if the value is negative"() {
        when:
        boolean result = BigDecimals.isNegative(new BigDecimal(value))

        then:
        result == expected

        where:
        value       | expected
        '0.000001'  | false
        '0.000000'  | false
        '-0.000001' | true
    }

    @Unroll("I can test if the value is positive or zero #value")
    def "I can test if the value is positive or zero"() {
        when:
        boolean result = BigDecimals.isPositiveOrZero(new BigDecimal(value))

        then:
        result == expected

        where:
        value       | expected
        '0.000001'  | true
        '0.000000'  | true
        '-0.000001' | false
    }

    @Unroll("I can test if the value is negative or zero #value")
    def "I can test if the value is negative or zero"() {
        when:
        boolean result = BigDecimals.isNegativeOrZero(new BigDecimal(value))

        then:
        result == expected

        where:
        value       | expected
        '0.000001'  | false
        '0.000000'  | true
        '-0.000001' | true
    }

    def "I can sum a collection of BigDecimal"() {
        given:
        List<BigDecimal> values = [1.1G, 2.2G, 3.3G]

        when:
        BigDecimal result = BigDecimals.sum(values, Function.identity())

        then:
        result == 6.6G
    }
}
