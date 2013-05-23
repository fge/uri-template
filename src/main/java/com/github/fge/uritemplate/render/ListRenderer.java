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

public final class ListRenderer
    extends MultiValueRenderer
{
    public ListRenderer(final ExpressionType type)
    {
        super(type);
    }

    @Override
    protected List<String> renderNamedExploded(final String varname,
        final VariableValue value)
    {
        final List<String> ret = Lists.newArrayList();

        for (final String element: value.getListValue())
            ret.add(element.isEmpty() ? varname + ifEmpty
                : varname + '=' + pctEncode(element));

        return ret;
    }

    @Override
    protected List<String> renderUnnamedExploded(final VariableValue value)
    {
        final List<String> ret = Lists.newArrayList();

        for (final String element: value.getListValue())
            ret.add(pctEncode(element));

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

        for (final String element: value.getListValue())
            elements.add(pctEncode(element));

        COMMA.appendTo(sb, elements);

        return ImmutableList.of(sb.toString());
    }

    @Override
    protected List<String> renderUnnamedNormal(final VariableValue value)
    {
        if (value.isEmpty())
            return ImmutableList.of();

        final List<String> ret = Lists.newArrayList();

        for (final String element: value.getListValue())
            ret.add(pctEncode(element));

        return ImmutableList.of(COMMA.join(ret));
    }
}
