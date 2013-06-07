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

import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.msgsimple.serviceloader.MessageBundles;
import com.github.fge.uritemplate.URITemplateMessageBundle;
import com.github.fge.uritemplate.URITemplateParseException;
import com.google.common.collect.Lists;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.List;

import static org.testng.Assert.*;

public final class ParsingExceptionsTest
{
    private static final MessageBundle BUNDLE
        = MessageBundles.forClass(URITemplateMessageBundle.class);

    @DataProvider
    public Iterator<Object[]> invalidInputs()
    {
        final List<Object[]> list = Lists.newArrayList();

        String input;
        String message;
        int offset;

        input = "foo%";
        message = BUNDLE.getMessage("PERCENT_SHORT_READ");
        offset = 3;
        list.add(new Object[]{input, message, offset});

        input = "foo%r";
        message = BUNDLE.getMessage("PERCENT_SHORT_READ");
        offset = 3;
        list.add(new Object[]{input, message, offset});

        input = "foo%ra";
        message = BUNDLE.getMessage("ILLEGAL_PERCENT");
        offset = 4;
        list.add(new Object[]{input, message, offset});

        input = "foo%ar";
        message = BUNDLE.getMessage("ILLEGAL_PERCENT");
        offset = 5;
        list.add(new Object[]{input, message, offset});

        input = "foo<";
        message = BUNDLE.getMessage("NO_PARSER");
        offset = 3;
        list.add(new Object[]{input, message, offset});

        input = "foo{";
        message = BUNDLE.getMessage("UNEXPECTED_EOF");
        offset = 3;
        list.add(new Object[]{input, message, offset});

        return list.iterator();
    }

    @Test(dataProvider = "invalidInputs")
    public void invalidInputsRaiseAppropriateExceptions(final String input,
        final String message, final int offset)
    {
        try {
            URITemplateParser.parse(input);
            fail("No exception thrown!!");
        } catch (URITemplateParseException e) {
            assertEquals(e.getOriginalMessage(), message);
            assertEquals(e.getOffset(), offset);
        }
    }
}
