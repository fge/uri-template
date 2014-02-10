/*
 * Copyright (c) 2014, Francis Galiegue (fgaliegue@gmail.com)
 *
 * This software is dual-licensed under:
 *
 * - the Lesser General Public License (LGPL) version 3.0 or, at your option, any
 *   later version;
 * - the Apache Software License (ASL) version 2.0.
 *
 * The text of both licenses is available under the src/resources/ directory of
 * this project (under the names LGPL-3.0.txt and ASL-2.0.txt respectively).
 *
 * Direct link to the sources:
 *
 * - LGPL 3.0: https://www.gnu.org/licenses/lgpl-3.0.txt
 * - ASL 2.0: http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package com.github.fge.uritemplate.parse;

import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.msgsimple.load.MessageBundles;
import com.github.fge.uritemplate.URITemplateMessageBundle;
import com.github.fge.uritemplate.URITemplateParseException;
import com.github.fge.uritemplate.expression.ExpressionType;
import com.github.fge.uritemplate.expression.TemplateExpression;
import com.github.fge.uritemplate.expression.URITemplateExpression;
import com.github.fge.uritemplate.vars.specs.VariableSpec;
import com.google.common.base.CharMatcher;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

import java.nio.CharBuffer;
import java.util.List;
import java.util.Map;

final class ExpressionParser
    implements TemplateParser
{
    private static final MessageBundle BUNDLE
        = MessageBundles.getBundle(URITemplateMessageBundle.class);

    private static final Map<Character, ExpressionType> EXPRESSION_TYPE_MAP;
    private static final CharMatcher COMMA = CharMatcher.is(',');
    private static final CharMatcher END_EXPRESSION = CharMatcher.is('}');

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
        // Swallow the '{'
        buffer.get();

        /*
         * Error if the buffer is empty after that
         */
        if (!buffer.hasRemaining())
            throw new URITemplateParseException(
                BUNDLE.getMessage("parse.unexpectedEOF"), buffer, true);

        /*
         * If the next char is a known expression type, swallow it; otherwise,
         * select SIMPLE.
         */
        ExpressionType type = ExpressionType.SIMPLE;
        char c = buffer.charAt(0);
        if (EXPRESSION_TYPE_MAP.containsKey(c))
            type = EXPRESSION_TYPE_MAP.get(buffer.get());

        /*
         * Now, swallow varspec by varspec.
         */
        final List<VariableSpec> varspecs = Lists.newArrayList();

        while (true) {
            /*
             * Swallow one varspec
             */
            varspecs.add(VariableSpecParser.parse(buffer));
            /*
             * Error if the buffer is empty after that
             */
            if (!buffer.hasRemaining())
                throw new URITemplateParseException(
                    BUNDLE.getMessage("parse.unexpectedEOF"), buffer, true);
            /*
             * Grab next character
             */
            c = buffer.get();
            /*
             * If it is a comma, swallow next varspec
             */
            if (COMMA.matches(c))
                continue;
            /*
             * If it is a closing bracket, we're done
             */
            if (END_EXPRESSION.matches(c))
                break;
            /*
             * If we reach this point, this is an error
             */
            throw new URITemplateParseException(
                BUNDLE.getMessage("parse.unexpectedToken"), buffer, true);
        }

        return new TemplateExpression(type, varspecs);
    }
}
