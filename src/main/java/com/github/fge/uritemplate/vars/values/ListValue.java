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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.NotThreadSafe;
import java.util.List;

/**
 * List variable value
 *
 * <p>Note that several methods (in this class or the enclosed {@link Builder}
 * class) can take any object as an argument. It is the caller's responsibility
 * to ensure that these objects have a suitable {@link Object#toString()}
 * .toString()} implementation.</p>
 *
 * <p>While a public constructor exists, it is <b>deprecated</b>. Use one of
 * the factory methods instead, or a {@link Builder} (see {@link
 * #newBuilder()}).</p>
 */
@Immutable
public final class ListValue
    extends VariableValue
{
    private final List<String> list;

    private ListValue(final Builder builder)
    {
        super(ValueType.ARRAY);
        list = ImmutableList.copyOf(builder.list);
    }

    /**
     * Create a new list value builder
     *
     * @return a builder
     */
    public static Builder newBuilder()
    {
        return new Builder();
    }

    /**
     * Build a list value out of an existing iterable (list, set, other)
     *
     * <p>This calls {@link Builder#addAll(Iterable)} internally.</p>
     *
     * @param iterable the iterable
     * @param <T> the type of iterable elements
     * @return a new list value
     */
    public static <T> VariableValue copyOf(final Iterable<T> iterable)
    {
        return new Builder().addAll(iterable).build();
    }

    /**
     * Build a list value out of a series of elements
     *
     * <p>This calls {@link Builder#add(Object, Object...)} internally.</p>
     *
     * @param first first element
     * @param other other elements, if any
     * @return a new list value
     */
    public static VariableValue of(final Object first, final Object... other)
    {
        return new Builder().add(first, other).build();
    }

    @Override
    public List<String> getListValue()
    {
        return list;
    }

    @Override
    public boolean isEmpty()
    {
        return list.isEmpty();
    }

    /**
     * Builder class for a {@link ListValue}
     */
    @NotThreadSafe
    public static final class Builder
    {
        private final List<String> list = Lists.newArrayList();

        private Builder()
        {
        }

        /**
         * Add a series of elements to this list
         *
         * @param first first element
         * @param other other elements, if any
         * @return this
         * @throws NullPointerException one argument at least is null
         */
        public Builder add(final Object first, final Object... other)
        {
            BUNDLE.checkNotNull(first, "listValue.nullElement");
            list.add(first.toString());
            for (final Object o: other) {
                BUNDLE.checkNotNull(o, "listValue.nullElement");
                list.add(o.toString());
            }
            return this;
        }

        /**
         * Add elements from an iterable (list, set, other)
         *
         * @param iterable the iterable
         * @param <T> type of elements in the iterable
         * @return this
         * @throws NullPointerException the iterable is null, or one of its
         * elements is null
         */
        public <T> Builder addAll(final Iterable<T> iterable)
        {
            BUNDLE.checkNotNull(iterable, "listValue.nullIterable");
            for (final T element: iterable) {
                BUNDLE.checkNotNull(element, "listValue.nullElement");
                list.add(element.toString());
            }
            return this;
        }

        /**
         * Build the value
         *
         * @return the list value as a {@link VariableValue}
         */
        public VariableValue build()
        {
            return new ListValue(this);
        }
    }
}
