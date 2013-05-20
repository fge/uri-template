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

package com.github.fge.uritemplate.expression;

public enum ExpressionType
{
    // No prefix
    SIMPLE("", ',', false, "", false),
    // +
    RESERVED("", ',', false, "", true),
    // .
    NAME_LABELS(".", '.', false, "", false),
    // /
    PATH_SEGMENTS("/", '/', false, "", false),
    // ;
    PATH_PARAMETERS(";", ';', true, "", false),
    // ?
    QUERY_STRING("?", '&', true, "=", false),
    // &
    QUERY_CONT("&", '&', true, "=", false),
    // #
    FRAGMENT("#", ',', false, "", true),
    ;

    /**
     * Prefix string of expansion (requires at least one defined variable)
     */
    final String prefix;
    /**
     * Separator if several varspecs are present
     */
    final char separator;
    /**
     * Whether the variable (string, list) or key (map) name should be included
     * if no explode modifier is found
     */
    final boolean named;
    /**
     * String to append to a name if the value is empty
     */
    final String ifEmpty;
    /**
     * Whether unreserved characters appear as raw on expansion
     */
    final boolean rawExpand;

    ExpressionType(final String prefix, final char separator,
        final boolean named, final String ifEmpty, final boolean rawExpand)
    {
        this.prefix = prefix;
        this.separator = separator;
        this.named = named;
        this.ifEmpty = ifEmpty;
        this.rawExpand = rawExpand;
    }

    public boolean isRawExpand()
    {
        return rawExpand;
    }

    public char getSeparator()
    {
        return separator;
    }
}
