package com.github.fge.uritemplate.parse;

import com.github.fge.uritemplate.URITemplateException;
import com.github.fge.uritemplate.URITemplateParseException;
import com.github.fge.uritemplate.expression.URITemplateExpression;
import com.github.fge.uritemplate.vars.VariableValue;
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
