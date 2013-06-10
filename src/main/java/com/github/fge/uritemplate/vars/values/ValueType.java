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

package com.github.fge.uritemplate.vars.values;

import com.github.fge.uritemplate.expression.ExpressionType;
import com.github.fge.uritemplate.render.ListRenderer;
import com.github.fge.uritemplate.render.MapRenderer;
import com.github.fge.uritemplate.render.StringRenderer;
import com.github.fge.uritemplate.render.ValueRenderer;

/**
 * Enumeration of the different types of variable values
 *
 * <p>The type of the value also determines how it is expanded. Therefore, this
 * enumeration also instantiates renderers.</p>
 *
 * @see ValueRenderer
 */
public enum ValueType
{
    /**
     * Scalar values (simple string values)
     */
    SCALAR("scalar")
    {
        @Override
        public ValueRenderer selectRenderer(final ExpressionType type)
        {
            return new StringRenderer(type);
        }
    },
    /**
     * Array/list values
     */
    ARRAY("list")
    {
        @Override
        public ValueRenderer selectRenderer(final ExpressionType type)
        {
            return new ListRenderer(type);
        }
    },
    /**
     * Map values
     *
     * <p>Note: the RFC calls these "associative arrays".</p>
     */
    MAP("map")
    {
        @Override
        public ValueRenderer selectRenderer(final ExpressionType type)
        {
            return new MapRenderer(type);
        }
    };

    private final String name;

    ValueType(final String name)
    {
        this.name = name;
    }

    /**
     * Get the renderer for this value type and expression type
     *
     * @param type the expression type
     * @return the appropriate renderer
     */
    public abstract ValueRenderer selectRenderer(final ExpressionType type);

    @Override
    public String toString()
    {
        return name;
    }
}
