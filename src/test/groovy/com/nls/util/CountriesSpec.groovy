package com.nls.util

import spock.lang.Specification

class CountriesSpec extends Specification {
    def "I can compare countries"() {
        when:
        Country uk1 = Countries.getAll().findIso('GBR')
        Country uk2 = Countries.getAll().findIso('GBR')
        Country fr = Countries.getAll().findIso('FRA')

        then:
        uk1 == uk2
        uk1.hashCode() == uk2.hashCode()
        uk1.compareTo(uk2) == 0
        uk1.equals(uk2)

        then:
        uk1 != fr
        uk1.hashCode() != fr.hashCode()
        uk1.compareTo(fr) != 0
        !uk1.equals(fr)
    }

    def "I can get all the countries"() {
        when:
        Countries countries = Countries.getAll()

        then:
        countries.getCountries().size() == 250

        when:
        Country country = countries.findIso('GBR')

        then:
        country.iso == 'GBR'
        country.code == 'GB'
        country.name == 'United Kingdom'
        country.getName(Locale.FRANCE) == 'Royaume-Uni'

        when:
        Country country2 = countries.findCode('GB')

        then:
        country == country2

        when:
        Country country3 = countries.findName('United Kingdom')

        then:
        country == country3

        when:
        Country country4 = countries.findWordMap('Kingdom')

        then:
        country == country4

        when:
        Country country5 = countries.findIsoOrCode('GBR')

        then:
        country == country5

        when:
        Country country6 = countries.findIsoOrCode('GB')

        then:
        country == country6

        when:
        Country country7 = countries.find('United Kingdom')

        then:
        country == country7
    }

    def "I can create a restricted country list"() {
        when:
        Countries countries = Countries.build(['USA', 'FRA'] as Set)

        then:
        countries.getCountries().size() == 3

        when:
        Country uk = countries.findIso('GBR')

        then:
        uk == null

        when:
        Country usa = countries.findIso('USA')

        then:
        usa.iso == 'USA'
        usa.code == 'US'
        usa.name == 'United States'
        usa.getName(Locale.FRANCE) == 'États-Unis'

        then:
        countries.countries*.getName(Locale.ENGLISH) == ['Global', 'France', 'United States']

        then:
        countries.getCountries(Locale.FRANCE)*.getName(Locale.FRANCE) == ['Global', 'États-Unis', 'France']

    }

    def "I can change the default"() {
        when:
        Countries.setDefault(Countries.build(['USA', 'FRA'] as Set))
        Countries countries = Countries.getDefault()

        then:
        countries.getCountries().size() == 3
        Countries.getAll().countries.size() == 250

        then:
        Countries.fromIso('GBR') == null
        Countries.getAll().findIso('GBR').name == 'United Kingdom'
        Country.valueOf('GBR').name == 'United Kingdom'
    }

    def cleanup() {
        Countries.setDefault(Countries.getAll())
    }
}
