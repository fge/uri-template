package com.github.fge.uritemplate.vars;

import com.github.fge.uritemplate.URITemplateException;
import com.github.fge.uritemplate.expression.ExpressionType;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;

public final class ExplodedVariable
    extends VariableSpec
{
    public ExplodedVariable(final String name)
    {
        super(VariableSpecType.EXPLODED, name);
    }

    @Override
    protected String renderScalar(final ExpressionType type,
        final String value)
        throws URITemplateException
    {
        return expandString(type, value);
    }

    @Override
    protected String renderList(final ExpressionType type,
        final List<String> value)
        throws URITemplateException
    {
        final Joiner joiner = Joiner.on(type.getSeparator());
        final List<String> list = Lists.newArrayList();
        for (final String s: value)
            list.add(expandString(type, s));
        return joiner.join(list);
    }

    @Override
    protected String renderMap(final ExpressionType type,
        final Map<String, String> map)
        throws URITemplateException
    {
        if (map.isEmpty())
            return null;
        final Joiner joiner = Joiner.on(type.getSeparator());
        final List<String> list = Lists.newArrayList();
        StringBuilder sb;
        for (final Map.Entry<String, String> entry: map.entrySet()) {
            sb = new StringBuilder(expandString(type, entry.getKey()))
                .append('=').append(expandString(type, entry.getValue()));
            list.add(sb.toString());
        }
        return joiner.join(list);
    }

    @Override
    public int hashCode()
    {
        return name.hashCode();
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
        final ExplodedVariable other = (ExplodedVariable) obj;
        return name.equals(other.name);
    }

    @Override
    public String toString()
    {
        return name + " (exploded)";
    }
}
