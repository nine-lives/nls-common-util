package com.nls.web

import spock.lang.Specification

class CspBuilderSpec extends Specification {
    def "I can build a csp policy with no duplicates"() {
        when:
        CspValueGroup child = new CspValueGroup('My Child Group')
                .add(new CspValue('https://value.com').frameSrc().scriptSrc())
                .add(CspValue.self().imgSrc().frameSrc())

        CspValueGroup group = new CspValueGroup('My Child Group')
                .add(new CspValue('https://value2.com').imgSrc())

        CspValueGroup parent = new CspValueGroup('My Parent Group')
                .add(new CspValue('https://value.com').frameSrc().styleSrc())
                .add(CspValue.none().formAction())
                .add(child)

        CspValue value = new CspValue('https://value2.com').imgSrc()

        CspBuilder builder = new CspBuilder()
            .add(group)
            .add(parent)
            .add(value)
            .reportUri('https://report-uri.com/')

        String result = builder.policy();

        then:
        result == '''form-action 'none';
frame-src 'self' https://value.com;
img-src 'self' https://value2.com;
script-src https://value.com;
style-src https://value.com;
report-uri https://report-uri.com/;'''

    }
}
