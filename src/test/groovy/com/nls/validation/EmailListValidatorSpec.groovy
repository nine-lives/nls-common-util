package com.nls.validation


import spock.lang.Specification

class EmailListValidatorSpec extends Specification {

    def "I can validate an email list"() {
        given:
        EmailListValidator validator = new EmailListValidator()

        when:
        boolean result = validator.isValid(emails, null)

        then:
        result == expected

        where:
        emails                                         | expected
        'email1@email.com,email2@email.com'            | true
        'email1@email.com;email2@email.com'            | true
        'email1@email.com\nemail2@email.com'           | true
        'email1&email.com,email2@email.com'            | false
        ''                                             | true
        'John Doe <email1@email.com>,email2@email.com' | false

    }
}
