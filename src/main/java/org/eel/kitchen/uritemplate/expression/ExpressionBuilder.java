package org.eel.kitchen.uritemplate.expression;

import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

final class ExpressionBuilder
{
    Operator operator;
    List<VarSpec> varSpecs;

    void setOperator(final Operator operator)
    {
        this.operator = operator;
    }

    void setVarSpecs(final Iterable<VarSpec> iterable)
    {
        varSpecs = Lists.newArrayList(iterable);
        Collections.reverse(varSpecs);
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder("operator: ");
        sb.append(operator == null ? "(none)" : operator)
            .append("; varSpecs: ");
        return sb.append(varSpecs).toString();
    }
}
