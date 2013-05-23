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
import com.google.common.base.Joiner;
import com.google.common.primitives.UnsignedBytes;

import java.nio.charset.Charset;
import java.util.List;

public abstract class ValueRenderer
{
    protected static final Joiner COMMA = Joiner.on(',');

    protected final boolean named;
    protected final String ifEmpty;
    protected final CharMatcher matcher;

    protected ValueRenderer(final ExpressionType type)
    {
        named = type.isNamed();
        ifEmpty = type.getIfEmpty();
        matcher = type.isRawExpand() ? CharMatchers.RESERVED_PLUS_UNRESERVED
            : CharMatchers.UNRESERVED;
    }

    public abstract List<String> render(final VariableSpec varspec,
        VariableValue value)
        throws URITemplateException;

    /*
     * Do a percent encoding of a string value
     */
    protected final String pctEncode(final String s)
    {
        final StringBuilder sb = new StringBuilder(s.length());
        for (final char c: s.toCharArray())
            sb.append(matcher.matches(c) ? c : encodeChar(c));
        return sb.toString();
    }

    /*
     * Do a percent encoding of a single character
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

