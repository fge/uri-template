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
import com.google.common.base.Joiner;

import java.util.List;

public abstract class MultiValueRenderer
    extends ValueRenderer
{
    protected static final Joiner COMMA = Joiner.on(',');

    protected MultiValueRenderer(final ExpressionType type)
    {
        super(type);
    }

    @Override
    public final List<String> render(final VariableSpec varspec,
        final VariableValue value)
        throws URITemplateException
    {
        final String varname = varspec.getName();

        if (named)
            return varspec.isExploded() ? renderNamedExploded(varname, value)
                : renderNamedNormal(varname, value);
        else
            return varspec.isExploded() ? renderUnnamedExploded(varname, value)
                : renderUnnamedNormal(varname, value);
    }

    protected abstract List<String> renderNamedExploded(final String varname,
        final VariableValue value);

    protected abstract List<String> renderUnnamedExploded(final String varname,
        final VariableValue value);

    protected abstract List<String> renderNamedNormal(final String varname,
        final VariableValue value);

    protected abstract List<String> renderUnnamedNormal(final String varname,
        final VariableValue value);
}
