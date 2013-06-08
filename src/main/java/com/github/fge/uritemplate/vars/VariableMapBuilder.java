package com.github.fge.uritemplate.vars;

import com.github.fge.Thawed;

public final class VariableMapBuilder
    implements Thawed<VariableMap>
{
    VariableMapBuilder(final VariableMap variableMap)
    {
    }

    @Override
    public VariableMap freeze()
    {
        return new VariableMap(this);
    }
}
