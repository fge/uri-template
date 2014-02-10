/*
 * Copyright (c) 2014, Francis Galiegue (fgaliegue@gmail.com)
 *
 * This software is dual-licensed under:
 *
 * - the Lesser General Public License (LGPL) version 3.0 or, at your option, any
 *   later version;
 * - the Apache Software License (ASL) version 2.0.
 *
 * The text of both licenses is available under the src/resources/ directory of
 * this project (under the names LGPL-3.0.txt and ASL-2.0.txt respectively).
 *
 * Direct link to the sources:
 *
 * - LGPL 3.0: https://www.gnu.org/licenses/lgpl-3.0.txt
 * - ASL 2.0: http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package com.github.fge.uritemplate.vars.specs;

/**
 * A variable specifier
 *
 * <p>A template expression can have one or more variable specifiers. For
 * instance, in {@code {+path:3,var}}, variable specifiers are {@code path:3}
 * and {@code var}.</p>
 *
 * <p>This class records the name of this specifier and its modifier, if any.
 * </p>
 */
public abstract class VariableSpec
{
    private final VariableSpecType type;
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
