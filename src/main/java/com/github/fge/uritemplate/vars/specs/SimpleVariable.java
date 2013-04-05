package com.github.fge.uritemplate.vars.specs;

import com.github.fge.uritemplate.URITemplateException;
import com.github.fge.uritemplate.expression.ExpressionType;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;

public final class SimpleVariable
    extends VariableSpec
{
    public SimpleVariable(final String name)
    {
        super(VariableSpecType.SIMPLE, name);
    }

    @Override
    protected String renderScalar(final ExpressionType type,
        final String value)
        throws URITemplateException
    {
        final String s = expandString(type, value);
        if (!PARAM_STYLE_EXPRESSIONS.contains(type))
            return s;
        final StringBuilder sb = new StringBuilder(name);
        if (!(s.isEmpty() && type == ExpressionType.PATH_PARAMETERS))
            sb.append('=').append(s);
        return sb.toString();
    }

    @Override
    protected String renderList(final ExpressionType type,
        final List<String> value)
        throws URITemplateException
    {
        if (value.isEmpty() && !PARAM_STYLE_EXPRESSIONS.contains(type))
            return null;
        final Joiner joiner = Joiner.on(',');
        final List<String> list = Lists.newArrayList();
        for (final String s: value)
            list.add(expandString(type, s));
        final String joined = joiner.join(list);
        if (!PARAM_STYLE_EXPRESSIONS.contains(type))
            return joined;
        final StringBuilder sb = new StringBuilder(name);
        if (!(joined.isEmpty() && type == ExpressionType.PATH_PARAMETERS))
            sb.append('=').append(joined);
        return sb.toString();
    }

    @Override
    protected String renderMap(final ExpressionType type,
        final Map<String, String> map)
        throws URITemplateException
    {
        if (map.isEmpty() && !PARAM_STYLE_EXPRESSIONS.contains(type))
            return null;
        final Joiner joiner = Joiner.on(',');
        final List<String> list = Lists.newArrayList();
        for (final Map.Entry<String, String> entry: map.entrySet()) {
            list.add(expandString(type, entry.getKey()));
            list.add(expandString(type, entry.getValue()));
        }
        final String joined = joiner.join(list);
        if (!PARAM_STYLE_EXPRESSIONS.contains(type))
            return joined;
        final StringBuilder sb = new StringBuilder(name);
        if (!(joined.isEmpty() && type == ExpressionType.PATH_PARAMETERS))
            sb.append('=').append(joined);
        return sb.toString();
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
        final SimpleVariable other = (SimpleVariable) obj;
        return name.equals(other.name);
    }

    @Override
    public String toString()
    {
        return name + " (simple)";
    }
}
