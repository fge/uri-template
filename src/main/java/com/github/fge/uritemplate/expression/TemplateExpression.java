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

package com.github.fge.uritemplate.expression;

import com.github.fge.uritemplate.URITemplateException;
import com.github.fge.uritemplate.vars.specs.VariableSpec;
import com.github.fge.uritemplate.vars.values.VariableValue;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;

public final class TemplateExpression
    implements URITemplateExpression
{
    private final ExpressionType expressionType;
    private final List<VariableSpec> variableSpecs;

    public TemplateExpression(final ExpressionType expressionType,
        final List<VariableSpec> variableSpecs)
    {
        this.expressionType = expressionType;
        this.variableSpecs = ImmutableList.copyOf(variableSpecs);
    }

    @Override
    public String expand(final Map<String, VariableValue> vars)
        throws URITemplateException
    {
        /*
         * List which will be joined with the expression type's joining
         * character, yielding the final result
         */
        final List<String> list = Lists.newArrayList();

        /*
         * Walk the varspecs list: if the name of the variable exists in the
         * variable map, add the rendered string to the list
         */
        String varname;
        VariableValue value;
        String rendered;
        for (final VariableSpec varspec: variableSpecs) {
            varname = varspec.getName();
            value = vars.get(varname);
            if (value == null)
                continue;
            rendered = varspec.render(expressionType, value);
            if (rendered != null)
                list.add(rendered);
        }

        /*
         * If the list is empty, return the empty string. Otherwise, return the
         * prefix followed by all joined rendered strings.
         */
        if (list.isEmpty())
            return "";

        final Joiner joiner = Joiner.on(expressionType.separator);
        final String joined = joiner.join(list);
        return expressionType.prefix + joined;
    }

    @Override
    public int hashCode()
    {
        return 31 * expressionType.hashCode() + variableSpecs.hashCode();
    }

    @Override
    public boolean equals(final Object obj)
    {
        if (obj == null)
            return false;
        if (this == obj)
            return true;
        if (getClass() != obj.getClass())
            return false;
        final TemplateExpression other = (TemplateExpression) obj;
        return expressionType == other.expressionType
            && variableSpecs.equals(other.variableSpecs);
    }
}
