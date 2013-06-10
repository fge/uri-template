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

/**
 * Enumeration of all expression types
 *
 * <p>In order to understand the different setters, please refer to <a
 * href="http://tools.ietf.org/html/rfc6570#appendix-A">appendix A of RFC
 * 6570</a>.</p>
 */
public enum ExpressionType
{
    /*
     * Simple character expansion
     */
    SIMPLE("", ',', false, "", false),
    /*
     * Reserved character expansion
     */
    RESERVED("", ',', false, "", true),
    /*
     * Name labels expansion
     */
    NAME_LABELS(".", '.', false, "", false),
    /*
     * Path segments expansion
     */
    PATH_SEGMENTS("/", '/', false, "", false),
    /*
     * Path parameters expansion
     */
    PATH_PARAMETERS(";", ';', true, "", false),
    /*
     * Query string expansion
     */
    QUERY_STRING("?", '&', true, "=", false),
    /*
     * Query string continuation expansion
     */
    QUERY_CONT("&", '&', true, "=", false),
    /*
     * Fragment expansion
     */
    FRAGMENT("#", ',', false, "", true),
    ;

    /**
     * Prefix string of expansion (requires at least one expanded token)
     */
    private final String prefix;

    /**
     * Separator if several tokens are present
     */
    private final char separator;

    /**
     * Whether the variable (string, list) or key (map) name should be included
     * if no explode modifier is found
     */
    private final boolean named;

    /**
     * String to append to a name if the matching value is empty (empty string,
     * empty list element, empty map value)
     */
    private final String ifEmpty;

    /**
     * Whether unreserved characters appear as raw on expansion
     */
    private final boolean rawExpand;

    ExpressionType(final String prefix, final char separator,
        final boolean named, final String ifEmpty, final boolean rawExpand)
    {
        this.prefix = prefix;
        this.separator = separator;
        this.named = named;
        this.ifEmpty = ifEmpty;
        this.rawExpand = rawExpand;
    }

    /**
     * Get the prefix string for this expansion type
     *
     * @return the prefix string
     */
    public String getPrefix()
    {
        return prefix;
    }

    /**
     * Tell whether the variable name should be used in expansion
     *
     * @return true if this is the case
     */
    public boolean isNamed()
    {
        return named;
    }

    /**
     * Get the substitution string for empty values
     *
     * @return the substitution string
     */
    public String getIfEmpty()
    {
        return ifEmpty;
    }

    /**
     * Tell whether the character set to be used for expansion includes the
     * reserved characters
     *
     * @return true if this is the case
     */
    public boolean isRawExpand()
    {
        return rawExpand;
    }

    /**
     * Get the separator between token expansion elements
     *
     * @return the separator
     */
    public char getSeparator()
    {
        return separator;
    }
}
