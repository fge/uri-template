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
import com.github.fge.uritemplate.expression.TemplateLiteral;
import com.github.fge.uritemplate.expression.URITemplateExpression;

import java.nio.CharBuffer;

final class LiteralParser
    implements TemplateParser
{
    private static final MessageBundle BUNDLE
        = MessageBundles.getBundle(URITemplateMessageBundle.class);

    @Override
    public URITemplateExpression parse(final CharBuffer buffer)
        throws URITemplateParseException
    {
        final StringBuilder sb = new StringBuilder();
        char c;

        /*
         * There is at least one character to parse
         */
        while (buffer.hasRemaining()) {
            c = buffer.charAt(0);
            if (!CharMatchers.LITERALS.matches(c))
                break;
            sb.append(buffer.get());
            if (CharMatchers.PERCENT.matches(c))
                parsePercentEncoded(buffer, sb);
        }

        return new TemplateLiteral(sb.toString());
    }

    private static void parsePercentEncoded(final CharBuffer buffer,
        final StringBuilder sb)
        throws URITemplateParseException
    {
        if (buffer.remaining() < 2)
            throw new URITemplateParseException(
                BUNDLE.getMessage("paser.percentShortRead"), buffer, true);

        final char first = buffer.get();
        if (!CharMatchers.HEXDIGIT.matches(first))
            throw new URITemplateParseException(
                BUNDLE.getMessage("parse.percentIllegal"), buffer, true);

        final char second = buffer.get();
        if (!CharMatchers.HEXDIGIT.matches(second))
            throw new URITemplateParseException(
                BUNDLE.getMessage("parse.percentIllegal"), buffer, true);

        sb.append(first).append(second);
    }
}
