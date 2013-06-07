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

package com.github.fge.uritemplate.vars.values;

import java.util.List;
import java.util.Map;

/**
 * Value for one variable used for the expansion process
 */
public abstract class VariableValue
{
    protected final ValueType type;

    protected VariableValue(final ValueType type)
    {
        this.type = type;
    }

    /**
     * Get the type for this value
     *
     * @return the value type
     */
    public final ValueType getType()
    {
        return type;
    }

    /**
     * Get a simple string for this value
     *
     * <p>Only valid for string values</p>
     *
     * @return the string
     * @throws IllegalStateException value is not a string value
     */
    public String getScalarValue()
    {
        throw new IllegalStateException();
    }

    /**
     * Get a list for this value
     *
     * <p>Only valid for list values</p>
     *
     * @return the list
     * @throws IllegalStateException value is not a list value
     */
    public List<String> getListValue()
    {
        throw new IllegalStateException();
    }

    /**
     * Get a map for this value
     *
     * <p>Only valid for map values</p>
     *
     * @return the map
     * @throws IllegalStateException value is not a map value
     */
    public Map<String, String> getMapValue()
    {
        throw new IllegalStateException();
    }

    /**
     * Tell whether this value is empty
     *
     * <p>For strings, this tells whether the string itself is empty. For lists
     * and maps, this tells whether the list or map have no elements/entries.
     * </p>
     *
     * @return true if the value is empty
     */
    public abstract boolean isEmpty();
}
