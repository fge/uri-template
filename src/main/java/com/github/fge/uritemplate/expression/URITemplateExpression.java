package com.github.fge.uritemplate.expression;

import com.github.fge.uritemplate.vars.VariableValue;

import java.util.Map;

public interface URITemplateExpression
{
    String expand(final Map<String, VariableValue> vars);
}
