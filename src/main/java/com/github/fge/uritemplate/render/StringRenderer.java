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

import com.github.fge.uritemplate.URITemplateException;
import com.github.fge.uritemplate.expression.ExpressionType;
import com.github.fge.uritemplate.vars.specs.VariableSpec;
import com.github.fge.uritemplate.vars.values.VariableValue;
import com.google.common.collect.ImmutableList;

import java.util.List;

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
        throws URITemplateException
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
        final int len = value.length();
        final int prefixLen = varspec.getPrefixLength();
        final String val = prefixLen == -1 ? value
            : value.substring(0, Math.min(len, prefixLen));
        ret += pctEncode(val);
        return ret;
    }
}
