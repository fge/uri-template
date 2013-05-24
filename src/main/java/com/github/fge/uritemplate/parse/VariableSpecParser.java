package com.github.fge.uritemplate.parse;

import com.github.fge.uritemplate.CharMatchers;
import com.github.fge.uritemplate.URITemplateParseException;
import com.github.fge.uritemplate.vars.specs.ExplodedVariable;
import com.github.fge.uritemplate.vars.specs.PrefixVariable;
import com.github.fge.uritemplate.vars.specs.SimpleVariable;
import com.github.fge.uritemplate.vars.specs.VariableSpec;
import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import java.nio.CharBuffer;
import java.util.List;

import static com.github.fge.uritemplate.ExceptionMessages.*;

final class VariableSpecParser
{
    private static final Joiner JOINER = Joiner.on('.');

    private static final CharMatcher VARCHAR = CharMatcher.inRange('0', '9')
        .or(CharMatcher.inRange('a', 'z')).or(CharMatcher.inRange('A', 'Z'))
        .or(CharMatcher.is('_')).or(CharMatchers.PERCENT).precomputed();
    private static final CharMatcher DOT = CharMatcher.is('.');
    private static final CharMatcher COLON = CharMatcher.is(':');
    private static final CharMatcher STAR = CharMatcher.is('*');
    private static final CharMatcher DIGIT = CharMatcher.inRange('0', '9')
        .precomputed();

    private VariableSpecParser()
    {
    }

    public static VariableSpec parse(final CharBuffer buffer)
        throws URITemplateParseException
    {
        final String name = parseFullName(buffer);

        if (!buffer.hasRemaining())
            return new SimpleVariable(name);

        final char c = buffer.charAt(0);
        if (STAR.matches(c)) {
            buffer.get();
            return new ExplodedVariable(name);
        }

        if (COLON.matches(c)) {
            buffer.get();
            return new PrefixVariable(name, getPrefixLength(buffer));
        }

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
            if (CharMatchers.PERCENT.matches(c))
                parsePercentEncoded(buffer, sb);
        }

        final String ret = sb.toString();
        if (ret.isEmpty())
            throw new URITemplateParseException(EMPTY_NAME, buffer);
        return ret;
    }

    private static void parsePercentEncoded(final CharBuffer buffer,
        final StringBuilder sb)
        throws URITemplateParseException
    {
        if (buffer.remaining() < 2)
            throw new URITemplateParseException(PERCENT_SHORT_READ, buffer,
                true);

        final char first = buffer.get();
        if (!CharMatchers.HEXDIGIT.matches(first))
            throw new URITemplateParseException(ILLEGAL_PERCENT, buffer,
                true);

        final char second = buffer.get();
        if (!CharMatchers.HEXDIGIT.matches(second))
            throw new URITemplateParseException(ILLEGAL_PERCENT, buffer,
                true);

        sb.append(first).append(second);
    }

    private static int getPrefixLength(final CharBuffer buffer)
        throws URITemplateParseException
    {
        final StringBuilder sb = new StringBuilder();

        char c;
        while (buffer.hasRemaining()) {
            c = buffer.charAt(0);
            if (!DIGIT.matches(c))
                break;
            sb.append(buffer.get());
        }

        final String s = sb.toString();
        if (s.isEmpty())
            throw new URITemplateParseException(EMPTY_PREFIX, buffer, true);
        final int ret;
        try {
            ret = Integer.parseInt(s);
            if (ret > 10000)
                throw new NumberFormatException();
            return ret;
        } catch (NumberFormatException ignored) {
            throw new URITemplateParseException(PREFIX_TOO_LARGE, buffer, true);
        }
    }
}
