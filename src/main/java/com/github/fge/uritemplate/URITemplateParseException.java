package com.github.fge.uritemplate;

import java.nio.CharBuffer;

public final class URITemplateParseException
    extends URITemplateException
{
    private final String originalMessage;
    private final int offset;

    public URITemplateParseException(final String message,
        final CharBuffer buffer, final boolean previousChar)
    {
        super(message);
        originalMessage = message;
        offset = previousChar ? buffer.position() - 1 : buffer.position();
    }

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
