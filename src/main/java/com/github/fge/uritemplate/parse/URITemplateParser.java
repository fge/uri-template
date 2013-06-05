package com.github.fge.uritemplate.parse;

import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.uritemplate.CharMatchers;
import com.github.fge.uritemplate.URITemplate;
import com.github.fge.uritemplate.URITemplateMessages;
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
 * @see URITemplate#URITemplate(String)
 */
public final class URITemplateParser
{
    private static final MessageBundle BUNDLE = URITemplateMessages.get();
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
                BUNDLE.getMessage("NO_PARSER"), buffer);
        return parser;
    }
}
