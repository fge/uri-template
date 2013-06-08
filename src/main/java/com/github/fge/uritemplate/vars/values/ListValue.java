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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Variable list value
 */
public final class ListValue
    extends VariableValue
{
    private final List<String> list;

    @Deprecated
    public ListValue(final List<String> list)
    {
        super(ValueType.ARRAY);
        this.list = ImmutableList.copyOf(list);
    }

    private ListValue(final Builder builder)
    {
        super(ValueType.ARRAY);
        list = ImmutableList.copyOf(builder.list);
    }

    public static Builder newBuilder()
    {
        return new Builder();
    }

    public static <T> VariableValue copyOf(final Iterable<T> iterable)
    {
        return new Builder().addAll(iterable).build();
    }

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

    public static final class Builder
    {
        private final List<String> list = Lists.newArrayList();

        private Builder()
        {
        }

        public Builder add(final Object first, final Object... other)
        {
            BUNDLE.checkNotNull(first, "listValue.nullElement");
            list.add(first.toString());
            for (final Object o: other) {
                BUNDLE.checkNotNull(o, "listValue.nullElement");
                list.add(o.toString());
            }
            return addAll(Lists.asList(first, other));
        }

        public <T> Builder addAll(final Iterable<T> iterable)
        {
            BUNDLE.checkNotNull(iterable, "listValue.nullIterable");
            for (final T element: iterable) {
                BUNDLE.checkNotNull(element, "listValue.nullElement");
                list.add(element.toString());
            }
            return this;
        }

        public VariableValue build()
        {
            return new ListValue(this);
        }
    }
}
