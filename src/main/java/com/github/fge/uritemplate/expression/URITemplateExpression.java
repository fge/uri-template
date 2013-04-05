package com.github.fge.uritemplate.expression;

import com.github.fge.uritemplate.URITemplateException;
import com.github.fge.uritemplate.vars.values.VariableValue;

import java.util.Map;

public interface URITemplateExpression
{
    String expand(final Map<String, VariableValue> vars)
        throws URITemplateException;
}
