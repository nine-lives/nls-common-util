package com.nls.util

import spock.lang.Specification

class ArrayUtilSpec extends Specification {

    def "I can create and fill an array"() {
        when:
        String[] result = ArrayUtil.create(5, String.class, p -> String.valueOf("i:" + p))

        then:
        result.length == 5
        result[0] == 'i:' + 0
        result[1] == 'i:' + 1
        result[2] == 'i:' + 2
        result[3] == 'i:' + 3
        result[4] == 'i:' + 4
    }
}
