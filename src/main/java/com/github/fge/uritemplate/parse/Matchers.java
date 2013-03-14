package com.github.fge.uritemplate.parse;

import com.google.common.base.CharMatcher;

public final class Matchers
{
    public static final CharMatcher LITERALS;
    public static final CharMatcher OPEN_BRACKET = CharMatcher.is('{');
    public static final CharMatcher PERCENT = CharMatcher.is('%');
    public static final CharMatcher HEXDIGIT = CharMatcher.inRange('0', '9')
        .or(CharMatcher.inRange('a', 'f')).or(CharMatcher.inRange('A', 'F'))
        .precomputed();
    public static final CharMatcher VARCHAR = CharMatcher.inRange('0', '9')
        .or(CharMatcher.inRange('a', 'z')).or(CharMatcher.inRange('A', 'Z'))
        .or(CharMatcher.is('_')).precomputed();
    public static final CharMatcher DOT = CharMatcher.is('.');
    public static final CharMatcher VARPREFIX = CharMatcher.is(':');
    public static final CharMatcher DIGIT = CharMatcher.inRange('0', '9')
        .precomputed();

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
