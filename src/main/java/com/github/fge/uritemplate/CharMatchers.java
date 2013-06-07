/*
 * Copyright (c) 2013, Francis Galiegue <fgaliegue@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Lesser GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Lesser GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.fge.uritemplate;

import com.google.common.base.CharMatcher;

/**
 * Character sets needed by the parsing and expansion processes
 *
 * <p>Those are defined by the RFC, section 1.5. They are used by the expansion
 * process.</p>
 */
public final class CharMatchers
{
    public static final CharMatcher LITERALS;
    public static final CharMatcher PERCENT = CharMatcher.is('%');
    public static final CharMatcher HEXDIGIT = CharMatcher.inRange('0', '9')
        .or(CharMatcher.inRange('a', 'f')).or(CharMatcher.inRange('A', 'F'))
        .precomputed();

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

        final CharMatcher ctl = CharMatcher.JAVA_ISO_CONTROL;
        final CharMatcher spc = CharMatcher.WHITESPACE;
        /*
         * This doesn't include the %: percent-encoded sequences will be
         * handled in the appropriate template parser
         */
        final CharMatcher other = CharMatcher.anyOf("\"'<>\\^`{|}");
        LITERALS = ctl.or(spc).or(other).negate().precomputed();
    }

    private CharMatchers()
    {
    }
}
