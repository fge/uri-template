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

package com.github.fge.uritemplate.parse;

import com.github.fge.uritemplate.URITemplateParseException;
import com.github.fge.uritemplate.expression.ExpressionType;
import com.github.fge.uritemplate.expression.URITemplateExpression;
import com.google.common.collect.ImmutableMap;

import java.nio.CharBuffer;
import java.util.Map;

public final class ExpressionParser
    implements TemplateParser
{
    private static final Map<Character, ExpressionType> EXPRESSION_TYPE_MAP;

    static {
        final ImmutableMap.Builder<Character, ExpressionType> builder
            = ImmutableMap.builder();

        char c;
        ExpressionType type;

        c = '+';
        type = ExpressionType.RESERVED;
        builder.put(c, type);

        c = '#';
        type = ExpressionType.FRAGMENT;
        builder.put(c, type);

        c = '.';
        type = ExpressionType.NAME_LABELS;
        builder.put(c, type);

        c = '/';
        type = ExpressionType.PATH_SEGMENTS;
        builder.put(c, type);

        c = ';';
        type = ExpressionType.PATH_PARAMETERS;
        builder.put(c, type);

        c = '?';
        type = ExpressionType.QUERY_STRING;
        builder.put(c, type);

        c = '&';
        type = ExpressionType.QUERY_CONT;
        builder.put(c, type);

        EXPRESSION_TYPE_MAP = builder.build();
    }
    @Override
    public URITemplateExpression parse(final CharBuffer buffer)
        throws URITemplateParseException
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
