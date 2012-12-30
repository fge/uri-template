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

package org.eel.kitchen.uritemplate.expression.variable;

import com.google.common.base.CharMatcher;
import org.eel.kitchen.uritemplate.InvalidTemplateException;
import org.eel.kitchen.uritemplate.expression.ExpressionBuilder;
import org.eel.kitchen.uritemplate.expression.TokenParser;

import java.nio.CharBuffer;

public final class VarSpecTokenParser
    implements TokenParser
{
    private static final CharMatcher MATCHER;

    static {
        CharMatcher matcher;

        // ALPHA
        matcher = CharMatcher.inRange('a', 'z')
            .or(CharMatcher.inRange('A', 'Z'));

        // DIGIT
        matcher = matcher.or(CharMatcher.inRange('0', '9'));

        // "_"
        matcher = matcher.or(CharMatcher.is('_'));

        // build
        MATCHER = matcher.precomputed();
    }

    private final CharBuffer buf;
    private final int index;
    private final VariableBuilder builder;
    private final StringBuilder sb;

    public VarSpecTokenParser(final CharBuffer buf, final int index,
        final VariableBuilder builder, final StringBuilder sb)
    {
        this.buf = buf;
        this.index = index;
        this.builder = builder;
        this.sb = sb;
    }

    TokenParser next;

    @Override
    public boolean parse()
        throws InvalidTemplateException
    {
        if (index == buf.length()) {
            final String varName = sb.toString();
            if (varName.isEmpty())
                throw new InvalidTemplateException("variable names cannot be " +
                    "empty");
            builder.setName(varName);
            return false;
        }

        final char c = buf.get(index);

        switch (c) {
            case '%':
                sb.append(c);
                next = new PercentEncodedVarSpecTokenParser(buf, index + 1,
                    builder, sb);
                break;
            default:
                if (!MATCHER.matches(c))
                    throw new InvalidTemplateException("illegal varspec " +
                        "character");
                next = new VarSpecTokenParser(buf, index + 1, builder, sb);
                sb.append(c);
        }
        return true;
    }

    @Override
    public TokenParser next()
    {
        return next;
    }
}
