/*
 * Copyright (c) 2013, Francis Galiegue <fgaliegue@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Lesser GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Lesser GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.fge.uritemplate.render;

import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.uritemplate.URITemplateException;
import com.github.fge.uritemplate.URITemplateMessages;
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
    private static final MessageBundle BUNDLE = URITemplateMessages.get();
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
            throw new URITemplateException(BUNDLE.getKey("EXPAND_INCOMPAT"));

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
