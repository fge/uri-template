package org.eel.kitchen.uritemplate.expression;

final class ExplodedVarSpec
    extends VarSpec
{
    ExplodedVarSpec(final String name)
    {
        super(name);
    }

    @Override
    public String toString()
    {
        return name + " (exploded)";
    }
}
