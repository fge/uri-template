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

package com.github.fge.uritemplate.vars.specs;

/**
 * Enumeration of a variable modifier type
 */
public enum VariableSpecType
{
    /**
     * No modifier
     */
    SIMPLE,
    /**
     * Prefix modifier ({@code :xxx} where {@code xxx} is an integer)
     *
     * <p>Only makes sense for string values.</p>
     */
    PREFIX,
    /**
     * Explode modifier ({@code *})
     *
     * <p>Only makes sense for list and map values.</p>
     */
    EXPLODED
}
