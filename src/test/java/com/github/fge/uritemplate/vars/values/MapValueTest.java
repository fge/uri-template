package com.github.fge.uritemplate.vars.values;

import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.msgsimple.load.MessageBundles;
import com.github.fge.uritemplate.URITemplateMessageBundle;
import com.google.common.collect.ImmutableMap;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Map;

import static org.testng.Assert.*;

public final class MapValueTest
{
    private static final MessageBundle BUNDLE
        = MessageBundles.getBundle(URITemplateMessageBundle.class);

    private MapValue.Builder builder;

    @BeforeMethod
    public void init()
    {
        builder = MapValue.newBuilder();
    }

    @Test
    public void builderForbidsNullKeys()
    {
        try {
            builder.put(null, null);
            fail("No exception thrown!");
        } catch (NullPointerException e) {
            assertEquals(e.getMessage(), BUNDLE.getMessage("mapValue.nullKey"));
        }
    }

    @Test
    public void builderForbidsNullValues()
    {
        try {
            builder.put("foo", null);
            fail("No exception thrown!");
        } catch (NullPointerException e) {
            assertEquals(e.getMessage(),
                BUNDLE.getMessage("mapValue.nullValue"));
        }
    }

    @Test
    public void builderForbidsNullMaps()
    {
        try {
            builder.putAll(null);
            fail("No exception thrown!");
        } catch (NullPointerException e) {
            assertEquals(e.getMessage(), BUNDLE.getMessage("mapValue.nullMap"));
        }
    }

    @Test
    public void entriesAddedViaPutShowUpInResult()
    {
        final String key = "key", value = "value";
        builder.put(key, value);
        assertEquals(builder.build().getMapValue(), ImmutableMap.of(key, value));
    }

    @Test
    public void entriesAddedViaPutAllShowUpInResult()
    {
        final Map<String, String> map = ImmutableMap.of("key", "value");
        builder.putAll(map);
        assertEquals(builder.build().getMapValue(), map);
    }
}
