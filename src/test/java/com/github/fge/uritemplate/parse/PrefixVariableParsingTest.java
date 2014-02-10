/*
 * Copyright (c) 2014, Francis Galiegue (fgaliegue@gmail.com)
 *
 * This software is dual-licensed under:
 *
 * - the Lesser General Public License (LGPL) version 3.0 or, at your option, any
 *   later version;
 * - the Apache Software License (ASL) version 2.0.
 *
 * The text of both licenses is available under the src/resources/ directory of
 * this project (under the names LGPL-3.0.txt and ASL-2.0.txt respectively).
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

public final class PrefixVariableParsingTest
{
    private static final MessageBundle BUNDLE
        = MessageBundles.getBundle(URITemplateMessageBundle.class);

    @DataProvider
    public Iterator<Object[]> validInputs()
    {
        final List<Object[]> list = Lists.newArrayList();

        String input;

        input = "foo:323";
        list.add(new Object[]{ input });

        input = "%33foo:323";
        list.add(new Object[]{ input });

        input = "foo%20:323";
        list.add(new Object[]{ input });

        input = "foo_%20bar:323";
        list.add(new Object[]{ input });

        input = "FoOb%02ZAZE287:323";
        list.add(new Object[]{ input });

        input = "foo.bar:323";
        list.add(new Object[]{ input });

        input = "foo_%20bar.baz%af.r:323";
        list.add(new Object[]{ input });

        return list.iterator();
    }

    @Test(dataProvider = "validInputs")
    public void simpleVariablesAreParsedCorrectly(final String input)
        throws URITemplateParseException
    {
        final CharBuffer buffer = CharBuffer.wrap(input).asReadOnlyBuffer();
        final VariableSpec varspec = VariableSpecParser.parse(buffer);

        assertEquals(varspec.getName(),
            input.substring(0, input.indexOf(':')));
        assertSame(varspec.getType(), VariableSpecType.PREFIX,
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

        input = "foo:";
        message = BUNDLE.getMessage("parse.emptyPrefix");
        offset = 3;
        list.add(new Object[]{input, message, offset});

        input = "foo:-1";
        message = BUNDLE.getMessage("parse.emptyPrefix");
        offset = 3;
        list.add(new Object[]{input, message, offset});

        input = "foo:a";
        message = BUNDLE.getMessage("parse.emptyPrefix");
        offset = 3;
        list.add(new Object[]{input, message, offset});

        input = "foo:10001";
        message = BUNDLE.getMessage("parse.prefixTooLarge");
        offset = 8;
        list.add(new Object[]{input, message, offset});

        input = "foo:2147483648";
        message = BUNDLE.getMessage("parse.prefixTooLarge");
        offset = 13;
        list.add(new Object[]{input, message, offset});

        return list.iterator();
    }

    @Test(dataProvider = "invalidInputs")
    public void illegalInputsRaiseAppropriateExceptions(final String input,
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
