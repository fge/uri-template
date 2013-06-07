/*
 * Copyright (c) 2013, Francis Galiegue <fgaliegue@gmail.com>
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

package com.github.fge.uritemplate.parse;

import com.github.fge.uritemplate.URITemplateException;
import com.github.fge.uritemplate.URITemplateParseException;
import com.github.fge.uritemplate.expression.URITemplateExpression;
import com.github.fge.uritemplate.vars.values.VariableValue;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.nio.CharBuffer;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.*;

public final class LiteralParsingTest
{
    private static final Map<String, VariableValue> VARS
        = ImmutableMap.of();

    @DataProvider
    public Iterator<Object[]> validInputs()
    {
        final List<Object[]> list = Lists.newArrayList();

        String input;

        input = "foo";
        list.add(new Object[]{input});

        input = "%23foo";
        list.add(new Object[]{input});

        input = "%23foo%24";
        list.add(new Object[]{input});

        input = "foo%24";
        list.add(new Object[]{input});

        input = "f%c4oo";
        list.add(new Object[]{input});

        input = "http://slashdot.org";
        list.add(new Object[]{input});

        input = "x?y=e";
        list.add(new Object[]{input});

        input = "urn:d:ze:/oize#/e/e";
        list.add(new Object[]{input});

        input = "ftp://ftp.foo.com/ee/z?a=b#e/dz";
        list.add(new Object[]{input});

        input = "http://z.t/hello%20world";
        list.add(new Object[]{input});

        return list.iterator();
    }

    @Test(dataProvider = "validInputs")
    public void validInputsAreParsedCorrectly(final String input)
        throws URITemplateException
    {
        final CharBuffer buffer = CharBuffer.wrap(input).asReadOnlyBuffer();
        final List<URITemplateExpression> list
            = URITemplateParser.parse(buffer);

        assertEquals(list.get(0).expand(VARS), input);
        assertFalse(buffer.hasRemaining());
    }

    @Test
    public void parsingEmptyInputGivesEmptyList()
        throws URITemplateParseException
    {
        final CharBuffer buffer = CharBuffer.wrap("").asReadOnlyBuffer();
        final List<URITemplateExpression> list
            = URITemplateParser.parse(buffer);
        assertTrue(list.isEmpty());
        assertFalse(buffer.hasRemaining());
    }
}
