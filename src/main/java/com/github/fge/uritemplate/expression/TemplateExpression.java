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

import com.github.fge.uritemplate.vars.VariableSpec;
import com.github.fge.uritemplate.vars.VariableValue;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;

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

    public ExpressionType getExpressionType()
    {
        return expressionType;
    }

    @VisibleForTesting
    public List<VariableSpec> getVariableSpecs()
    {
        return variableSpecs;
    }

    @Override
    public String expand(final Map<String, VariableValue> vars)
    {
        return null;
    }
}
