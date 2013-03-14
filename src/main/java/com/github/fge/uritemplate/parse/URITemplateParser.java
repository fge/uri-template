package com.github.fge.uritemplate.parse;

import com.github.fge.uritemplate.URITemplateException;
import com.github.fge.uritemplate.expression.URITemplateExpression;
import com.google.common.collect.Lists;

import java.nio.CharBuffer;
import java.util.List;

public final class URITemplateParser
{
    private final List<URITemplateExpression> expressions
        = Lists.newArrayList();

    public void parse(final String input)
        throws URITemplateException
    {
        final CharBuffer buffer = CharBuffer.wrap(input);

        ExpressionParser expressionParser;
        URITemplateExpression expression;

        while (buffer.hasRemaining()) {
            expressionParser = selectParser(buffer);
            if (expressionParser == null)
                throw new URITemplateException();
            expression = expressionParser.parse(buffer);
            expressions.add(expression);
        }
    }

    private static ExpressionParser selectParser(final CharBuffer buffer)
    {
        return null;
    }
}
