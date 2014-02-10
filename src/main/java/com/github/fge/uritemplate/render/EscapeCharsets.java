package com.github.fge.uritemplate.render;

final class EscapeCharsets
{
    private EscapeCharsets()
    {
    }

    static final String UNRESERVED = "-._~";
    static final String RESERVED_PLUS_UNRESERVED = UNRESERVED
        + ":/?#[]@" // gen-delims
        + "!$&'()*+,;="; // sub-delims
}
