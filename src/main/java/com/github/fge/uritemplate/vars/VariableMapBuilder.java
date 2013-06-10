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

    public VariableMapBuilder addValue(final String varname,
        final VariableValue value)
    {
        vars.put(
            BUNDLE.checkNotNull(varname, "varmap.nullVarName"),
            BUNDLE.checkNotNull(value, "varmap.nullValue")
        );
        return this;
    }

    public VariableMapBuilder addScalarValue(final String varname,
        final Object value)
    {
        return addValue(varname, new ScalarValue(value));
    }

    public <T> VariableMapBuilder addListValue(final String varname,
        final Iterable<T> iterable)
    {
        return addValue(varname, ListValue.copyOf(iterable));
    }

    public VariableMapBuilder addListValue(final String varname,
        final Object first, final Object... other)
    {
        return addValue(varname, ListValue.of(first, other));
    }

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
