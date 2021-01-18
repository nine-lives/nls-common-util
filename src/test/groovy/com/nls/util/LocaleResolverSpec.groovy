package com.nls.util

import spock.lang.Specification
import spock.lang.Unroll

class LocaleResolverSpec extends Specification {
    @Unroll("I can resolve the best locale - #acceptLang")
    def "I can resolve the best locale"() {
        given:
        LocaleResolver resolver = new LocaleResolver(Locales.forLanguageTag(defaultLocale), "en, en-GB, en-US, fr, fr_FR", ",")

        when:
        Locale locale = resolver.resolve(acceptLang)

        then:
        locale == Locales.forLanguageTag(expected)

        where:
        defaultLocale | acceptLang                                                  | expected
        "de_DE"       | "en-GB, en, fr-CH, fr;q=0.8, de;q=0.7, *;q=0.5"             | "en-GB"
        "de_DE"       | "en-GB;q=0.9, en, fr-CH;q=0.9, fr;q=0.8, de;q=0.7, *;q=0.5" | "en"
        "de_DE"       | "en;q=0.9, de;q=0.7, *;q=0.5"                               | "en"
        "de_DE"       | "fr-CH;q=0.9, de;q=0.7, *;q=0.5"                            | "fr"
        "de_DE"       | "es-ES;q=0.9, de;q=0.7, *;q=0.5"                            | "de-DE"
    }
}
