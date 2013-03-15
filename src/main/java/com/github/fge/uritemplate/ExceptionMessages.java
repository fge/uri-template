package com.github.fge.uritemplate;

public final class ExceptionMessages
{
    public static final String NO_PARSER = "no suitable parser found";
    public static final String PERCENT_SHORT_READ
        = "not enough remaining characters for a percent-encoded sequence";
    public static final String ILLEGAL_PERCENT
        = "illegal character in percent-encoded sequence";
    public static final String EMPTY_NAME = "empty variable name";

    private ExceptionMessages()
    {
    }
}
