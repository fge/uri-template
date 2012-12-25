package org.eel.kitchen.uritemplate;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public final class URITemplateTest
{
    @Test
    public void nullTemplateStringIsNotAccepted()
        throws InvalidTemplateException
    {
        try {
            new URITemplate(null);
            fail("No exception thrown!");
        } catch (NullPointerException e) {
            assertEquals(e.getMessage(), "null template not accepted");
        }
    }

    @Test
    public void nonPairedBracketsAreNotAccepted()
    {
        try {
            new URITemplate("{");
            fail("No exception thrown!");
        } catch (InvalidTemplateException e) {
            assertEquals(e.getMessage(), "unpaired brackets");
        }
    }

    @Test
    public void toStringReturnsTemplateItself()
        throws InvalidTemplateException
    {
        final String tmpl = "http://foo.bar/{baz}";
        final URITemplate template = new URITemplate(tmpl);
        assertEquals(template.toString(), tmpl);
    }
}
