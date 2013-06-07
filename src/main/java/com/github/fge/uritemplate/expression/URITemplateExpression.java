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
import com.github.fge.uritemplate.vars.values.VariableValue;

import java.util.Map;

/**
 * Generic URI template expression
 *
 * <p>This interface covers literal expansions (ie, not expressions) and
 * expression expansions.</p>
 */
public interface URITemplateExpression
{
    /**
     * Compute an expanded string given a map of variables
     *
     * @param vars the variables (names and values)
     * @return the expanded string
     * @throws URITemplateException illegal parsing
     */
    String expand(final Map<String, VariableValue> vars)
        throws URITemplateException;
}
