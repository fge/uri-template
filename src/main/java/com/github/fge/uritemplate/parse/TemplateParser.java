package com.github.fge.uritemplate.parse;

import com.github.fge.uritemplate.URITemplateException;
import com.github.fge.uritemplate.expression.URITemplateExpression;

import java.nio.CharBuffer;

interface TemplateParser
{
    URITemplateExpression parse(final CharBuffer buffer)
        throws URITemplateException;
}
