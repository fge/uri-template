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

package com.github.fge.uritemplate.vars.values;

import javax.annotation.concurrent.Immutable;

/**
 * Simple string variable value
 *
 * <p>Note that the constructor takes an arbitrary type as an argument. It is
 * the caller's responsibility to ensure that this value has a correct {@link
 * Object#toString() .toString()} implementation.</p>
 */
@Immutable
public final class ScalarValue
    extends VariableValue
{
    private final String value;

    /**
     * Constructor
     *
     * @param value the value
     * @throws NullPointerException value is null
     */
    public ScalarValue(final Object value)
    {
        super(ValueType.SCALAR);
        BUNDLE.checkNotNull(value, "scalar.nullValue");
        this.value = value.toString();
    }

    @Override
    public String getScalarValue()
    {
        return value;
    }

    @Override
    public boolean isEmpty()
    {
        return value.isEmpty();
    }
}
