package com.github.fge.uritemplate.expression;

import com.github.fge.uritemplate.URITemplateException;
import com.github.fge.uritemplate.vars.values.VariableValue;

import java.util.Map;

/**
 * Generic URI template expression
 *
 * <p>This interface covers literal expansions (ie, not expressions) and
 * expression expansions.</p>
 */
public interface URITemplateExpression
{
    /**
     * Compute an expanded string given a map of variables
     *
     * @param vars the variables (names and values)
     * @return the expanded string
     * @throws URITemplateException illegal parsing
     */
    String expand(final Map<String, VariableValue> vars)
        throws URITemplateException;
}
