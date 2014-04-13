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

/**
 * Renderer for list variable values
 */
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
