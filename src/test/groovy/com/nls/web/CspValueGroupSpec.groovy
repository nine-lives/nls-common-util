package com.nls.web

import spock.lang.Specification

class CspValueGroupSpec extends Specification {
    def "I can create a value group"() {
        when:
        CspValueGroup child = new CspValueGroup('My Child Group')
                .add(new CspValue('https://value.com').frameSrc().scriptSrc())
                .add(CspValue.self().imgSrc().frameSrc())

        CspValueGroup parent = new CspValueGroup('My Parent Group')
                .add(CspValue.none().formAction())
                .add(child)

        then:
        child.name == 'My Child Group'
        child.values.size() == 2

        parent.name == 'My Parent Group'
        parent.values.size() == 3
        parent.values.find { it.value == 'https://value.com'}.directives == ['frame-src', 'script-src'] as Set
        parent.values.find { it.value == "'self'"}.directives == ['img-src', 'frame-src'] as Set
        parent.values.find { it.value == "'none'"}.directives == ['form-action'] as Set
    }
}
