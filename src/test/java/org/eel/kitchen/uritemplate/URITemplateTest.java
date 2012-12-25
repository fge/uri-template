package org.eel.kitchen.uritemplate;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public final class URITemplateTest
{
    @Test
    public void nullTemplateStringIsNotAccepted()
    {
        try {
            new URITemplate(null);
            fail("No exception thrown!");
        } catch (NullPointerException e) {
            assertEquals(e.getMessage(), "null template not accepted");
        }
    }
}
