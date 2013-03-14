package com.github.fge.uritemplate;

import java.nio.CharBuffer;

public final class URITemplateParseException
    extends URITemplateException
{
    private final int offset;

    public URITemplateParseException(final String message,
        final CharBuffer buffer)
    {
        super(message);
        offset = buffer.position();
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
