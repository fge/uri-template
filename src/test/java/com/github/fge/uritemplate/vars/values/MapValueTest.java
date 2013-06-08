package com.github.fge.uritemplate.vars.values;

import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.msgsimple.serviceloader.MessageBundles;
import com.github.fge.uritemplate.URITemplateMessageBundle;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public final class MapValueTest
{
    private static final MessageBundle BUNDLE
        = MessageBundles.forClass(URITemplateMessageBundle.class);

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
}
