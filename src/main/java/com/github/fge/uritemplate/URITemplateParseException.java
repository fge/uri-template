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
