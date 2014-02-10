/*
 * Copyright (c) 2014, Francis Galiegue (fgaliegue@gmail.com)
 *
 * This software is dual-licensed under:
 *
 * - the Lesser General Public License (LGPL) version 3.0 or, at your option, any
 *   later version;
 * - the Apache Software License (ASL) version 2.0.
 *
 * The text of both licenses is available under the src/resources/ directory of
 * this project (under the names LGPL-3.0.txt and ASL-2.0.txt respectively).
 *
 * Direct link to the sources:
 *
 * - LGPL 3.0: https://www.gnu.org/licenses/lgpl-3.0.txt
 * - ASL 2.0: http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package com.github.fge.uritemplate;

import com.github.fge.uritemplate.vars.values.ListValue;
import com.github.fge.uritemplate.vars.values.MapValue;
import com.github.fge.uritemplate.vars.values.ScalarValue;
import com.github.fge.uritemplate.vars.values.VariableValue;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

public final class SampleVars
{
    private static final Map<String, VariableValue> VARS;

    private SampleVars()
    {
    }

    static {
        final ImmutableMap.Builder<String, VariableValue> builder
            = ImmutableMap.builder();

        String name;
        VariableValue value;

        /*
         * Extracted from section 3 of the RFC
         */
        name = "count";
        value = ListValue.of("one", "two", "three");
        builder.put(name, value);

        name = "dom";
        value = ListValue.of("example", "com");
        builder.put(name, value);

        name = "dub";
        value = new ScalarValue("me/too");
        builder.put(name, value);

        name = "hello";
        value = new ScalarValue("Hello World!");
        builder.put(name, value);

        name = "half";
        value = new ScalarValue("50%");
        builder.put(name, value);

        name = "var";
        value = new ScalarValue("value");
        builder.put(name, value);

        name = "who";
        value = new ScalarValue("fred");
        builder.put(name, value);

        name = "base";
        value = new ScalarValue("http://example.com/home/");
        builder.put(name, value);

        name = "path";
        value = new ScalarValue("/foo/bar");
        builder.put(name, value);

        name = "list";
        value = ListValue.of("red", "green", "blue");
        builder.put(name, value);

        name = "keys";
        value = MapValue.newBuilder().put("semi", ";").put("dot", ".")
            .put("comma", ",").build();
        builder.put(name, value);

        name = "v";
        value = new ScalarValue("6");
        builder.put(name, value);

        name = "x";
        value = new ScalarValue("1024");
        builder.put(name, value);

        name = "y";
        value = new ScalarValue("768");
        builder.put(name, value);

        name = "empty";
        value = new ScalarValue("");
        builder.put(name, value);

        name = "empty_keys";
        value = MapValue.newBuilder().build();
        builder.put(name, value);

        // undef not defined
        VARS = builder.build();
    }

    public static Map<String, VariableValue> get()
    {
        return VARS;
    }
}

