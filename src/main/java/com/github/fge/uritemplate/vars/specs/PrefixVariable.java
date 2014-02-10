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
 * A varspec with a prefix modifier (for instance, {@code foo:3} in {@code
 * {foo:3}}
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
