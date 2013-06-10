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

import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.msgsimple.serviceloader.MessageBundleFactory;
import com.github.fge.uritemplate.CharMatchers;
import com.github.fge.uritemplate.URITemplate;
import com.github.fge.uritemplate.URITemplateMessageBundle;
import com.github.fge.uritemplate.URITemplateParseException;
import com.github.fge.uritemplate.expression.URITemplateExpression;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.CharMatcher;
import com.google.common.collect.Lists;

import java.nio.CharBuffer;
import java.util.List;

/**
 * Main parser class
 *
 * <p>You should not use this class directly.</p>
 *
 * @see URITemplate#URITemplate(String)
 */
public final class URITemplateParser
{
    private static final MessageBundle BUNDLE
        = MessageBundleFactory.getBundle(URITemplateMessageBundle.class);

    private static final CharMatcher BEGIN_EXPRESSION = CharMatcher.is('{');

    private URITemplateParser()
    {
    }

    public static List<URITemplateExpression> parse(final String input)
        throws URITemplateParseException
    {
        return parse(CharBuffer.wrap(input).asReadOnlyBuffer());
    }

    @VisibleForTesting
    static List<URITemplateExpression> parse(final CharBuffer buffer)
        throws URITemplateParseException
    {
        final List<URITemplateExpression> ret = Lists.newArrayList();

        TemplateParser templateParser;
        URITemplateExpression expression;

        while (buffer.hasRemaining()) {
            templateParser = selectParser(buffer);
            expression = templateParser.parse(buffer);
            ret.add(expression);
        }

        return ret;
    }

    private static TemplateParser selectParser(final CharBuffer buffer)
        throws URITemplateParseException
    {
        final char c = buffer.charAt(0);
        final TemplateParser parser;
        if (CharMatchers.LITERALS.matches(c))
            parser = new LiteralParser();
        else if (BEGIN_EXPRESSION.matches(c))
            parser = new ExpressionParser();
        else
            throw new URITemplateParseException(
                BUNDLE.getMessage("parse.noParser"), buffer);
        return parser;
    }
}
