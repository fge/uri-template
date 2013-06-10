/*
 * Copyright (c) 2013, Francis Galiegue <fgaliegue@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Lesser GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Lesser GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
