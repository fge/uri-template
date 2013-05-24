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

package com.github.fge.uritemplate.render;

import com.github.fge.uritemplate.CharMatchers;
import com.github.fge.uritemplate.URITemplateException;
import com.github.fge.uritemplate.expression.ExpressionType;
import com.github.fge.uritemplate.vars.specs.VariableSpec;
import com.github.fge.uritemplate.vars.values.VariableValue;
import com.google.common.base.CharMatcher;
import com.google.common.primitives.UnsignedBytes;

import java.nio.charset.Charset;
import java.util.List;

/**
 * Main rendering class
 *
 * <p>The algorithm used for rendering is centered around this class,and is
 * adapted from the algorithm suggested in the RFC's appendix.</p>
 *
 * <p>Eventually, rendering can be viewed as joining a list of rendered strings
 * with the expression type separator; if the resulting list is empty, the end
 * result is the empty string; otherwise, it is the expression's prefix string
 * (if any) followed by the joined list of rendered strings.</p>
 *
 * <p>This class renders one variable value according to the expression type and
 * value type. The rendering method returns a list, which can be empty.</p>
 *
 * @see ExpressionType
 */
public abstract class ValueRenderer
{
    /**
     * Whether variable values are named during expansion
     *
     * @see ExpressionType#isNamed()
     */
    protected final boolean named;
    /**
     * Substitution string for an empty value/list member/map value
     *
     * @see ExpressionType#getIfEmpty()
     */
    protected final String ifEmpty;
    /**
     * Set of characters not subject to percent-encoding
     *
     * @see ExpressionType#isRawExpand()
     */
    protected final CharMatcher matcher;

    /**
     * Constructor
     *
     * @param type the expression type
     */
    protected ValueRenderer(final ExpressionType type)
    {
        named = type.isNamed();
        ifEmpty = type.getIfEmpty();
        matcher = type.isRawExpand() ? CharMatchers.RESERVED_PLUS_UNRESERVED
            : CharMatchers.UNRESERVED;
    }

    /**
     * Render a value given a varspec and value
     *
     * @param varspec the varspec
     * @param value the matching variable value
     * @return a list of rendered strings
     * @throws URITemplateException illegal expansion
     */
    public abstract List<String> render(final VariableSpec varspec,
        VariableValue value)
        throws URITemplateException;

    /**
     * Render a string value, doing character percent-encoding where needed
     *
     * <p>The character set on which to perform percent encoding is dependent
     * on the expression type.</p>
     *
     * @param s the string to encode
     * @return an encoded string
     * @see CharMatchers
     * @see ExpressionType#isRawExpand()
     */
    protected final String pctEncode(final String s)
    {
        final StringBuilder sb = new StringBuilder(s.length());
        for (final char c: s.toCharArray())
            sb.append(matcher.matches(c) ? c : encodeChar(c));
        return sb.toString();
    }

    /**
     * Perform a percent encoding of one character when necessary
     *
     * <p>As the RFC says, this method extracts the UTF-8 encoding of the
     * character and percent-encodes each character.</p>
     *
     * @param c the character to encode
     * @return the matching percent-encoded string
     */
    private static String encodeChar(final char c)
    {
        final String tmp = new String(new char[] { c });
        final byte[] bytes = tmp.getBytes(Charset.forName("UTF-8"));
        final StringBuilder sb = new StringBuilder();
        for (final byte b: bytes)
            sb.append('%').append(UnsignedBytes.toString(b, 16));
        return sb.toString();
    }
}

