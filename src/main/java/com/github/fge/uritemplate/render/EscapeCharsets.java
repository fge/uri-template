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
