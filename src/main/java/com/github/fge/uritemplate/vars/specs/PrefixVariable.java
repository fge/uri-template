package com.github.fge.uritemplate.vars.specs;

/**
 * A varspec with a prefix modifier
 */
public final class PrefixVariable
    extends VariableSpec
{
    private final int length;

    public PrefixVariable(final String name, final int length)
    {
        super(VariableSpecType.PREFIX, name);
        this.length = length;
    }

    @Override
    public boolean isExploded()
    {
        return false;
    }

    @Override
    public int getPrefixLength()
    {
        return length;
    }

    @Override
    public int hashCode()
    {
        return 31 * name.hashCode() + length;
    }

    @Override
    public boolean equals(final Object obj)
    {
        if (obj == null)
            return false;
        if (this == obj)
            return true;
        if (getClass() != obj.getClass())
            return false;
        final PrefixVariable other = (PrefixVariable) obj;
        return name.equals(other.name) && length == other.length;
    }

    @Override
    public String toString()
    {
        return name + " (prefix length: " + length + ')';
    }
}
