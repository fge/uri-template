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

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.eel.kitchen.uritemplate.InvalidTemplateException;

import java.util.List;

public final class ExpressionParser
{
    // What separates variable names in an expression (after operators)
    private static final Splitter SPLITTER = Splitter.on(',');

    private ExpressionParser()
    {
    }

    public static Expression parse(final String input)
        throws InvalidTemplateException
    {
        final List<String> list = Lists.newArrayList();

        final TemplateOperator operator = TemplateOperator.detectFrom(input);

        for (final String varName: SPLITTER.split(operator.varSpecs(input))) {
            parseVariableName(varName);
            list.add(varName);
        }
        return new Expression(list);
    }

    private static void parseVariableName(final String s)
        throws InvalidTemplateException
    {
        if (s.isEmpty())
            throw new InvalidTemplateException("variable names cannot be " +
                "empty");
    }
}
