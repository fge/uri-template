package com.github.fge.uritemplate.vars.values;

import java.util.List;
import java.util.Map;

/**
 * Value for one variable used for the expansion process
 */
public abstract class VariableValue
{
    protected final ValueType type;

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
     * @throws IllegalStateException value is not a string value
     */
    public String getScalarValue()
    {
        throw new IllegalStateException();
    }

    /**
     * Get a list for this value
     *
     * <p>Only valid for list values</p>
     *
     * @return the list
     * @throws IllegalStateException value is not a list value
     */
    public List<String> getListValue()
    {
        throw new IllegalStateException();
    }

    /**
     * Get a map for this value
     *
     * <p>Only valid for map values</p>
     *
     * @return the map
     * @throws IllegalStateException value is not a map value
     */
    public Map<String, String> getMapValue()
    {
        throw new IllegalStateException();
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
