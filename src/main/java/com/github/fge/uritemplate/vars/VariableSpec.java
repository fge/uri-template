package com.github.fge.uritemplate.vars;

public abstract class VariableSpec
{
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

    @Override
    public abstract int hashCode();

    @Override
    public abstract boolean equals(final Object obj);

    @Override
    public abstract String toString();
}
