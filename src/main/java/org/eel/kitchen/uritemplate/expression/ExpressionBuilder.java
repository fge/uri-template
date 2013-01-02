package org.eel.kitchen.uritemplate.expression;

import com.google.common.collect.Lists;
import org.eel.kitchen.uritemplate.expression.variable.Variable;

import java.util.Collections;
import java.util.List;

public final class ExpressionBuilder
{
    private Operator operator;
    private List<Variable> variables;

    public void setOperator(final Operator operator)
    {
        this.operator = operator;
    }

    public void setVariables(final Iterable<Variable> iterable)
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
