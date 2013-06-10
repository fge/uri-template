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
import com.github.fge.uritemplate.render.ValueRenderer;
import com.github.fge.uritemplate.vars.VariableMap;
import com.github.fge.uritemplate.vars.specs.VariableSpec;
import com.github.fge.uritemplate.vars.values.VariableValue;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Template expression (ie, not literal) rendering class
 *
 * <p>This class ultimately handles all {@code {...}} sequences found in a
 * URI template.</p>
 *
 * @see ValueRenderer
 */
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
    public String expand(final VariableMap vars)
        throws URITemplateException
    {
        /*
         * Near exact reproduction of the suggested algorithm, with two
         * differences:
         *
         * - parsing errors are treated elsewhere;
         * - the possibility of varspecs both exploded and prefixed is left
         *   open; not here: it is one or the other, not both.
         */

        // Expanded values
        final List<String> expansions = Lists.newArrayList();

        VariableValue value;
        ValueRenderer renderer;

        /*
         * Walk over the defined varspecs for this template
         */
        for (final VariableSpec varspec: variableSpecs) {
            value = vars.get(varspec.getName());
            // No such variable: continue
            if (value == null)
                continue;
            renderer = value.getType().selectRenderer(expressionType);
            expansions.addAll(renderer.render(varspec, value));
        }

        if (expansions.isEmpty())
            return "";
        final Joiner joiner = Joiner.on(expressionType.getSeparator());
        // Where the final result is stored
        final StringBuilder sb = new StringBuilder(expressionType.getPrefix());
        joiner.appendTo(sb, expansions);
        return sb.toString();
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
