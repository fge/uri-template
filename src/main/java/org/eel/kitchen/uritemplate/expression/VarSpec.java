package org.eel.kitchen.uritemplate.expression;

public abstract class VarSpec
{
    protected final String name;

    protected VarSpec(final String name)
    {
        this.name = name;
    }

    @Override
    public abstract String toString();
}
