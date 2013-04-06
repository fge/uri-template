package com.github.fge.uritemplate.vars.specs;

import com.github.fge.uritemplate.URITemplateException;
import com.github.fge.uritemplate.expression.ExpressionType;
import com.github.fge.uritemplate.vars.values.VariableValue;
import com.google.common.base.CharMatcher;
import com.google.common.primitives.UnsignedBytes;

import java.nio.charset.Charset;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

import static com.github.fge.uritemplate.expression.ExpressionType.*;

/*
 * TODO: intelligence here
 *
 * A varspec has all the necessary elements to render according to the needs. It
 * needs two elements to operate:
 *
 * - the type of the value;
 * - the type of the expression.
 *
 * According to its own, internal information, its rendering method (name to be
 * decided) should be able to return a String to the caller with the appropriate
 * contents.
 */
public abstract class VariableSpec
{
    protected static final EnumSet<ExpressionType> PARAM_STYLE_EXPRESSIONS
        = EnumSet.of(PATH_PARAMETERS, QUERY_STRING, QUERY_CONT);
    private static final CharMatcher UNRESERVED;
    private static final CharMatcher RESERVED_PLUS_UNRESERVED;

    static {
        /*
         * Charsets defined by RFC 6570, section 1.5
         */
        // reserved
        final CharMatcher reserved = CharMatcher.inRange('a', 'z')
            .or(CharMatcher.inRange('A', 'Z'))
            .or(CharMatcher.inRange('0', '9'))
            .or(CharMatcher.anyOf("-._~"));
        // gen-delims
        final CharMatcher genDelims = CharMatcher.anyOf(":/?#[]@");
        // sub-delims
        final CharMatcher subDelims = CharMatcher.anyOf("!$&'()*+,;=");
        UNRESERVED = reserved.precomputed();
        // "reserved" is gen-delims or sub-delims
        RESERVED_PLUS_UNRESERVED = reserved.or(genDelims).or(subDelims)
            .precomputed();
    }

    protected final VariableSpecType type;
    protected final String name;

    protected VariableSpec(final VariableSpecType type, final String name)
    {
        this.type = type;
        this.name = name;
    }

    public final VariableSpecType getType()
    {
        return type;
    }

    public final String getName()
    {
        return name;
    }

    public final String render(final ExpressionType type,
        final VariableValue value)
        throws URITemplateException
    {
        switch (value.getType()) {
            case SCALAR:
                return renderScalar(type, value.getScalarValue());
            case ARRAY:
                return renderList(type, value.getListValue());
            case MAP:
                return renderMap(type, value.getMapValue());
        }

        throw new RuntimeException("How did I get there?");
    }

    protected abstract String renderScalar(final ExpressionType type,
        final String value)
        throws URITemplateException;

    protected abstract String renderList(final ExpressionType type,
        final List<String> value)
        throws URITemplateException;

    protected abstract String renderMap(final ExpressionType type,
        final Map<String, String> map)
        throws URITemplateException;

    @Override
    public abstract int hashCode();

    @Override
    public abstract boolean equals(final Object obj);

    @Override
    public abstract String toString();

    protected static String expandString(final ExpressionType type,
        final String s)
    {
        final CharMatcher matcher = type.isRawExpand()
            ? RESERVED_PLUS_UNRESERVED : UNRESERVED;
        final StringBuilder sb = new StringBuilder(s.length());
        for (final char c: s.toCharArray())
            sb.append(matcher.matches(c) ? c : pctEncode(c));
        return sb.toString();
    }

    private static String pctEncode(final char c)
    {
        final String tmp = new String(new char[] { c });
        final byte[] bytes = tmp.getBytes(Charset.forName("UTF-8"));
        final StringBuilder sb = new StringBuilder();
        for (final byte b: bytes)
            sb.append('%').append(UnsignedBytes.toString(b, 16));
        return sb.toString();
    }
}
