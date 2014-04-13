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

package com.github.fge.uritemplate.render;

import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.msgsimple.load.MessageBundles;
import com.github.fge.uritemplate.URITemplateException;
import com.github.fge.uritemplate.URITemplateMessageBundle;
import com.github.fge.uritemplate.expression.ExpressionType;
import com.github.fge.uritemplate.vars.specs.VariableSpec;
import com.github.fge.uritemplate.vars.values.VariableValue;
import com.google.common.base.Joiner;

import java.util.List;

/**
 * Base abstract class for list and map rendering
 *
 * <p>The rendering algorithm for both is very similar. Common logic is
 * delegated to this class.</p>
 *
 * <p>The main rendering method essentially delegates to other rendering methods
 * since the actual result is highly dependent on both the varspec modifier and
 * expression type.</p>
 */
public abstract class MultiValueRenderer
    extends ValueRenderer
{
    private static final MessageBundle BUNDLE
        = MessageBundles.getBundle(URITemplateMessageBundle.class);

    protected static final Joiner COMMA = Joiner.on(',');

    protected MultiValueRenderer(final ExpressionType type)
    {
        super(type);
    }

    @Override
    public final List<String> render(final VariableSpec varspec,
        final VariableValue value)
        throws URITemplateException
    {
        // It is illegal to have a prefix modifier on list/map values
        if (varspec.getPrefixLength() != -1)
            throw new URITemplateException(
                BUNDLE.getMessage("expand.incompatVarspecValue"));

        final String varname = varspec.getName();

        if (named)
            return varspec.isExploded() ? renderNamedExploded(varname, value)
                : renderNamedNormal(varname, value);
        else
            return varspec.isExploded() ? renderUnnamedExploded(value)
                : renderUnnamedNormal(value);
    }

    /**
     * Rendering method for named expressions and exploded varspecs
     *
     * @param varname name of the variable (used in lists)
     * @param value value of the variable
     * @return list of rendered elements
     */
    protected abstract List<String> renderNamedExploded(final String varname,
        final VariableValue value);

    /**
     * Rendering method for non named expressions and exploded varspecs
     *
     * @param value value of the variable
     * @return list of rendered elements
     */
    protected abstract List<String> renderUnnamedExploded(
        final VariableValue value);

    /**
     * Rendering method for named expressions and non exploded varspecs
     *
     * @param varname name of the variable (used in lists)
     * @param value value of the variable
     * @return list of rendered elements
     */
    protected abstract List<String> renderNamedNormal(final String varname,
        final VariableValue value);

    /**
     * Rendering method for non named expressions and non exploded varspecs
     *
     * @param value value of the variable
     * @return list of rendered elements
     */
    protected abstract List<String> renderUnnamedNormal(
        final VariableValue value);
}
