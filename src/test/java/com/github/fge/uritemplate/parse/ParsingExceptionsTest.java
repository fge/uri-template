package com.github.fge.uritemplate.parse;

import com.github.fge.uritemplate.ExceptionMessages;
import com.github.fge.uritemplate.URITemplateParseException;
import com.google.common.collect.Lists;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.List;

import static org.testng.Assert.*;

public final class ParsingExceptionsTest
{

    @DataProvider
    public Iterator<Object[]> invalidInputs()
    {
        final List<Object[]> list = Lists.newArrayList();

        String input;
        String message;
        int offset;

        input = "foo%";
        message = ExceptionMessages.PERCENT_SHORT_READ;
        offset = 3;
        list.add(new Object[]{input, message, offset});

        input = "foo%r";
        message = ExceptionMessages.PERCENT_SHORT_READ;
        offset = 3;
        list.add(new Object[]{input, message, offset});

        input = "foo%ra";
        message = ExceptionMessages.ILLEGAL_PERCENT;
        offset = 4;
        list.add(new Object[]{input, message, offset});

        input = "foo%ar";
        message = ExceptionMessages.ILLEGAL_PERCENT;
        offset = 5;
        list.add(new Object[]{input, message, offset});

        input = "foo<";
        message = ExceptionMessages.NO_PARSER;
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
