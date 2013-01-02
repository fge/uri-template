package org.eel.kitchen.uritemplate.expression;

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
