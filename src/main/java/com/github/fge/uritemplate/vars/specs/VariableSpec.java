package com.github.fge.uritemplate.vars.specs;

/*
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

/**
 * A variable specifier
 *
 * <p>A template expression can have one or more variable specifiers. For
 * instance, in {@code {+path:3,var}}, variable specifiers are {@code path:3}
 * and {@code var}.</p>
 *
 * <p>This class therefore records the name of this specifier and its modifier,
 * if any.</p>
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

    /**
     * Get the modifier type for this varspec
     *
     * @return the modifier type
     */
    public final VariableSpecType getType()
    {
        return type;
    }

    /**
     * Get the name for this varspec
     *
     * @return the name
     */
    public final String getName()
    {
        return name;
    }

    /**
     * Tell whether this varspec has an explode modifier
     *
     * @return true if an explode modifier is present
     */
    public abstract boolean isExploded();

    /**
     * Return the prefix length for this varspec
     *
     * <p>Returns -1 if no prefix length is specified. Recall: valid values are
     * integers between 0 and 10000.</p>
     *
     * @return the prefix length, or -1 if no prefix modidifer
     */
    public abstract int getPrefixLength();

    @Override
    public abstract int hashCode();

    @Override
    public abstract boolean equals(final Object obj);

    @Override
    public abstract String toString();
}
