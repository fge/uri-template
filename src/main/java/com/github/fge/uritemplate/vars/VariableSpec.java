package com.github.fge.uritemplate.vars;

public abstract class VariableSpec
{
    protected final String name;

    protected VariableSpec(final String name)
    {
        this.name = name;
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
