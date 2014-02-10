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

package com.github.fge.uritemplate;

import java.nio.CharBuffer;

/**
 * Exception class on URI template parsing error
 *
 * <p>The exception message will always contain the offset at which the error
 * was encountered.</p>
 */
public final class URITemplateParseException
    extends URITemplateException
{
    private final String originalMessage;
    private final int offset;

    /**
     * Constructor
     *
     * @param message the message to display
     * @param buffer the buffer in which the error occurred
     * @param previousChar whether the error relates to the current buffer
     * position or the character immediately preceding it
     */
    public URITemplateParseException(final String message,
        final CharBuffer buffer, final boolean previousChar)
    {
        super(message);
        originalMessage = message;
        offset = previousChar ? buffer.position() - 1 : buffer.position();
    }

    /**
     * Alternate constructor
     *
     * <p>This is equivalent to calling {@link
     * #URITemplateParseException(String, CharBuffer, boolean)} with the third
     * argument being false.</p>
     *
     * @param message the message to display
     * @param buffer the buffer in which the error occurred
     */
    public URITemplateParseException(final String message,
        final CharBuffer buffer)
    {
        this(message, buffer, false);
    }

    public String getOriginalMessage()
    {
        return originalMessage;
    }

    public int getOffset()
    {
        return offset;
    }

    @Override
    public String getMessage()
    {
        return super.getMessage() + " (at offset " + offset + ')';
    }
}
