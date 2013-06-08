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
import com.google.common.collect.Maps;

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

    @Deprecated
    public MapValue(final Map<String, String> map)
    {
        super(ValueType.MAP);
        this.map = ImmutableMap.copyOf(map);
    }

    private MapValue(final Builder builder)
    {
        super(ValueType.MAP);
        map = ImmutableMap.copyOf(builder.map);
    }

    public static Builder newBuilder()
    {
        return new Builder();
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

    public static final class Builder
    {
        /*
         * We use a LinkedHashMap to respect insertion order. While not required
         * by URI, it is nicer to the user. And Guava's ImmutableMap respects
         * insertion order as well.
         */
        private final Map<String, String> map = Maps.newLinkedHashMap();

        private Builder()
        {
        }

        public Builder put(final String key, final Object value)
        {
            map.put(
                BUNDLE.checkNotNull(key, "mapValue.nullKey"),
                BUNDLE.checkNotNull(value, "mapValue.nullValue").toString()
            );
            return this;
        }

        public VariableValue build()
        {
            return new MapValue(this);
        }
    }
}