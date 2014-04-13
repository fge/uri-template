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
