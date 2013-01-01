package org.eel.kitchen.uritemplate.expression.variable;

public final class ExplodedVariable
    extends Variable
{
    ExplodedVariable(final String name)
    {
        super(name);
    }

    @Override
    public String toString()
    {
        return name + " (exploded)";
    }
}
