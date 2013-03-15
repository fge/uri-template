package com.github.fge.uritemplate.parse;

import com.github.fge.uritemplate.URITemplateParseException;
import com.github.fge.uritemplate.expression.URITemplateExpression;

import java.nio.CharBuffer;

/*
 * Note: in spite of its name, this also parses literals
 */
public interface TemplateParser
{
    /*
     * Rules:
     *
     * * the char buffer will always be positioned at the start of the current
     *   expression;
     * * the parser must swallow all characters until the end of the current
     *   expression.
     */
    URITemplateExpression parse(final CharBuffer buffer)
        throws URITemplateParseException;
}
