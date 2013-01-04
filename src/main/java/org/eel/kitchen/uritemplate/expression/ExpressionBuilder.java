package org.eel.kitchen.uritemplate.expression;

import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

final class ExpressionBuilder
{
    Operator operator;
    List<Variable> variables;

    void setOperator(final Operator operator)
    {
        this.operator = operator;
    }

    void setVariables(final Iterable<Variable> iterable)
    {
        variables = Lists.newArrayList(iterable);
        Collections.reverse(variables);
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder("operator: ");
        sb.append(operator == null ? "(none)" : operator)
            .append("; variables: ");
        return sb.append(variables).toString();
    }
}
