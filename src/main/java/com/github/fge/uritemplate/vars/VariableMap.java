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

package com.github.fge.uritemplate.vars;

import com.github.fge.Frozen;
import com.github.fge.uritemplate.URITemplate;
import com.github.fge.uritemplate.vars.values.VariableValue;
import com.google.common.collect.ImmutableMap;

import javax.annotation.concurrent.Immutable;
import java.util.Map;

/**
 * Container class for variables name/value pairs
 *
 * <p>You must use a builder for building an instance of this class, using the
 * {@link #newBuilder()} method.</p>
 *
 * <p>Note that this class uses the freeze/thaw pattern. You can therefore
 * "thaw" an instance of this class in order to obtain a builder again.</p>
 *
 * @see VariableMapBuilder
 * @see URITemplate#toString(VariableMap)
 * @see URITemplate#toURI(VariableMap)
 * @see URITemplate#toURL(VariableMap)
 */
@Immutable
public final class VariableMap
    implements Frozen<VariableMapBuilder>
{
    final Map<String, VariableValue> vars;

    VariableMap(final VariableMapBuilder builder)
    {
        vars = ImmutableMap.copyOf(builder.vars);
    }

    /**
     * Create a new builder for this class
     *
     * @return a {@link VariableMapBuilder}
     */
    public static VariableMapBuilder newBuilder()
    {
        return new VariableMapBuilder();
    }

    /**
     * Get the value associated with a variable name
     *
     * @param varname the variable name
     * @return the value, or {@code null} if there is no matching value
     */
    public VariableValue get(final String varname)
    {
        return vars.get(varname);
    }

    /**
     * Return a thawed version of this instance
     *
     * @return a {@link VariableMapBuilder} filled with name/value pairs of this
     * instance
     */
    @Override
    public VariableMapBuilder thaw()
    {
        return new VariableMapBuilder(this);
    }
}
