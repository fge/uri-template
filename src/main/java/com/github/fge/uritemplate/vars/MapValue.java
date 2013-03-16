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

package com.github.fge.uritemplate.vars;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public final class MapValue
    extends VariableValue
{
    private final Map<String, String> value;

    public MapValue(final Map<String, String> value)
    {
        super(ValueType.MAP);
        this.value = ImmutableMap.copyOf(value);
    }

    @Override
    public Map<String, String> getMapValue()
    {
        return value;
    }
}
