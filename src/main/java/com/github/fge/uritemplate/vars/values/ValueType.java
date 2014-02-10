/*
 * Copyright (c) 2014, Francis Galiegue (fgaliegue@gmail.com)
 *
 * This software is dual-licensed under:
 *
 * - the Lesser General Public License (LGPL) version 3.0 or, at your option, any
 *   later version;
 * - the Apache Software License (ASL) version 2.0.
 *
 * The text of both licenses is available under the src/resources/ directory of
 * this project (under the names LGPL-3.0.txt and ASL-2.0.txt respectively).
 *
 * Direct link to the sources:
 *
 * - LGPL 3.0: https://www.gnu.org/licenses/lgpl-3.0.txt
 * - ASL 2.0: http://www.apache.org/licenses/LICENSE-2.0.txt
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
