package org.eel.kitchen.uritemplate.expression;

public abstract class Variable
{
    protected final String name;

    protected Variable(final String name)
    {
        this.name = name;
    }

    @Override
    public abstract String toString();
}
