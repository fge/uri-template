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
 * <p>Also note that null keys or values are not accepted.</p>
 *
 * <p>While there is one public constructor, it is <b>deprecated</b>. Use {@link
 * #copyOf(Map)} instead, or for more control, use a {@link Builder} (see
 * {@link #newBuilder()}).</p>
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

    /**
     * Convenience method to build a variable value from an existing {@link Map}
     *
     * @param map the map
     * @param <T> the type of values in this map
     * @return a new map value as a {@link VariableValue}
     * @throws NullPointerException map is null, or one of its keys or values
     * is null
     */
    public static <T> VariableValue copyOf(final Map<String, T> map)
    {
        return newBuilder().putAll(map).build();
    }

    @Override
    public Map<String, String> getMapValue()
    {
        // Safe: this is an ImmutableMap
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
         * by URIs, it is nicer to the user. And Guava's ImmutableMap respects
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
         * @param <T> the type of the value
         * @return this
         * @throws NullPointerException the key or value is null
         */
        public <T> Builder put(final String key, final T value)
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
