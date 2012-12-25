/*
 * Copyright (c) 2012, Francis Galiegue <fgaliegue@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Lesser GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Lesser GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
    public void illegalURIWithEmptySubstitutionsIsConsideredAnError()
    {
        try {
            new URITemplate("^{hello}");
            fail("No exception thrown!");
        } catch (InvalidTemplateException e) {
            assertEquals(e.getMessage(), "calculated URI would be invalid");
            assertSame(e.getCause().getClass(), IllegalArgumentException.class);
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
