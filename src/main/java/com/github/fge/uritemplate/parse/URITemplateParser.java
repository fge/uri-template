package com.github.fge.uritemplate.parse;

import com.github.fge.uritemplate.ExceptionMessages;
import com.github.fge.uritemplate.URITemplateParseException;
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
        throws URITemplateParseException
    {
        final CharBuffer buffer = CharBuffer.wrap(input).asReadOnlyBuffer();
        final List<URITemplateExpression> ret = Lists.newArrayList();

        ExpressionParser expressionParser;
        URITemplateExpression expression;

        while (buffer.hasRemaining()) {
            expressionParser = selectParser(buffer);
            expression = expressionParser.parse(buffer);
            ret.add(expression);
        }

        return ret;
    }

    private static ExpressionParser selectParser(final CharBuffer buffer)
        throws URITemplateParseException
    {
        final char c = buffer.charAt(0);
        final ExpressionParser parser = Matchers.LITERALS.matches(c)
            ? new LiteralParser() : null;
        if (parser == null)
            throw new URITemplateParseException(ExceptionMessages.NO_PARSER,
                buffer);
        return parser;
    }
}
