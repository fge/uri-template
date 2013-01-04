package org.eel.kitchen.uritemplate.expression;

final class SimpleVarSpec
    extends VarSpec
{
    SimpleVarSpec(final String name)
    {
        super(name);
    }

    @Override
    public String toString()
    {
        return name;
    }
}
