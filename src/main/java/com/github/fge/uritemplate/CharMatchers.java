package com.github.fge.uritemplate;

import com.google.common.base.CharMatcher;

/**
 * Character sets needed by the expansion process
 *
 * <p>Those are defined by the RFC, section 1.5. They are used by the expansion
 * process.</p>
 */
public final class CharMatchers
{
    /**
     * The {@code unreserved} character set
     */
    public static final CharMatcher UNRESERVED;
    /**
     * The {@code reserved} character set
     */
    public static final CharMatcher RESERVED_PLUS_UNRESERVED;

    static {
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
