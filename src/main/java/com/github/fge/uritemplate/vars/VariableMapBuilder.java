package com.github.fge.uritemplate.vars;

import com.github.fge.Thawed;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.msgsimple.serviceloader.MessageBundleFactory;
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
        = MessageBundleFactory.getBundle(URITemplateMessageBundle.class);

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
     * @see MapValue.Builder#putAll(Map)
     */
    public <T> VariableMapBuilder addMapValue(final String varname,
        final Map<String, T> map)
    {
        return addValue(varname, MapValue.newBuilder().putAll(map).build());
    }

    @Override
    public VariableMap freeze()
    {
        return new VariableMap(this);
    }
}
