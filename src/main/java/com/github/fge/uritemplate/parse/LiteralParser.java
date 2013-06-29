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
import com.github.fge.msgsimple.load.MessageBundles;
import com.github.fge.uritemplate.CharMatchers;
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
