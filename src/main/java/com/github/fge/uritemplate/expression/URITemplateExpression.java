package com.github.fge.uritemplate.expression;

import com.github.fge.uritemplate.vars.TemplateVariable;

import java.util.Map;

public interface URITemplateExpression
{
    String expand(final Map<String, TemplateVariable> vars);
}
