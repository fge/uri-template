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

import com.github.fge.uritemplate.URITemplateException;
import com.github.fge.uritemplate.expression.ExpressionType;
import com.github.fge.uritemplate.vars.specs.VariableSpec;
import com.github.fge.uritemplate.vars.values.VariableValue;
import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 * String value renderer
 *
 * <p>Rendering of a string value is the only event where the returned list
 * cannot be empty. Concurrently, it will always have a single element.</p>
 */
public final class StringRenderer
    extends ValueRenderer
{
    public StringRenderer(final ExpressionType type)
    {
        super(type);
    }

    @Override
    public List<String> render(final VariableSpec varspec,
        final VariableValue value)
        throws URITemplateException
    {
        return ImmutableList.of(doRender(varspec, value.getScalarValue()));
    }

    private String doRender(final VariableSpec varspec, final String value)
    {
        String ret = "";
        if (named) {
            // Note: variable names do not contain any character susceptible to
            // be percent encoded, so we leave them intact
            ret += varspec.getName();
            if (value.isEmpty())
                return ret + ifEmpty;
            ret += '=';
        }
        // Account for a prefix, if any. Note: explode modifier is ignored.
        final int len = value.length();
        final int prefixLen = varspec.getPrefixLength();
        final String val = prefixLen == -1 ? value
            : value.substring(0, Math.min(len, prefixLen));
        ret += pctEncode(val);
        return ret;
    }
}
