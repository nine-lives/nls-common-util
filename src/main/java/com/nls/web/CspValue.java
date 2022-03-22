package com.nls.web;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CspValue {
    private final String value;
    private final Set<String> directives = new HashSet<>();

    public CspValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public Set<String> getDirectives() {
        return Collections.unmodifiableSet(directives);
    }

    public Map<String, String> getDirectiveMap() {
        return directives.stream().collect(Collectors.toMap(Function.identity(), o -> getValue()));
    }

    public static CspValue self() {
        return new CspValue("'self'");
    }

    public static CspValue none() {
        return new CspValue("'none'");
    }

    public static CspValue unsafeInline() {
        return new CspValue("'unsafe-inline'");
    }

    public static CspValue unsafeEval() {
        return new CspValue("'unsafe-eval'");
    }

    public CspValue childSrc() {
        directives.add("child-src");
        return this;
    }

    public CspValue connectSrc() {
        directives.add("connect-src");
        return this;
    }

    public CspValue defaultSrc() {
        directives.add("default-src");
        return this;
    }

    public CspValue fontSrc() {
        directives.add("font-src");
        return this;
    }

    public CspValue frameSrc() {
        directives.add("frame-src");
        return this;
    }

    public CspValue imgSrc() {
        directives.add("img-src");
        return this;
    }

    public CspValue manifestSrc() {
        directives.add("manifest-src");
        return this;
    }

    public CspValue mediaSrc() {
        directives.add("media-src");
        return this;
    }

    public CspValue objectSrc() {
        directives.add("object-src");
        return this;
    }

    public CspValue prefetchSrc() {
        directives.add("prefetch-src");
        return this;
    }

    public CspValue scriptSrc() {
        directives.add("script-src");
        return this;
    }

    public CspValue scriptSrcElem() {
        directives.add("script-src-elem");
        return this;
    }

    public CspValue styleSrc() {
        directives.add("style-src");
        return this;
    }

    public CspValue styleSrcElem() {
        directives.add("style-src-elem");
        return this;
    }

    public CspValue workerSrc() {
        directives.add("worker-src");
        return this;
    }

    public CspValue formAction() {
        directives.add("form-action");
        return this;
    }

    public CspValue frameAncestors() {
        directives.add("frame-ancestors");
        return this;
    }
}
