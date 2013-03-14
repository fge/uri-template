package com.github.fge.uritemplate.expression;

import com.github.fge.uritemplate.vars.VariableSpec;

import java.util.Map;

public final class TemplateLiteral
    implements URITemplateExpression
{
    private final String literal;

    public TemplateLiteral(final String literal)
    {
        this.literal = literal;
    }

    @Override
    public String expand(final Map<String, VariableSpec> vars)
    {
        return literal;
    }
}
