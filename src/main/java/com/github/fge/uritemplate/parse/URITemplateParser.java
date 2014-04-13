/*
 * Copyright (c) 2014, Francis Galiegue (fgaliegue@gmail.com)
 *
 * This software is dual-licensed under:
 *
 * - the Lesser General Public License (LGPL) version 3.0 or, at your option, any
 *   later version;
 * - the Apache Software License (ASL) version 2.0.
 *
 * The text of this file and of both licenses is available at the root of this
 * project or, if you have the jar distribution, in directory META-INF/, under
 * the names LGPL-3.0.txt and ASL-2.0.txt respectively.
 *
 * Direct link to the sources:
 *
 * - LGPL 3.0: https://www.gnu.org/licenses/lgpl-3.0.txt
 * - ASL 2.0: http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package com.github.fge.uritemplate.parse;

import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.msgsimple.load.MessageBundles;
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
        = MessageBundles.getBundle(URITemplateMessageBundle.class);

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
