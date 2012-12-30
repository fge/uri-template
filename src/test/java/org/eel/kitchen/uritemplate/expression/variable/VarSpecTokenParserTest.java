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

package org.eel.kitchen.uritemplate.expression.variable;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import org.eel.kitchen.uritemplate.InvalidTemplateException;
import org.eel.kitchen.uritemplate.expression.TokenParser;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.nio.CharBuffer;
import java.util.Iterator;
import java.util.Set;

import static org.testng.Assert.*;

public final class VarSpecTokenParserTest
{
    private VariableBuilder builder;
    private StringBuilder sb;

    @BeforeMethod
    public void initParser()
    {
        builder = new VariableBuilder();
        sb = new StringBuilder();
    }

    @DataProvider
    public Iterator<Object[]> legalVariableSpecs()
    {
        final Set<String> names = ImmutableSet.of(
            "foo", // Only alpha
            "foo_bar", // Alpha and underscore
            "_", // Underscore only
            "0", // Digit only
            "0_a", // All three
            "a%25a", // Percent sequence in the middle
            "a%25", // Percent sequence at the end
            "%25a", // Percent sequence at the beginning
            "a%ff%a00" // Two subsequent percent sequences
        );

        final Set<Object[]> set = Sets.newHashSet();

        for (final String name: names)
            set.add(new Object[]{ name });

        return set.iterator();
    }

    @Test(dataProvider = "legalVariableSpecs")
    public void legalVariableSpecIsParsedCorrectly(final String name)
        throws InvalidTemplateException
    {
        final CharBuffer buf = CharBuffer.wrap(name.toCharArray());
        TokenParser parser = new VarSpecTokenParser(buf, 0, builder, sb);

        while (parser.parse())
            parser = parser.next();

        assertTrue(true);
    }

    // As the RFC requires at least one character in a variable name, this means
    // empty inputs are not accepted
    @Test
    public void emptySingleVariableReferenceIsIllegal()
    {
        final CharBuffer buf = CharBuffer.wrap("".toCharArray());
        TokenParser parser = new VarSpecTokenParser(buf, 0, builder, sb);

        try {
            while (parser.parse())
                parser = parser.next();
            fail("No exception thrown!");
        } catch (InvalidTemplateException e) {
            assertEquals(e.getMessage(), "variable names cannot be empty");
        }
    }

    @DataProvider
    public Iterator<Object[]> illegalPercentEscapes()
    {
        final Set<String> names = ImmutableSet.of(
            "%", // Single % without any chars behind
            "a%", // Same but with preceeding legal char
            "%x", // One token which is not a legal char
            "%fx", // One legal token and one illegal one
            "%a" // One legal token but no second one
        );

        final Set<Object[]> set = Sets.newHashSet();

        for (final String name: names)
            set.add(new Object[]{ name });

        return set.iterator();
    }

    @Test(dataProvider = "illegalPercentEscapes")
    public void illegalPercentEscapesAreDetected(final String name)
    {
        final CharBuffer buf = CharBuffer.wrap(name.toCharArray());
        TokenParser parser = new VarSpecTokenParser(buf, 0, builder, sb);

        try {
            while (parser.parse())
                parser = parser.next();
            fail("No exception thrown!");
        } catch (InvalidTemplateException e) {
            // FIXME: hardcoded
            final String message = e.getMessage();
            assertTrue(message.startsWith("illegal percent-escaped sequence: "),
                "unexpected error message");
        }
    }
}
