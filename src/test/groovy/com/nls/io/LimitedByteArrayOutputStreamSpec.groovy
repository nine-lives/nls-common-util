package com.nls.io

import spock.lang.Specification

class LimitedByteArrayOutputStreamSpec extends Specification {
    def "I can get a limited byte array"() {
        given:
        LimitedByteArrayOutputStream result = new LimitedByteArrayOutputStream(10)
        OutputStreamWriter out = new OutputStreamWriter(result)

        when:
        out.write("1234567890abcdedfghijklmnop")
        out.close()

        then:
        result.toString() == '1234567890'
    }

}
