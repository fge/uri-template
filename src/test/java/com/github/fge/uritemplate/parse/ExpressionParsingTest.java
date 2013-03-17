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

import com.github.fge.uritemplate.ExceptionMessages;
import com.github.fge.uritemplate.URITemplateParseException;
import com.github.fge.uritemplate.expression.ExpressionType;
import com.github.fge.uritemplate.expression.TemplateExpression;
import com.github.fge.uritemplate.expression.URITemplateExpression;
import com.github.fge.uritemplate.vars.ExplodedVariable;
import com.github.fge.uritemplate.vars.PrefixVariable;
import com.github.fge.uritemplate.vars.SimpleVariable;
import com.github.fge.uritemplate.vars.VariableSpec;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.nio.CharBuffer;
import java.util.Iterator;
import java.util.List;

import static org.testng.Assert.*;

public final class ExpressionParsingTest
{
    @DataProvider
    public Iterator<Object[]> validInputs()
    {
        final List<Object[]> list = Lists.newArrayList();

        String input;
        ExpressionType type;
        List<VariableSpec> varspecs;

        input = "{foo}";
        type = ExpressionType.SIMPLE;
        varspecs = ImmutableList.<VariableSpec>of(new SimpleVariable("foo"));
        list.add(new Object[]{input, type, varspecs});

        input = "{foo,bar}";
        type = ExpressionType.SIMPLE;
        varspecs = ImmutableList.<VariableSpec>of(new SimpleVariable("foo"),
            new SimpleVariable("bar"));
        list.add(new Object[]{input, type, varspecs});

        input = "{+foo}";
        type = ExpressionType.RESERVED;
        varspecs = ImmutableList.<VariableSpec>of(new SimpleVariable("foo"));
        list.add(new Object[]{input, type, varspecs});

        input = "{.foo:10,bar*}";
        type = ExpressionType.NAME_LABELS;
        varspecs = ImmutableList.of(new PrefixVariable("foo", 10),
            new ExplodedVariable("bar"));
        list.add(new Object[]{input, type, varspecs});

        return list.iterator();
    }

    @Test(dataProvider = "validInputs")
    public void expressionsAreCorrectlyParsed(final String input,
        final ExpressionType type, final List<VariableSpec> varspecs)
        throws URITemplateParseException
    {
        final CharBuffer buffer = CharBuffer.wrap(input).asReadOnlyBuffer();
        final URITemplateExpression actual
            = new ExpressionParser().parse(buffer);

        assertFalse(buffer.hasRemaining());

        final URITemplateExpression expected
            = new TemplateExpression(type, varspecs);

        assertEquals(actual, expected);
    }

    @DataProvider
    public Iterator<Object[]> invalidInputs()
    {
        final List<Object[]> list = Lists.newArrayList();

        String input;
        String message;
        int offset;

        input = "{foo";
        message = ExceptionMessages.UNEXPECTED_EOF;
        offset = 3;
        list.add(new Object[]{input, message, offset});

        input = "{foo#bar}";
        message = ExceptionMessages.UNEXPECTED_TOKEN;
        offset = 4;
        list.add(new Object[]{input, message, offset});

        return list.iterator();
    }

    @Test(dataProvider = "invalidInputs")
    public void invalidInputsRaiseAppropriateExceptions(final String input,
        final String message, final int offset)
    {
        final CharBuffer buffer = CharBuffer.wrap(input).asReadOnlyBuffer();
        try {
            new ExpressionParser().parse(buffer);
            fail("No exception thrown!!");
        } catch (URITemplateParseException e) {
            assertEquals(e.getOriginalMessage(), message);
            assertEquals(e.getOffset(), offset);
        }
    }
}
