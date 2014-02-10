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

package com.github.fge.uritemplate.render;

import com.github.fge.uritemplate.URITemplateException;
import com.github.fge.uritemplate.expression.ExpressionType;
import com.github.fge.uritemplate.vars.specs.VariableSpec;
import com.github.fge.uritemplate.vars.values.VariableValue;
import com.google.common.escape.Escaper;
import com.google.common.net.PercentEscaper;

import java.util.List;

/**
 * Main rendering class
 *
 * <p>The algorithm used for rendering is centered around this class,and is
 * adapted from the algorithm suggested in the RFC's appendix.</p>
 *
 * <p>Eventually, rendering can be viewed as joining a list of rendered strings
 * with the expression type separator; if the resulting list is empty, the end
 * result is the empty string; otherwise, it is the expression's prefix string
 * (if any) followed by the joined list of rendered strings.</p>
 *
 * <p>This class renders one variable value according to the expression type and
 * value type. The rendering method returns a list, which can be empty.</p>
 *
 * @see ExpressionType
 */
public abstract class ValueRenderer
{
    /**
     * Whether variable values are named during expansion
     *
     * @see ExpressionType#isNamed()
     */
    protected final boolean named;

    /**
     * Substitution string for an empty value/list member/map value
     *
     * @see ExpressionType#getIfEmpty()
     */
    protected final String ifEmpty;

    /**
     * Our character escaper
     *
     * @see EscapeCharsets
     */
    private final Escaper escaper;

    /**
     * Constructor
     *
     * @param type the expression type
     */
    protected ValueRenderer(final ExpressionType type)
    {
        named = type.isNamed();
        ifEmpty = type.getIfEmpty();
        final String escaped = type.isRawExpand()
            ? EscapeCharsets.RESERVED_PLUS_UNRESERVED
            : EscapeCharsets.UNRESERVED;
        escaper = new PercentEscaper(escaped, false);
    }

    /**
     * Render a value given a varspec and value
     *
     * @param varspec the varspec
     * @param value the matching variable value
     * @return a list of rendered strings
     * @throws URITemplateException illegal expansion
     */
    public abstract List<String> render(final VariableSpec varspec,
        VariableValue value)
        throws URITemplateException;

    /**
     * Render a string value, doing character percent-encoding where needed
     *
     * <p>The character set on which to perform percent encoding is dependent
     * on the expression type.</p>
     *
     * @param s the string to encode
     * @return an encoded string
     * @see ExpressionType#isRawExpand()
     */
    protected final String pctEncode(final String s)
    {
        return escaper.escape(s);
    }
}

