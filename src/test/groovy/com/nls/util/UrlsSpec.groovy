package com.nls.util

import spock.lang.Specification
import spock.lang.Unroll

class UrlsSpec extends Specification {

    @Unroll("I can split a query string - #url")
    def "I can split a query string"() {
        when:
        Map<String, List<String>> result = Urls.splitQuery(new URL(url));

        then:
        result == expected

        where:
        url                                 | expected
        "https://9ls.com"                   | [:]
        "https://9ls.com?"                  | [:]
        "https://9ls.com?one=1"             | [one: ['1']]
        "https://9ls.com?one=1&two=2"       | [one: ['1'], two: ['2']]
        "https://9ls.com?one=1&two=2&one=3" | [one: ['1', '3'], two: ['2']]
        "https://9ls.com?one"               | [one: [null]]
        "https://9ls.com?one="              | [one: [null]]
    }
}
