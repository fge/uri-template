/*
 * Copyright (c) 2012, Francis Galiegue <fgaliegue@gmail.com>
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

package org.eel.kitchen.uritemplate.expression;

import com.google.common.base.CharMatcher;

// FIXME: HACKISH
public enum TemplateOperator
{
    // NONE is a _hack_, and not a pretty one (see constructor). It MUST
    // be last :(
    NONE(null);

    private final CharMatcher matcher;

    TemplateOperator(final Character c)
    {
        matcher = c == null ? CharMatcher.ANY : CharMatcher.is(c);
    }

    public static TemplateOperator detectFrom(final String input)
    {
        // A hack... Because of NONE
        if (input.isEmpty())
            return NONE;

        final Character first = input.charAt(0);

        // Guaranteed never to fail, but again, it is a hack
        for (final TemplateOperator op: values())
            if (op.matcher.matches(first))
                return op;

        throw new IllegalStateException("should not happen!");
    }

    // Again, a hack
    public String varSpecs(final String input)
    {
        return this == NONE ? input : input.substring(1);
    }
}
