/*
 * Copyright (c) 2014, Francis Galiegue (fgaliegue@gmail.com)
 *
 * This software is dual-licensed under:
 *
 * - the Lesser General Public License (LGPL) version 3.0 or, at your option, any
 *   later version;
 * - the Apache Software License (ASL) version 2.0.
 *
 * The text of this file and of both licenses is available at the root of this
 * project or, if you have the jar distribution, in directory META-INF/, under
 * the names LGPL-3.0.txt and ASL-2.0.txt respectively.
 *
 * Direct link to the sources:
 *
 * - LGPL 3.0: https://www.gnu.org/licenses/lgpl-3.0.txt
 * - ASL 2.0: http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package com.github.fge.uritemplate.render;

import com.github.fge.uritemplate.expression.ExpressionType;
import com.github.fge.uritemplate.vars.values.VariableValue;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;

/**
 * Renderer for map variable values
 */
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
    protected List<String> renderUnnamedExploded(final VariableValue value)
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
    protected List<String> renderUnnamedNormal(final VariableValue value)
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
