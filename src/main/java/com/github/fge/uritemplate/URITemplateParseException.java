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
