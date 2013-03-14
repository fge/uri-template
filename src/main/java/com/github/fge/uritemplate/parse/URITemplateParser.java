package com.github.fge.uritemplate.parse;

import com.github.fge.uritemplate.ExceptionMessages;
import com.github.fge.uritemplate.URITemplateException;
import com.github.fge.uritemplate.expression.URITemplateExpression;
import com.google.common.collect.Lists;

import java.nio.CharBuffer;
import java.util.List;

public final class URITemplateParser
{
    private URITemplateParser()
    {
    }

    public static List<URITemplateExpression> parse(final String input)
        throws URITemplateException
    {
        final CharBuffer buffer = CharBuffer.wrap(input).asReadOnlyBuffer();
        final List<URITemplateExpression> ret = Lists.newArrayList();

        ExpressionParser expressionParser;
        URITemplateExpression expression;

        while (buffer.hasRemaining()) {
            expressionParser = selectParser(buffer);
            if (expressionParser == null)
                throw new URITemplateException(ExceptionMessages.NO_PARSER);
            expression = expressionParser.parse(buffer);
            ret.add(expression);
        }

        return ret;
    }

    private static ExpressionParser selectParser(final CharBuffer buffer)
    {
        final char c = buffer.charAt(0);
        return Matchers.LITERALS.matches(c) ? new LiteralParser() : null;
    }
}
