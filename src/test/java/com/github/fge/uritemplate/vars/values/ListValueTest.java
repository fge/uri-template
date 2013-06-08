package com.github.fge.uritemplate.vars.values;

import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.msgsimple.serviceloader.MessageBundles;
import com.github.fge.uritemplate.URITemplateMessageBundle;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.testng.Assert.*;

public final class ListValueTest
{
    private static final MessageBundle BUNDLE
        = MessageBundles.forClass(URITemplateMessageBundle.class);

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
}
