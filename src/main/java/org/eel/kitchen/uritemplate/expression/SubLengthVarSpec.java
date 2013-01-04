package org.eel.kitchen.uritemplate.expression;

final class SubLengthVarSpec
    extends VarSpec
{
    private final int subLength;

    SubLengthVarSpec(final String name, final int subLength)
    {
        super(name);
        this.subLength = subLength;
    }

    @Override
    public String toString()
    {
        return name + " (up to " + subLength + " chars)";
    }
}
