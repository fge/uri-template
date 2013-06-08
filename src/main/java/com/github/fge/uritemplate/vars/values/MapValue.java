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

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * Map variable value
 *
 * <p>Note: the RFC calls these "associative arrays".</p>
 */
public final class MapValue
    extends VariableValue
{
    private final Map<String, String> map;

    public MapValue(final Map<String, String> map)
    {
        super(ValueType.MAP);
        this.map = ImmutableMap.copyOf(map);
    }

    @Override
    public Map<String, String> getMapValue()
    {
        return map;
    }

    @Override
    public boolean isEmpty()
    {
        return map.isEmpty();
    }
}