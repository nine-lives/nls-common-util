package com.nls.util


import spock.lang.Specification
import spock.lang.Unroll

class GTINSpec extends Specification {
//    private807298008936, EAN 0807298008936

    @Unroll("I can check a GTIN is valid - #gtin")
    def "I can check and EAN is a valid GTIN13"() {
        when:
        boolean result = GTIN.valid(gtin)

        then:
        expected == result

        where:
        gtin            | expected
        '807298008936'  | true
        '0807298008936' | true
        '4002051699123' | true
    }
}
