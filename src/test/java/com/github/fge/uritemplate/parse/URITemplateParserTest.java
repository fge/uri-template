package com.github.fge.uritemplate.parse;

import com.github.fge.uritemplate.ExceptionMessages;
import com.github.fge.uritemplate.URITemplateParseException;
import com.github.fge.uritemplate.expression.URITemplateExpression;
import com.github.fge.uritemplate.vars.TemplateVariable;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.*;

public final class URITemplateParserTest
{
    private static final Map<String, TemplateVariable> VARS
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
        throws URITemplateParseException
    {
        final List<URITemplateExpression> list = URITemplateParser.parse(input);

        assertEquals(list.get(0).expand(VARS), input);
    }

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

    @Test
    public void parsingEmptyInputGivesEmptyList()
        throws URITemplateParseException
    {
        final List<URITemplateExpression> list = URITemplateParser.parse("");
        assertTrue(list.isEmpty());
    }
}
