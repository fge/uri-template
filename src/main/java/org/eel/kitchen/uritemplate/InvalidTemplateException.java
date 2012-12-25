package org.eel.kitchen.uritemplate;

public final class InvalidTemplateException
    extends Exception
{
    public InvalidTemplateException(String message)
    {
        super(message);
    }

    public InvalidTemplateException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public InvalidTemplateException(Throwable cause)
    {
        super(cause);
    }
}
