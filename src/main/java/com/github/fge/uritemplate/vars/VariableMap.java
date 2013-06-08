package com.github.fge.uritemplate.vars;

import com.github.fge.Frozen;

public final class VariableMap
    implements Frozen<VariableMapBuilder>
{
    VariableMap(final VariableMapBuilder builder)
    {
    }

    @Override
    public VariableMapBuilder thaw()
    {
        return new VariableMapBuilder(this);
    }
}
