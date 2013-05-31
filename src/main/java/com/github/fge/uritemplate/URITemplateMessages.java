package com.github.fge.uritemplate;

import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.msgsimple.source.MessageSource;
import com.github.fge.msgsimple.source.PropertiesMessageSource;

import java.io.IOException;

public final class URITemplateMessages
{
    private static final MessageBundle BUNDLE;

    static {
        final String resourcePath
            = "/com/github/fge/uritemplate/messages.properties";
        final MessageSource source;

        try {
            source = PropertiesMessageSource.fromResource(resourcePath);
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }

        BUNDLE = new MessageBundle.Builder().appendSource(source).build();
    }

    private URITemplateMessages()
    {
    }

    public static MessageBundle get()
    {
        return BUNDLE;
    }
}
