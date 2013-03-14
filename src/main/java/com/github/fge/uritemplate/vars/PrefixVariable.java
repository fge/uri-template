package com.github.fge.uritemplate.vars;

public final class PrefixVariable
    extends VariableSpec
{
    private final int length;

    public PrefixVariable(final String name, final int length)
    {
        super(name);
        this.length = length;
    }
}
