package com.nls.web

import spock.lang.Specification
import spock.lang.Unroll

class CspValueSpec extends Specification {

    @Unroll("I can create directives for a value #value")
    def "I can create directives for a value"() {
        when:
        CspValue result = entity;

        then:
        result.directives.empty
        result.value == value

        when:
        result.childSrc()

        then:
        result.directives == ['child-src'] as Set
        result.directiveMap.get('child-src') == value

        when:
        result.connectSrc()

        then:
        result.directives == ['child-src', 'connect-src'] as Set
        result.directiveMap.get('connect-src') == value

        when:
        result.defaultSrc()

        then:
        result.directives == ['child-src', 'connect-src', 'default-src'] as Set
        result.directiveMap.get('default-src') == value

        when:
        result.fontSrc()

        then:
        result.directives == ['child-src', 'connect-src', 'default-src', 'font-src'] as Set
        result.directiveMap.get('font-src') == value

        when:
        result.frameSrc()

        then:
        result.directives == ['child-src', 'connect-src', 'default-src', 'font-src', 'frame-src'] as Set
        result.directiveMap.get('frame-src') == value

        when:
        result.imgSrc()

        then:
        result.directives == ['child-src', 'connect-src', 'default-src', 'font-src', 'frame-src', 'img-src'] as Set
        result.directiveMap.get('img-src') == value

        when:
        result.manifestSrc()

        then:
        result.directives == ['child-src', 'connect-src', 'default-src', 'font-src', 'frame-src', 'img-src', 'manifest-src'] as Set
        result.directiveMap.get('manifest-src') == value

        when:
        result.mediaSrc()

        then:
        result.directives == ['child-src', 'connect-src', 'default-src', 'font-src', 'frame-src', 'img-src', 'manifest-src', 'media-src'] as Set
        result.directiveMap.get('media-src') == value

        when:
        result.objectSrc()

        then:
        result.directives == ['child-src', 'connect-src', 'default-src', 'font-src', 'frame-src', 'img-src', 'manifest-src', 'media-src', 'object-src'] as Set
        result.directiveMap.get('object-src') == value

        when:
        result.prefetchSrc()

        then:
        result.directives == ['child-src', 'connect-src', 'default-src', 'font-src', 'frame-src', 'img-src', 'manifest-src', 'media-src', 'object-src', 'prefetch-src'] as Set
        result.directiveMap.get('prefetch-src') == value

        when:
        result.scriptSrc()

        then:
        result.directives == ['child-src', 'connect-src', 'default-src', 'font-src', 'frame-src', 'img-src', 'manifest-src', 'media-src', 'object-src', 'prefetch-src', 'script-src'] as Set
        result.directiveMap.get('script-src') == value

        when:
        result.styleSrc()

        then:
        result.directives == ['child-src', 'connect-src', 'default-src', 'font-src', 'frame-src', 'img-src', 'manifest-src', 'media-src', 'object-src', 'prefetch-src', 'script-src', 'style-src'] as Set
        result.directiveMap.get('style-src') == value

        when:
        result.scriptSrcElem()

        then:
        result.directives == ['child-src', 'connect-src', 'default-src', 'font-src', 'frame-src', 'img-src', 'manifest-src', 'media-src', 'object-src', 'prefetch-src', 'script-src', 'style-src', 'script-src-elem'] as Set
        result.directiveMap.get('script-src-elem') == value

        when:
        result.styleSrc()

        then:
        result.directives == ['child-src', 'connect-src', 'default-src', 'font-src', 'frame-src', 'img-src', 'manifest-src', 'media-src', 'object-src', 'prefetch-src', 'script-src', 'style-src', 'script-src-elem', 'style-src'] as Set
        result.directiveMap.get('style-src') == value

        when:
        result.styleSrcElem()

        then:
        result.directives == ['child-src', 'connect-src', 'default-src', 'font-src', 'frame-src', 'img-src', 'manifest-src', 'media-src', 'object-src', 'prefetch-src', 'script-src', 'style-src', 'script-src-elem', 'style-src', 'style-src-elem'] as Set
        result.directiveMap.get('style-src-elem') == value

        when:
        result.workerSrc()

        then:
        result.directives == ['child-src', 'connect-src', 'default-src', 'font-src', 'frame-src', 'img-src', 'manifest-src', 'media-src', 'object-src', 'prefetch-src', 'script-src', 'style-src', 'script-src-elem', 'style-src', 'style-src-elem', 'worker-src'] as Set
        result.directiveMap.get('worker-src') == value

        when:
        result.formAction()

        then:
        result.directives == ['child-src', 'connect-src', 'default-src', 'font-src', 'frame-src', 'img-src', 'manifest-src', 'media-src', 'object-src', 'prefetch-src', 'script-src', 'style-src', 'script-src-elem', 'style-src', 'style-src-elem', 'worker-src', 'form-action'] as Set
        result.directiveMap.get('form-action') == value

        when:
        result.frameSrc()

        then:
        result.directives == ['child-src', 'connect-src', 'default-src', 'font-src', 'frame-src', 'img-src', 'manifest-src', 'media-src', 'object-src', 'prefetch-src', 'script-src', 'style-src', 'script-src-elem', 'style-src', 'style-src-elem', 'worker-src', 'form-action', 'frame-src'] as Set
        result.directiveMap.get('frame-src') == value

        when:
        result.frameAncestors()

        then:
        result.directives == ['child-src', 'connect-src', 'default-src', 'font-src', 'frame-src', 'img-src', 'manifest-src', 'media-src', 'object-src', 'prefetch-src', 'script-src', 'style-src', 'script-src-elem', 'style-src', 'style-src-elem', 'worker-src', 'form-action', 'frame-src', 'frame-ancestors'] as Set
        result.directiveMap.get('frame-ancestors') == value

        result.directiveMap.keySet() == result.directives

        where:
        value               | entity
        'https://value.com' | new CspValue('https://value.com')
        '\'self\''              | CspValue.self()
        '\'none\''              | CspValue.none()
        '\'unsafe-inline\''     | CspValue.unsafeInline()
        '\'unsafe-eval\''       | CspValue.unsafeEval()
    }
}
