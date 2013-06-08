package com.github.fge.uritemplate.vars;

import com.github.fge.Thawed;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.msgsimple.serviceloader.MessageBundles;
import com.github.fge.uritemplate.URITemplateMessageBundle;
import com.github.fge.uritemplate.vars.values.ScalarValue;
import com.github.fge.uritemplate.vars.values.VariableValue;
import com.google.common.collect.Maps;

import java.util.Map;

public final class VariableMapBuilder
    implements Thawed<VariableMap>
{
    private static final MessageBundle BUNDLE
        = MessageBundles.forClass(URITemplateMessageBundle.class);

    final Map<String, VariableValue> vars = Maps.newHashMap();

    VariableMapBuilder()
    {
    }

    VariableMapBuilder(final VariableMap variableMap)
    {
        vars.putAll(variableMap.vars);
    }

    public VariableMapBuilder setScalar(final String varname,
        final Object value)
    {
        vars.put(BUNDLE.checkNotNull(varname, "varmap.nullVarName"),
            new ScalarValue(value));
        return this;
    }

    @Override
    public VariableMap freeze()
    {
        return new VariableMap(this);
    }
}
