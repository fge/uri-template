package com.github.fge.uritemplate;

import com.google.common.base.CharMatcher;

public final class CharMatchers
{
    public static final CharMatcher UNRESERVED;
    public static final CharMatcher RESERVED_PLUS_UNRESERVED;

    static {
        /*
         * Charsets defined by RFC 6570, section 1.5
         */
        // reserved
        final CharMatcher reserved = CharMatcher.inRange('a', 'z')
            .or(CharMatcher.inRange('A', 'Z'))
            .or(CharMatcher.inRange('0', '9'))
            .or(CharMatcher.anyOf("-._~"));
        // gen-delims
        final CharMatcher genDelims = CharMatcher.anyOf(":/?#[]@");
        // sub-delims
        final CharMatcher subDelims = CharMatcher.anyOf("!$&'()*+,;=");
        UNRESERVED = reserved.precomputed();
        // "reserved" is gen-delims or sub-delims
        RESERVED_PLUS_UNRESERVED = reserved.or(genDelims).or(subDelims)
            .precomputed();
    }

    private CharMatchers()
    {
    }
}
