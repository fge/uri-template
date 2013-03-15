package com.github.fge.uritemplate.parse;

import com.github.fge.uritemplate.URITemplateParseException;
import com.github.fge.uritemplate.vars.SimpleVariable;
import com.github.fge.uritemplate.vars.VariableSpec;
import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import java.nio.CharBuffer;
import java.util.List;

import static com.github.fge.uritemplate.ExceptionMessages.*;

public final class VariableSpecParser
{
    private static final Joiner JOINER = Joiner.on('.');

    private static final CharMatcher VARCHAR = CharMatcher.inRange('0', '9')
        .or(CharMatcher.inRange('a', 'z')).or(CharMatcher.inRange('A', 'Z'))
        .or(CharMatcher.is('_')).or(Matchers.PERCENT).precomputed();
    private static final CharMatcher DOT = CharMatcher.is('.');
    private static final CharMatcher VARPREFIX = CharMatcher.is(':');
    private static final CharMatcher DIGIT = CharMatcher.inRange('0', '9')
        .precomputed();

    private VariableSpecParser()
    {
    }

    public static VariableSpec parse(final CharBuffer buffer)
        throws URITemplateParseException
    {
        final String name = parseFullName(buffer);
        return new SimpleVariable(name);
    }

    private static String parseFullName(final CharBuffer buffer)
        throws URITemplateParseException
    {
        final List<String> components = Lists.newArrayList();

        while (true) {
            components.add(readName(buffer));
            if (!buffer.hasRemaining())
                break;
            if (!DOT.matches(buffer.charAt(0)))
                break;
            buffer.get(); // Read the dot
        }

        return JOINER.join(components);
    }

    private static String readName(final CharBuffer buffer)
        throws URITemplateParseException
    {
        final StringBuilder sb = new StringBuilder();

        char c;
        while (buffer.hasRemaining()) {
            c = buffer.charAt(0);
            if (!VARCHAR.matches(c))
                break;
            sb.append(buffer.get());
            if (Matchers.PERCENT.matches(c))
                parsePercentEncoded(buffer, sb);
        }

        return sb.toString();
    }

    private static void parsePercentEncoded(final CharBuffer buffer,
        final StringBuilder sb)
        throws URITemplateParseException
    {
        if (buffer.remaining() < 2)
            throw new URITemplateParseException(PERCENT_SHORT_READ, buffer,
                true);

        final char first = buffer.get();
        if (!Matchers.HEXDIGIT.matches(first))
            throw new URITemplateParseException(ILLEGAL_PERCENT, buffer,
                true);

        final char second = buffer.get();
        if (!Matchers.HEXDIGIT.matches(second))
            throw new URITemplateParseException(ILLEGAL_PERCENT, buffer,
                true);

        sb.append(first).append(second);
    }
}
