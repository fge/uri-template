package com.github.fge.uritemplate.parse;

import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.uritemplate.CharMatchers;
import com.github.fge.uritemplate.URITemplateMessages;
import com.github.fge.uritemplate.URITemplateParseException;
import com.github.fge.uritemplate.expression.TemplateLiteral;
import com.github.fge.uritemplate.expression.URITemplateExpression;

import java.nio.CharBuffer;

final class LiteralParser
    implements TemplateParser
{
    private static final MessageBundle BUNDLE = URITemplateMessages.get();

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
                BUNDLE.getKey("PERCENT_SHORT_READ"), buffer, true);

        final char first = buffer.get();
        if (!CharMatchers.HEXDIGIT.matches(first))
            throw new URITemplateParseException(
                BUNDLE.getKey("ILLEGAL_PERCENT"), buffer, true);

        final char second = buffer.get();
        if (!CharMatchers.HEXDIGIT.matches(second))
            throw new URITemplateParseException(
                BUNDLE.getKey("ILLEGAL_PERCENT"), buffer, true);

        sb.append(first).append(second);
    }
}
