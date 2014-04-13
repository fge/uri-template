/*
 * Copyright (c) 2014, Francis Galiegue (fgaliegue@gmail.com)
 *
 * This software is dual-licensed under:
 *
 * - the Lesser General Public License (LGPL) version 3.0 or, at your option, any
 *   later version;
 * - the Apache Software License (ASL) version 2.0.
 *
 * The text of this file and of both licenses is available at the root of this
 * project or, if you have the jar distribution, in directory META-INF/, under
 * the names LGPL-3.0.txt and ASL-2.0.txt respectively.
 *
 * Direct link to the sources:
 *
 * - LGPL 3.0: https://www.gnu.org/licenses/lgpl-3.0.txt
 * - ASL 2.0: http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package com.github.fge.uritemplate.parse;

import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.msgsimple.load.MessageBundles;
import com.github.fge.uritemplate.URITemplateMessageBundle;
import com.github.fge.uritemplate.URITemplateParseException;
import com.github.fge.uritemplate.expression.ExpressionType;
import com.github.fge.uritemplate.expression.TemplateExpression;
import com.github.fge.uritemplate.expression.URITemplateExpression;
import com.github.fge.uritemplate.vars.specs.ExplodedVariable;
import com.github.fge.uritemplate.vars.specs.PrefixVariable;
import com.github.fge.uritemplate.vars.specs.SimpleVariable;
import com.github.fge.uritemplate.vars.specs.VariableSpec;
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
    private static final MessageBundle BUNDLE
        = MessageBundles.getBundle(URITemplateMessageBundle.class);

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
        message = BUNDLE.getMessage("parse.unexpectedEOF");
        offset = 3;
        list.add(new Object[]{input, message, offset});

        input = "{foo#bar}";
        message = BUNDLE.getMessage("parse.unexpectedToken");
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
