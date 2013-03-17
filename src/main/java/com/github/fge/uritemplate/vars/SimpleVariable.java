package com.github.fge.uritemplate.vars;

import com.github.fge.uritemplate.URITemplateException;
import com.github.fge.uritemplate.expression.URITemplateExpression;

public final class SimpleVariable
    extends VariableSpec
{
    public SimpleVariable(final String name)
    {
        super(VariableSpecType.SIMPLE, name);
    }

    @Override
    public String render(final URITemplateExpression expression,
        final VariableValue value)
        throws URITemplateException
    {
        return null;
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
