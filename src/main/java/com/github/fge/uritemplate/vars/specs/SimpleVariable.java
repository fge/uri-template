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
 * A varspec with no modifier
 */
public final class SimpleVariable
    extends VariableSpec
{
    public SimpleVariable(final String name)
    {
        super(VariableSpecType.SIMPLE, name);
    }

    @Override
    public boolean isExploded()
    {
        return false;
    }

    @Override
    public int getPrefixLength()
    {
        return -1;
    }

    @Override
    public int hashCode()
    {
        return name.hashCode();
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
        final SimpleVariable other = (SimpleVariable) obj;
        return name.equals(other.name);
    }

    @Override
    public String toString()
    {
        return name + " (simple)";
    }
}
