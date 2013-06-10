package com.github.fge.uritemplate.vars.values;

import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.msgsimple.serviceloader.MessageBundleFactory;
import com.github.fge.uritemplate.URITemplateMessageBundle;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.*;

public final class ListValueTest
{
    private static final MessageBundle BUNDLE
        = MessageBundleFactory.getBundle(URITemplateMessageBundle.class);

    private ListValue.Builder builder;

    @BeforeMethod
    public void init()
    {
        builder = ListValue.newBuilder();
    }

    @Test
    public void cannotAppendFirstNullElement()
    {
        try {
            builder.add(null);
            fail("No exception thrown!");
        } catch (NullPointerException e) {
            assertEquals(e.getMessage(),
                BUNDLE.getMessage("listValue.nullElement"));
        }
    }

    @Test
    public void cannotAppendNullElementAfterFirstOne()
    {
        try {
            builder.add(1, 2, null);
            fail("No exception thrown!");
        } catch (NullPointerException e) {
            assertEquals(e.getMessage(),
                BUNDLE.getMessage("listValue.nullElement"));
        }
    }

    @Test
    public void cannotAppendNullCollection()
    {
        try {
            builder.addAll(null);
            fail("No exception thrown!");
        } catch (NullPointerException e) {
            assertEquals(e.getMessage(),
                BUNDLE.getMessage("listValue.nullIterable"));
        }
    }

    @Test
    public void cannotAppendCollectionWithNullElement()
    {
        try {
            builder.addAll(Arrays.asList(1, 3, null));
            fail("No exception thrown!");
        } catch (NullPointerException e) {
            assertEquals(e.getMessage(),
                BUNDLE.getMessage("listValue.nullElement"));
        }
    }

    @Test
    public void addedValuesViaAddShowUpInResult()
    {
        final String s1 = "hello", s2 = "world";
        builder.add(s1, s2);
        assertEquals(builder.build().getListValue(), Arrays.asList(s1, s2));
    }

    @Test
    public void addedValuesViaAddAllShowUpInResult()
    {
        final List<String> list = Arrays.asList("hello", "world");
        builder.addAll(list);
        assertEquals(builder.build().getListValue(), list);
    }
}
