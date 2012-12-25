package org.eel.kitchen.uritemplate;

import com.google.common.base.CharMatcher;

import java.util.regex.Pattern;

public final class URITemplate
{
    private static final CharMatcher BRACKETS = CharMatcher.anyOf("{}");
    private static final Pattern PATTERN = Pattern.compile("\\{[^{}]*\\}");
    private final String tmpl;

    public URITemplate(final String tmpl)
        throws InvalidTemplateException
    {
        if (tmpl == null)
            throw new NullPointerException("null template not accepted");

        final String withoutTemplates = PATTERN.matcher(tmpl).replaceAll("");
        if (BRACKETS.matchesAnyOf(withoutTemplates))
            throw new InvalidTemplateException("unpaired brackets");
        this.tmpl = tmpl;
    }

    @Override
    public String toString()
    {
        return tmpl;
    }
}
