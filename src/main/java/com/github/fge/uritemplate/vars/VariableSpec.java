package com.github.fge.uritemplate.vars;

/*
 * TODO: intelligence here
 *
 * A varspec has all the necessary elements to render according to the needs. It
 * needs two elements to operate:
 *
 * - the type of the value;
 * - the type of the expression.
 *
 * According to its own, internal information, its rendering method (name to be
 * decided) should be able to return a String to the caller with the appropriate
 * contents.
 */
public abstract class VariableSpec
{
    protected final VariableSpecType type;
    protected final String name;

    protected VariableSpec(final VariableSpecType type, final String name)
    {
        this.type = type;
        this.name = name;
    }

    public VariableSpecType getType()
    {
        return type;
    }

    public final String getName()
    {
        return name;
    }

    @Override
    public abstract int hashCode();

    @Override
    public abstract boolean equals(final Object obj);

    @Override
    public abstract String toString();
}
