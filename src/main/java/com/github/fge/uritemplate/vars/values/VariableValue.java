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

package com.github.fge.uritemplate.vars.values;

import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.msgsimple.load.MessageBundles;
import com.github.fge.uritemplate.URITemplateMessageBundle;

import java.util.List;
import java.util.Map;

/**
 * Abstract class for one variable value
 */
public abstract class VariableValue
{
    protected static final MessageBundle BUNDLE
        = MessageBundles.getBundle(URITemplateMessageBundle.class);

    private final ValueType type;

    protected VariableValue(final ValueType type)
    {
        this.type = type;
    }

    /**
     * Get the type for this value
     *
     * @return the value type
     */
    public final ValueType getType()
    {
        return type;
    }

    /**
     * Get a simple string for this value
     *
     * <p>Only valid for string values</p>
     *
     * @return the string
     * @throws IllegalArgumentException value is not a string value
     */
    public String getScalarValue()
    {
        throw new IllegalArgumentException(BUNDLE.printf("value.notScalar",
            type));
    }

    /**
     * Get a list for this value
     *
     * <p>Only valid for list values</p>
     *
     * @return the list
     * @throws IllegalArgumentException value is not a list value
     */
    public List<String> getListValue()
    {
        throw new IllegalArgumentException(BUNDLE.printf("value.notList",
            type));
    }

    /**
     * Get a map for this value
     *
     * <p>Only valid for map values</p>
     *
     * @return the map
     * @throws IllegalArgumentException value is not a map value
     */
    public Map<String, String> getMapValue()
    {
        throw new IllegalArgumentException(BUNDLE.printf("value.notMap",
            type));
    }

    /**
     * Tell whether this value is empty
     *
     * <p>For strings, this tells whether the string itself is empty. For lists
     * and maps, this tells whether the list or map have no elements/entries.
     * </p>
     *
     * @return true if the value is empty
     */
    public abstract boolean isEmpty();
}
