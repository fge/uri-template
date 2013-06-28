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

import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.NotThreadSafe;
import java.util.Map;

/**
 * Map variable value
 *
 * <p>Note that some methods allow to pass values of arbitrary type. It is the
 * caller's responsibility to ensure that these values have a correct {@link
 * Object#toString() .toString()} implementation.</p>
 *
 * <p>While there is one public constructor, it is <b>deprecated</b>. Use a
 * {@link Builder} instead (see {@link #newBuilder()}).</p>
 */
@Immutable
public final class MapValue
    extends VariableValue
{
    private final Map<String, String> map;

    private MapValue(final Builder builder)
    {
        super(ValueType.MAP);
        map = ImmutableMap.copyOf(builder.map);
    }

    /**
     * Create a new builder for this class
     *
     * @return a {@link Builder}
     */
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

    /**
     * Builder class for a {@link MapValue}
     */
    @NotThreadSafe
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

        /**
         * Add one key/value pair to the map
         *
         * @param key the key
         * @param value the value
         * @return this
         * @throws NullPointerException the key or value is null
         */
        public Builder put(final String key, final Object value)
        {
            map.put(
                BUNDLE.checkNotNull(key, "mapValue.nullKey"),
                BUNDLE.checkNotNull(value, "mapValue.nullValue").toString()
            );
            return this;
        }

        /**
         * Inject a map of key/value pairs
         *
         * @param map the map
         * @param <T> the type of this map's values
         * @return this
         * @throws NullPointerException map is null, or one of its keys or
         * values is null
         */
        public <T> Builder putAll(final Map<String, T> map)
        {
            BUNDLE.checkNotNull(map, "mapValue.nullMap");
            for (final Map.Entry<String, T> entry: map.entrySet())
                put(entry.getKey(), entry.getValue());
            return this;
        }

        /**
         * Build the value
         *
         * @return the map value as a {@link VariableValue}
         */
        public VariableValue build()
        {
            return new MapValue(this);
        }
    }
}