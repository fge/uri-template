package com.github.fge.uritemplate.parse;

import com.github.fge.uritemplate.URITemplateParseException;
import com.github.fge.uritemplate.expression.TemplateLiteral;
import com.github.fge.uritemplate.expression.URITemplateExpression;
import com.google.common.base.CharMatcher;

import java.nio.CharBuffer;

import static com.github.fge.uritemplate.ExceptionMessages.*;

public final class LiteralParser
    implements ExpressionParser
{
    private static final CharMatcher PERCENT = CharMatcher.is('%');
    private static final CharMatcher HEXDIGIT
        = CharMatcher.inRange('0', '9')
            .or(CharMatcher.inRange('a', 'f'))
            .or(CharMatcher.inRange('A', 'F'))
            .precomputed();

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
            if (!Matchers.LITERALS.matches(c))
                break;
            sb.append(buffer.get());
            if (PERCENT.matches(c))
                parsePercentEncoded(buffer, sb);
        }

        return new TemplateLiteral(sb.toString());
    }

    private static void parsePercentEncoded(final CharBuffer buffer,
        final StringBuilder sb)
        throws URITemplateParseException
    {
        if (buffer.remaining() < 2)
            throw new URITemplateParseException(PERCENT_SHORT_READ, buffer,
                true);

        final char first = buffer.get();
        if (!HEXDIGIT.matches(first))
            throw new URITemplateParseException(ILLEGAL_PERCENT, buffer,
                true);

        final char second = buffer.get();
        if (!HEXDIGIT.matches(second))
            throw new URITemplateParseException(ILLEGAL_PERCENT, buffer,
                true);

        sb.append(first).append(second);
    }
}
