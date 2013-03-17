package com.github.fge.uritemplate.vars;

import com.github.fge.uritemplate.URITemplateException;
import com.github.fge.uritemplate.expression.ExpressionType;
import com.google.common.base.CharMatcher;

import java.util.List;
import java.util.Map;

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
    private static final CharMatcher UNRESERVED;

    static {
        /*
         * Set of unreserved characters as defined by RFC 6570, section 1.5
         */
        final CharMatcher matcher = CharMatcher.inRange('a', 'z')
            .or(CharMatcher.inRange('A', 'Z'))
            .or(CharMatcher.inRange('0', '9'))
            .or(CharMatcher.anyOf("-._~"));
        UNRESERVED = matcher.precomputed();
    }

    protected final VariableSpecType type;
    protected final String name;

    protected VariableSpec(final VariableSpecType type, final String name)
    {
        this.type = type;
        this.name = name;
    }

    public VariableSpecType getType()
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
        if (type.isRawExpand())
            return s;
        final StringBuilder sb = new StringBuilder(s.length());
        for (final char c: s.toCharArray())
            if (UNRESERVED.matches(c))
                sb.append(c);
            else
                sb.append('%').append(Integer.toString(c, 16));
        return sb.toString();
    }
}
