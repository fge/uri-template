/*
 * Copyright (c) 2012, Francis Galiegue <fgaliegue@gmail.com>
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

package org.eel.kitchen.uritemplate.expression;

import com.google.common.collect.ImmutableSet;
import org.eel.kitchen.uritemplate.expression.variable.Variable;

import java.util.Collection;

public final class Expression
{
    private final Variable variable;

    public Expression(final ExpressionBuilder builder)
    {
        variable = builder.variables.get(0);
    }

    public Collection<Variable> getVariables()
    {
        return ImmutableSet.of(variable);
    }
}
