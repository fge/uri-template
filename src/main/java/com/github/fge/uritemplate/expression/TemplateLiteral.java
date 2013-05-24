package com.github.fge.uritemplate.expression;

import com.github.fge.uritemplate.URITemplateException;
import com.github.fge.uritemplate.vars.values.VariableValue;

import java.util.Map;

/**
 * Literal expander
 *
 * <p>This covers all strings inbetween actual URI template expressions.</p>
 */
public final class TemplateLiteral
    implements URITemplateExpression
{
    private final String literal;

    public TemplateLiteral(final String literal)
    {
        this.literal = literal;
    }

    @Override
    public String expand(final Map<String, VariableValue> vars)
        throws URITemplateException
    {
        return literal;
    }
}
