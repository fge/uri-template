package com.github.fge.uritemplate.parse;

import com.google.common.base.CharMatcher;

public final class Matchers
{
    public static final CharMatcher LITERALS;
    public static final CharMatcher OPEN_BRACKET = CharMatcher.is('{');

    /*
     * Note: may not be exact... Best effort to match against RFC 6570 section
     * 2.1.
     *
     * The rest of invalid stuff will be caught when attempting to generate a
     * URI...
     */
    static {
        final CharMatcher ctl = CharMatcher.JAVA_ISO_CONTROL;
        final CharMatcher spc = CharMatcher.WHITESPACE;
        /*
         * This doesn't include the %: percent-encoded sequences will be
         * handled in the appropriate template parser
         */
        final CharMatcher other = CharMatcher.anyOf("\"'<>\\^`{|}");
        LITERALS = ctl.or(spc).or(other).negate().precomputed();
    }

    private Matchers()
    {
    }
}
