package com.github.fge.uritemplate.vars.specs;

import com.github.fge.uritemplate.ExceptionMessages;
import com.github.fge.uritemplate.URITemplateException;
import com.github.fge.uritemplate.expression.ExpressionType;

import java.util.List;
import java.util.Map;

public final class PrefixVariable
    extends VariableSpec
{
    private final int length;

    public PrefixVariable(final String name, final int length)
    {
        super(VariableSpecType.PREFIX, name);
        this.length = length;
    }


    @Override
    protected String renderScalar(final ExpressionType type,
        final String value)
        throws URITemplateException
    {
        final int len = Math.min(value.length(), length);
        final String s = value.substring(0, len);
        final String expanded = expandString(type, s);
        if (!PARAM_STYLE_EXPRESSIONS.contains(type))
            return expanded;
        final StringBuilder sb = new StringBuilder(name);
        if (!(expanded.isEmpty() && type == ExpressionType.PATH_PARAMETERS))
            sb.append('=').append(expanded);
        return sb.toString();

    }

    @Override
    protected String renderList(final ExpressionType type,
        final List<String> value)
        throws URITemplateException
    {
        throw new URITemplateException(ExceptionMessages.EXPAND_INCOMPAT);
    }

    @Override
    protected String renderMap(final ExpressionType type,
        final Map<String, String> map)
        throws URITemplateException
    {
        throw new URITemplateException(ExceptionMessages.EXPAND_INCOMPAT);
    }

    @Override
    public boolean isExploded()
    {
        return false;
    }

    @Override
    public int getPrefixLength()
    {
        return length;
    }

    @Override
    public int hashCode()
    {
        return 31 * name.hashCode() + length;
    }

    @Override
    public boolean equals(final Object obj)
    {
        if (obj == null)
            return false;
        if (this == obj)
            return true;
        if (getClass() != obj.getClass())
            return false;
        final PrefixVariable other = (PrefixVariable) obj;
        return name.equals(other.name) && length == other.length;
    }

    @Override
    public String toString()
    {
        return name + " (prefix length: " + length + ')';
    }
}
