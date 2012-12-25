package org.eel.kitchen.uritemplate;

public final class URITemplate
{
    private final String tmpl;

    public URITemplate(final String tmpl)
    {
        if (tmpl == null)
            throw new NullPointerException("null template not accepted");
        this.tmpl = tmpl;
    }
}
