package org.eel.kitchen.uritemplate.expression.variable;

final class SimpleVariable
    extends Variable
{
    SimpleVariable(final String name)
    {
        super(name);
    }

    @Override
    public String toString()
    {
        return name;
    }
}
