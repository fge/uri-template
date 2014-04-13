/*
 * Copyright (c) 2014, Francis Galiegue (fgaliegue@gmail.com)
 *
 * This software is dual-licensed under:
 *
 * - the Lesser General Public License (LGPL) version 3.0 or, at your option, any
 *   later version;
 * - the Apache Software License (ASL) version 2.0.
 *
 * The text of this file and of both licenses is available at the root of this
 * project or, if you have the jar distribution, in directory META-INF/, under
 * the names LGPL-3.0.txt and ASL-2.0.txt respectively.
 *
 * Direct link to the sources:
 *
 * - LGPL 3.0: https://www.gnu.org/licenses/lgpl-3.0.txt
 * - ASL 2.0: http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package com.github.fge.uritemplate.expression;

import com.github.fge.uritemplate.URITemplateException;
import com.github.fge.uritemplate.vars.VariableMap;

/**
 * Literals rendering class
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
    public String expand(final VariableMap vars)
        throws URITemplateException
    {
        return literal;
    }
}
