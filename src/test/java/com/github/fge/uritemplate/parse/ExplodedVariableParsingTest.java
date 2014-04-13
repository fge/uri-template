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

public final class ExplodedVariableParsingTest
{
    @DataProvider
    public Iterator<Object[]> validInputs()
    {
        final List<Object[]> list = Lists.newArrayList();

        String input;

        input = "foo*";
        list.add(new Object[]{ input });

        input = "%33foo*";
        list.add(new Object[]{ input });

        input = "foo%20*";
        list.add(new Object[]{ input });

        input = "foo_%20bar*";
        list.add(new Object[]{ input });

        input = "FoOb%02ZAZE287*";
        list.add(new Object[]{ input });

        input = "foo.bar*";
        list.add(new Object[]{ input });

        input = "foo_%20bar.baz%af.r*";
        list.add(new Object[]{ input });

        return list.iterator();
    }

    @Test(dataProvider = "validInputs")
    public void simpleVariablesAreParsedCorrectly(final String input)
        throws URITemplateParseException
    {
        final CharBuffer buffer = CharBuffer.wrap(input).asReadOnlyBuffer();
        final VariableSpec varspec = VariableSpecParser.parse(buffer);

        assertEquals(varspec.getName(), input.substring(0, input.length() - 1));
        assertSame(varspec.getType(), VariableSpecType.EXPLODED,
            "unexpected class for parsed variable");
        assertFalse(buffer.hasRemaining());
    }
}
