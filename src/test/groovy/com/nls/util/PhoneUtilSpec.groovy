package com.nls.util

import spock.lang.Specification
import spock.lang.Unroll

class PhoneUtilSpec extends Specification {
    @Unroll("I can test if a number is valid - #phone")
    def "Valid"() {
        when:
        boolean result = PhoneUtil.valid(phone)

        then:
        valid == result

        where:
        phone                 | valid
        null                  | false
        ''                    | false
        ' '                   | false
        '+447879440893'       | true
        '+44 7 87 94 40 8 93' | true
        '07879440893'         | true
        '078 7 944 0893'      | true
        '078 7 944 08'        | false
        '+41446681800'        | true
    }

    @Unroll("I can normalise a number - #phone")
    def "Normalise"() {
        when:
        String result = PhoneUtil.normalise(phone)

        then:
        expected == result

        where:
        phone                 | expected
        null                  | null
        ''                    | ''
        ' '                   | ' '
        '+447879440893'       | '07879440893'
        '+44 7 87 94 40 8 93' | '07879440893'
        '07879440893'         | '07879440893'
        '078 7 944 0893'      | '07879440893'
        '078 7 944 08'        | '078 7 944 08'
        '+4144 668 1800'      | '+41446681800'
        '+41446681800'        | '+41446681800'
        'SOME THING'          | 'SOME THING'
    }

    @Unroll("I can prettify a number - #phone")
    def "Prettify"() {
        when:
        String result = PhoneUtil.prettify(phone)

        then:
        expected == result

        where:
        phone                 | expected
        null                  | null
        ''                    | ''
        ' '                   | ' '
        '+447879440893'       | '07879 440893'
        '+44 7 87 94 40 8 93' | '07879 440893'
        '07879440893'         | '07879 440893'
        '078 7 944 0893'      | '07879 440893'
        '078 7 944 08'        | '078794408'
        '078794408'           | '078794408'
        '+4144 668 1800'      | '+41 44 668 18 00'
        '+41446681800'        | '+41 44 668 18 00'
        'SOME THING'          | 'SOME THING'
    }
}
