/*
 * Copyright (c) 2014, Francis Galiegue (fgaliegue@gmail.com)
 *
 * This software is dual-licensed under:
 *
 * - the Lesser General Public License (LGPL) version 3.0 or, at your option, any
 *   later version;
 * - the Apache Software License (ASL) version 2.0.
 *
 * The text of both licenses is available under the src/resources/ directory of
 * this project (under the names LGPL-3.0.txt and ASL-2.0.txt respectively).
 *
 * Direct link to the sources:
 *
 * - LGPL 3.0: https://www.gnu.org/licenses/lgpl-3.0.txt
 * - ASL 2.0: http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package com.github.fge.uritemplate;

import com.github.fge.uritemplate.expression.URITemplateExpression;
import com.github.fge.uritemplate.parse.URITemplateParser;
import com.github.fge.uritemplate.vars.VariableMap;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

/**
 * Main URI template class
 *
 * @see URITemplateParser
 */
public final class URITemplate
{
    /**
     * Ordered list of parsed URI template expressions
     */
    private final List<URITemplateExpression> expressions;

    /**
     * Constructor
     *
     * @param input the input string
     * @throws URITemplateParseException parse error
     */
    public URITemplate(final String input)
        throws URITemplateParseException
    {
        expressions = URITemplateParser.parse(input);
    }

    /**
     * Expand this template to a string given a list of variables
     *
     * @param vars the variable map (names as keys, contents as values)
     * @return expanded string
     * @throws URITemplateException expansion error (f.e. modifier mismatch)
     */
    public String toString(final VariableMap vars)
        throws URITemplateException
    {
        final StringBuilder sb = new StringBuilder();

        for (final URITemplateExpression expression: expressions)
            sb.append(expression.expand(vars));

        return sb.toString();
    }

    /**
     * Expand this template to a URI given a set of variables
     *
     * @param vars the variables
     * @return a URI
     * @throws URITemplateException see {@link #toString(VariableMap)}
     * @throws URISyntaxException expanded string is not a valid URI
     */
    public URI toURI(final VariableMap vars)
        throws URITemplateException, URISyntaxException
    {
        return new URI(toString(vars));
    }

    /**
     * Expand this template to a URL given a set of variables
     *
     * @param vars the variables
     * @return a URL
     * @throws URITemplateException see {@link #toString(VariableMap)}
     * @throws MalformedURLException expanded string is not a valid URL
     */
    public URL toURL(final VariableMap vars)
        throws URITemplateException, MalformedURLException
    {
        return new URL(toString(vars));
    }
}
