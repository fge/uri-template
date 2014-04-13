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

package com.github.fge.uritemplate.vars.values;

import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.msgsimple.load.MessageBundles;
import com.github.fge.uritemplate.URITemplateMessageBundle;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.*;

public final class ListValueTest
{
    private static final MessageBundle BUNDLE
        = MessageBundles.getBundle(URITemplateMessageBundle.class);

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
