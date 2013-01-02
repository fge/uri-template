package org.eel.kitchen.uritemplate.expression;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public enum Operator
{
    RESERVED('+'),
    FRAGMENT('#'),
    LABELS('.'),
    PATH('/'),
    // FIXME: unsure about the name for the following
    PARAM(';'),
    QUERY('?'),
    QUERY_CONT('&');

    private static final Map<Character, Operator> REVERSE_MAP;
    private static final char[] VALID_CHARS;

    static {
        final ImmutableMap.Builder<Character, Operator> builder
            = ImmutableMap.builder();
        final Operator[] values = values();
        final int length = values.length;

        Operator op;

        VALID_CHARS = new char[length];

        for (int index = 0; index < length; index++) {
            op = values[index];
            VALID_CHARS[index] = op.opchar;
            builder.put(op.opchar, op);
        }

        REVERSE_MAP = builder.build();
    }

    private final char opchar;

    Operator(final char opchar)
    {
        this.opchar = opchar;
    }

    public static Operator fromChar(final char c)
    {
        return REVERSE_MAP.get(c);
    }

    public static char[] validChars()
    {
        // Arrays are not immutable, we must return a copy each time
        return VALID_CHARS.clone();
    }
}
