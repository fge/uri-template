/*
 * Copyright (c) 2012, Francis Galiegue <fgaliegue@gmail.com>
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

package org.eel.kitchen.uritemplate;

import com.google.common.base.CharMatcher;

/**
 * List of character matchers according to RFC 6570, section 1.5
 */
public final class CharMatchers
{
    // No instantiations
    private CharMatchers()
    {
    }

    public static final CharMatcher ALPHA = CharMatcher.inRange('a', 'z')
        .or(CharMatcher.inRange('A', 'Z'));

    // We cannot use Guava's CharMatcher.DIGIT since that includes
    // Unicode numbers, and only 0..9 is allowed
    public static final CharMatcher DIGITS = CharMatcher.inRange('0', '9');

    public static final CharMatcher HEXDIGIT = ALPHA.or(DIGITS);

    public static final CharMatcher UNRESERVED
        = HEXDIGIT.or(CharMatcher.anyOf("-._~"));

    public static final CharMatcher GEN_DELIMS = CharMatcher.anyOf(":/?#[]@");

    public static final CharMatcher SUB_DELIMS
        = CharMatcher.anyOf("!$&'()*+,;=");

    public static final CharMatcher RESERVED = GEN_DELIMS.or(SUB_DELIMS);

    // FIXME: Java's Character cannot match Unicode chars with code points
    // greater than, or equal to, 0xffff. These definitions are therefore
    // a little approximative. Hopefully they are enough for a start...
    private static final CharMatcher NOT_LITERAL
        = CharMatcher.JAVA_ISO_CONTROL.or(CharMatcher.WHITESPACE)
            .or(CharMatcher.anyOf("\"'<>\\^`{|}"));

    public static final CharMatcher LITERAL
        = CharMatcher.ANY.and(NOT_LITERAL.negate());
}

