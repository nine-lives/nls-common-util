package com.nls.util

import spock.lang.Specification
import spock.lang.Unroll

class BacsUtilSpec extends Specification {

    @Unroll("I can test if a bacs account is valid or empty #sort #account")
    def "I can test if a bacs account is valid or empty"() {
        when:
        boolean result = BacsUtil.validOrEmpty(sort, account)

        then:
        result == expected

        where:
        sort       | account    | expected
        '08-99-99' | '66374958' | true
        '20-59-59' | '99999999' | false
        '12-34'    | '66374958' | false
        null       | '66374958' | false
        '20-59-59' | ''         | false
        '20-59-59' | null       | false
        ''         | ''         | true
        '  '       | '  '       | true
        null       | null       | true
        '  '       | null       | true
        null       | '  '       | true
    }

    @Unroll("I can test if a bacs account is valid #sort #account")
    def "I can test if a bacs account is valid"() {
        when:
        boolean result = BacsUtil.valid(sort, account)

        then:
        result == expected

        where:
        sort       | account    | expected
        '08-99-99' | '66374958' | true
        '20-59-59' | '99999999' | false
        '12-34'    | '66374958' | false
        null       | '66374958' | false
        '20-59-59' | ''         | false
        '20-59-59' | null       | false
        ''         | ''         | false
        '  '       | '  '       | false
        null       | null       | false
        '  '       | null       | false
        null       | '  '       | false
    }

    @Unroll("I can prettify the sort code #sort")
    def "I can prettify the sort code"() {
        when:
        String result = BacsUtil.prettifySortCode(sort)

        then:
        result == expected

        where:
        sort       | expected
        '08-99-99' | '08-99-99'
        '08 99 99' | '08-99-99'
        '089999'   | '08-99-99'
        '08-99'    | ''
        '    '     | ''
        null       | ''
    }

    @Unroll("I can normalise sort codes and accounts #value")
    def "I can normalise sort codes and accounts"() {
        when:
        String result = BacsUtil.normalise(value)

        then:
        result == expected

        where:
        value       | expected
        '08-99-99' | '089999'
        '08 asdadsdad 99 asdadd 99' | '089999'
        '    '     | ''
        null       | ''
    }
}
