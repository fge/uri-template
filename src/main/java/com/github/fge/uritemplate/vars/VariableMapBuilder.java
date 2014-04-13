/*
 * Copyright (c) 2014, Francis Galiegue (fgaliegue@gmail.com)
 *
 * This software is dual-licensed under:
 *
 * - the Lesser General Public License (LGPL) version 3.0 or, at your option, any
 *   later version;
 * - the Apache Software License (ASL) version 2.0.
 *
 * The text of this file and of both licenses is available at the root of this
 * project or, if you have the jar distribution, in directory META-INF/, under
 * the names LGPL-3.0.txt and ASL-2.0.txt respectively.
 *
 * Direct link to the sources:
 *
 * - LGPL 3.0: https://www.gnu.org/licenses/lgpl-3.0.txt
 * - ASL 2.0: http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package com.github.fge.uritemplate.vars;

import com.github.fge.Thawed;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.msgsimple.load.MessageBundles;
import com.github.fge.uritemplate.URITemplateMessageBundle;
import com.github.fge.uritemplate.vars.values.ListValue;
import com.github.fge.uritemplate.vars.values.MapValue;
import com.github.fge.uritemplate.vars.values.ScalarValue;
import com.github.fge.uritemplate.vars.values.VariableValue;
import com.google.common.collect.Maps;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.Map;

/**
 * Builder class for a {@link VariableMap}
 *
 * <p>Use {@link #freeze()} to build the variable map.</p>
 *
 * @see VariableMap#thaw()
 */
@NotThreadSafe
public final class VariableMapBuilder
    implements Thawed<VariableMap>
{
    private static final MessageBundle BUNDLE
        = MessageBundles.getBundle(URITemplateMessageBundle.class);

    final Map<String, VariableValue> vars = Maps.newHashMap();

    VariableMapBuilder()
    {
    }

    VariableMapBuilder(final VariableMap variableMap)
    {
        vars.putAll(variableMap.vars);
    }

    /**
     * Associate a value to a variable name
     *
     * @param varname the variable name
     * @param value the value, as a {@link VariableValue}
     * @return this
     * @throws NullPointerException either the name or the value is null
     */
    public VariableMapBuilder addValue(final String varname,
        final VariableValue value)
    {
        vars.put(
            BUNDLE.checkNotNull(varname, "varmap.nullVarName"),
            BUNDLE.checkNotNull(value, "varmap.nullValue")
        );
        return this;
    }

    /**
     * Shortcut method to associate a name with a scalar value
     *
     * <p>The value argument can be any object, just ensure that it implements
     * {@link Object#toString()} correctly!</p>
     *
     * @param varname the variable name
     * @param value the value
     * @return this
     * @see ScalarValue#ScalarValue(Object)
     */
    public VariableMapBuilder addScalarValue(final String varname,
        final Object value)
    {
        return addValue(varname, new ScalarValue(value));
    }

    /**
     * Shortcut method to associate a name with a list value
     *
     * <p>Any {@link Iterable} can be used (thereby including all collections:
     * sets, lists, etc). Note that it is your  responsibility that objects in
     * this iterable implement {@link Object#toString()} correctly!</p>
     *
     * @param varname the variable name
     * @param iterable the iterable
     * @param <T> type of elements in the iterable
     * @return this
     * @see ListValue#copyOf(Iterable)
     */
    public <T> VariableMapBuilder addListValue(final String varname,
        final Iterable<T> iterable)
    {
        return addValue(varname, ListValue.copyOf(iterable));
    }

    /**
     * Shortcut method to associate a name with a list value
     *
     * <p>This method calls {@link Object#toString()} on each element to add;
     * it is your responsibility to ensure that elements added implement {@link
     * Object#toString()} correctly!</p>
     *
     * @param varname the variable name
     * @param first first element of the list value
     * @param other other elements of the list value, if any
     * @return this
     * @see ListValue#of(Object, Object...)
     */
    public VariableMapBuilder addListValue(final String varname,
        final Object first, final Object... other)
    {
        return addValue(varname, ListValue.of(first, other));
    }

    /**
     * Shortcut method to associate a variable name to a map value
     *
     * <p>Values of the map can be of any type. You should ensure that they
     * implement {@link Object#toString()} correctly!</p>
     *
     * @param varname the variable name
     * @param map the map
     * @param <T> type of map values
     * @return this
     * @see MapValue#copyOf(Map)
     */
    public <T> VariableMapBuilder addMapValue(final String varname,
        final Map<String, T> map)
    {
        return addValue(varname, MapValue.copyOf(map));
    }

    /**
     * Add all variable definitions from another variable map
     *
     * @param other the other variable map to copy definitions from
     * @return this
     * @throws NullPointerException other variable map is null
     */
    public VariableMapBuilder addVariableMap(final VariableMap other)
    {
        BUNDLE.checkNotNull(other, "varmap.nullInput");
        vars.putAll(other.vars);
        return this;
    }

    @Override
    public VariableMap freeze()
    {
        return new VariableMap(this);
    }
}
