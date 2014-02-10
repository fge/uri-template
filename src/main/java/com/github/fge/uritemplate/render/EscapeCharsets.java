package com.github.fge.uritemplate.render;

import com.google.common.net.PercentEscaper;

/**
 * Character sets to escape during URI template expansions
 *
 * <p>This makes uses of Guava's {@link PercentEscaper} and relies on the
 * grammar defined in RFC 3986.</p>
 */
final class EscapeCharsets
{
    private EscapeCharsets()
    {
    }

    /*
     * Note that the grammar also includes all of alphanumeric ASCII chars;
     * however, PercentEscaper automatically considers such chars as safe: if
     * you try and include them, you'll get an IllegalArgumentException.
     */
    static final String UNRESERVED = "-._~";
    static final String RESERVED_PLUS_UNRESERVED = UNRESERVED
        + ":/?#[]@" // gen-delims
        + "!$&'()*+,;="; // sub-delims
}
