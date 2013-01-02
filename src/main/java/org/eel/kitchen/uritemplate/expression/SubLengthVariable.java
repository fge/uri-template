package org.eel.kitchen.uritemplate.expression;

final class SubLengthVariable
    extends Variable
{
    private final int subLength;

    SubLengthVariable(final String name, final int subLength)
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
