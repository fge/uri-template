package com.github.fge.uritemplate.vars;

public abstract class VariableSpec
{
    private final String name;

    protected VariableSpec(final String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
}
