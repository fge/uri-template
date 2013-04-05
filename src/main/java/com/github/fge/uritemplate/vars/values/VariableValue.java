package com.github.fge.uritemplate.vars.values;

import java.util.List;
import java.util.Map;

public abstract class VariableValue
{
    protected final ValueType type;

    protected VariableValue(final ValueType type)
    {
        this.type = type;
    }

    public final ValueType getType()
    {
        return type;
    }

    public String getScalarValue()
    {
        throw new IllegalStateException();
    }

    public List<String> getListValue()
    {
        throw new IllegalStateException();
    }

    public Map<String, String> getMapValue()
    {
        throw new IllegalStateException();
    }
}
