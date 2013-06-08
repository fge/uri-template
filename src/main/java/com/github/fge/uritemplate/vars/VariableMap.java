package com.github.fge.uritemplate.vars;

import com.github.fge.Frozen;
import com.github.fge.uritemplate.vars.values.VariableValue;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

public final class VariableMap
    implements Frozen<VariableMapBuilder>
{
    final Map<String, VariableValue> vars;

    VariableMap(final VariableMapBuilder builder)
    {
        vars = ImmutableMap.copyOf(builder.vars);
    }

    public static VariableMapBuilder newBuilder()
    {
        return new VariableMapBuilder();
    }

    @Override
    public VariableMapBuilder thaw()
    {
        return new VariableMapBuilder(this);
    }
}
