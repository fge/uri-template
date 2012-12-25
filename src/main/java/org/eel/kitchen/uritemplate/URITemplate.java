package org.eel.kitchen.uritemplate;

import com.google.common.base.CharMatcher;

import java.net.URI;
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

        /*
         * FIXME: unsure. But from what I understand, any template, even with
         * empty substitutions, cannot yield an invalid URI. At least, so far,
         * I cannot figure out a situation where you would write a legal
         * template yielding an illegal URI with empty substitutions only.
         */
        try {
            URI.create(withoutTemplates);
        } catch (IllegalArgumentException e) {
            throw new InvalidTemplateException("calculated URI would be " +
                "invalid", e);
        }

        this.tmpl = tmpl;
    }

    @Override
    public String toString()
    {
        return tmpl;
    }
}
