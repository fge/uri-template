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
