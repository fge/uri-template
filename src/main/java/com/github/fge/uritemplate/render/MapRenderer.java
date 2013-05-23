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

package com.github.fge.uritemplate.render;

import com.github.fge.uritemplate.expression.ExpressionType;
import com.github.fge.uritemplate.vars.values.VariableValue;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;

public final class MapRenderer
    extends MultiValueRenderer
{
    public MapRenderer(final ExpressionType type)
    {
        super(type);
    }

    @Override
    protected List<String> renderNamedExploded(final String varname,
        final VariableValue value)
    {
        final List<String> ret = Lists.newArrayList();
        final Map<String, String> map = value.getMapValue();

        StringBuilder element;
        String val;

        for (final Map.Entry<String, String> entry: map.entrySet()) {
            element = new StringBuilder(pctEncode(entry.getKey()));
            val = entry.getValue();
            if (val.isEmpty())
                element.append(ifEmpty);
            else
                element.append('=').append(pctEncode(val));
            ret.add(element.toString());
        }

        return ret;
    }

    @Override
    protected List<String> renderUnnamedExploded(final String varname,
        final VariableValue value)
    {
        final List<String> ret = Lists.newArrayList();
        final Map<String, String> map = value.getMapValue();

        for (final Map.Entry<String, String> entry: map.entrySet())
            ret.add(pctEncode(entry.getKey()) + '='
                + pctEncode(entry.getValue()));

        return ret;
    }

    @Override
    protected List<String> renderNamedNormal(final String varname,
        final VariableValue value)
    {
        final StringBuilder sb = new StringBuilder(varname);

        if (value.isEmpty())
            return ImmutableList.of(sb.append(ifEmpty).toString());

        sb.append('=');

        final List<String> elements = Lists.newArrayList();

        for (final String element: mapAsList(value))
            elements.add(pctEncode(element));

        COMMA.appendTo(sb, elements);

        return ImmutableList.of(sb.toString());
    }

    @Override
    protected List<String> renderUnnamedNormal(final String varname,
        final VariableValue value)
    {
        if (value.isEmpty())
            return ImmutableList.of();

        final List<String> ret = Lists.newArrayList();

        for (final String element: mapAsList(value))
            ret.add(pctEncode(element));

        return ImmutableList.of(COMMA.join(ret));
    }

    private static List<String> mapAsList(final VariableValue value)
    {
        final List<String> ret = Lists.newArrayList();
        final Map<String, String> map = value.getMapValue();

        for (final Map.Entry<String, String> entry: map.entrySet()) {
            ret.add(entry.getKey());
            ret.add(entry.getValue());
        }

        return ret;
    }
}
