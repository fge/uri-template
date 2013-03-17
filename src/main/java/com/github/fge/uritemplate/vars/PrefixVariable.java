package com.github.fge.uritemplate.vars;

import com.github.fge.uritemplate.URITemplateException;
import com.github.fge.uritemplate.expression.URITemplateExpression;

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
    protected String renderScalar(final URITemplateExpression expression,
        final String value)
        throws URITemplateException
    {
        return null;
    }

    @Override
    protected String renderList(final URITemplateExpression expression,
        final List<String> value)
        throws URITemplateException
    {
        return null;
    }

    @Override
    protected String renderMap(final URITemplateExpression expression,
        final Map<String, String> map)
        throws URITemplateException
    {
        return null;
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
