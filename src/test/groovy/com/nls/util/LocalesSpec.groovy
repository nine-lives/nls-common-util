package com.nls.util

import spock.lang.Specification
import spock.lang.Unroll

class LocalesSpec extends Specification {

    @Unroll("I can convert a language tag to a Locale - #input")
    def "I can convert a language tag to a Locale"() {
        when:
        Locale locale = Locales.forLanguageTag(input)

        then:
        locale == expected

        where:
        input     | expected
        "en"      | new Locale("en")
        "en_GB"   | new Locale("en", "GB")
        "en-GB"   | new Locale("en", "GB")
        " en-GB " | new Locale("en", "GB")
        ""        | null
        null      | null
    }

    @Unroll("I can convert a list of locale strings to Locales - #input")
    def "I can convert a list of locale strings to Locales"() {
        when:
        List<Locale> locales = Locales.forLanguageTags(input)

        then:
        locales.size() == 4
        locales[0].language == "en"
        locales[0].country == ""
        locales[1].language == "en"
        locales[1].country == "GB"
        locales[2].language == "fr"
        locales[2].country == ""
        locales[3].language == "es"
        locales[3].country == "US"

        where:
        test          | input
        "hyphens"     | ["en", "en-GB", "fr", "es-US"] as String[]
        "underscores" | ["en", "en_GB", "fr", "es_US"] as String[]
        "empty slots" | ["en", "en_GB", null, "fr", "  ", "es_US"] as String[]
        "spaces"      | ["en ", " en_GB ", " fr", "  ", "es_US "] as String[]
    }

    @Unroll("I can convert a string of locale strings with a separator to Locales - #input")
    def "I can convert a string of locale strings with a separator to Locales"() {
        when:
        List<Locale> locales = Locales.forLanguageTags(input, ",")

        then:
        locales.size() == 4
        locales[0].language == "en"
        locales[0].country == ""
        locales[1].language == "en"
        locales[1].country == "GB"
        locales[2].language == "fr"
        locales[2].country == ""
        locales[3].language == "es"
        locales[3].country == "US"

        where:
        test          | input
        "hyphens"     | "en,en-GB,fr,es-US"
        "underscores" | "en,en_GB,fr,es_US"
        "empty slots" | "en,,en-GB,,fr,es-US"
        "spaces"      | "en , en-GB ,    fr ,  es-US"
    }

}
