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
import com.github.fge.uritemplate.vars.specs.VariableSpec;
import com.github.fge.uritemplate.vars.specs.VariableSpecType;
import com.google.common.collect.Lists;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.nio.CharBuffer;
import java.util.Iterator;
import java.util.List;

import static org.testng.Assert.*;

public final class SimpleVariableParsingTest
{
    private static final MessageBundle BUNDLE
        = MessageBundles.getBundle(URITemplateMessageBundle.class);
    
    @DataProvider
    public Iterator<Object[]> validInputs()
    {
        final List<Object[]> list = Lists.newArrayList();

        String input;

        input = "foo";
        list.add(new Object[]{ input });

        input = "%33foo";
        list.add(new Object[]{ input });

        input = "foo%20";
        list.add(new Object[]{ input });

        input = "foo_%20bar";
        list.add(new Object[]{ input });

        input = "FoOb%02ZAZE287";
        list.add(new Object[]{ input });

        input = "foo.bar";
        list.add(new Object[]{ input });

        input = "foo_%20bar.baz%af.r";
        list.add(new Object[]{ input });

        return list.iterator();
    }

    @Test(dataProvider = "validInputs")
    public void simpleVariablesAreParsedCorrectly(final String input)
        throws URITemplateParseException
    {
        final CharBuffer buffer = CharBuffer.wrap(input).asReadOnlyBuffer();
        final VariableSpec varspec = VariableSpecParser.parse(buffer);

        assertEquals(varspec.getName(), input);
        assertSame(varspec.getType(), VariableSpecType.SIMPLE,
            "unexpected class for parsed variable");
        assertFalse(buffer.hasRemaining());
    }

    @DataProvider
    public Iterator<Object[]> invalidInputs()
    {
        final List<Object[]> list = Lists.newArrayList();

        String input;
        String message;
        int offset;

        input = "";
        message = BUNDLE.getMessage("parse.emptyVarname");
        offset = 0;
        list.add(new Object[]{input, message, offset});

        input = "%";
        message = BUNDLE.getMessage("paser.percentShortRead");
        offset = 0;
        list.add(new Object[]{input, message, offset});

        input = "foo..bar";
        message = BUNDLE.getMessage("parse.emptyVarname");
        offset = 4;
        list.add(new Object[]{input, message, offset});

        input = ".";
        message = BUNDLE.getMessage("parse.emptyVarname");
        offset = 0;
        list.add(new Object[]{input, message, offset});

        input = "foo%ra";
        message = BUNDLE.getMessage("parse.percentIllegal");
        offset = 4;
        list.add(new Object[]{input, message, offset});

        input = "foo%ar";
        message = BUNDLE.getMessage("parse.percentIllegal");
        offset = 5;
        list.add(new Object[]{input, message, offset});

        return list.iterator();
    }

    @Test(dataProvider = "invalidInputs")
    public void invalidInputsThrowAppropriateExceptions(final String input,
        final String message, final int offset)
    {
        try {
            VariableSpecParser.parse(CharBuffer.wrap(input).asReadOnlyBuffer());
            fail("No exception thrown!!");
        } catch (URITemplateParseException e) {
            assertEquals(e.getOriginalMessage(), message);
            assertEquals(e.getOffset(), offset);
        }
    }
}
