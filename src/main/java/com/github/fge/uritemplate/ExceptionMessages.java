package com.github.fge.uritemplate;

public final class ExceptionMessages
{
    public static final String NO_PARSER = "no suitable parser found";
    public static final String PERCENT_SHORT_READ
        = "not enough remaining characters for a percent-encoded sequence";
    public static final String ILLEGAL_PERCENT
        = "illegal character in percent-encoded sequence";
    public static final String EMPTY_NAME = "empty variable name";
    public static final String EMPTY_PREFIX = "prefix is empty";
    public static final String PREFIX_TOO_LARGE
        = "prefix length is too large (maximum 10000)";
    public static final String UNEXPECTED_EOF
        = "unexpected end of input: expected a comma or a closing bracket";
    public static final String UNEXPECTED_TOKEN
        = "unexpected token: expected a comma or a closing bracket";
    public static final String EXPAND_INCOMPAT
        = "incompatible varspec/value combination";
    private ExceptionMessages()
    {
    }
}
