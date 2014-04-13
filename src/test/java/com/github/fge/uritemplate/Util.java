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

package com.github.fge.uritemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.uritemplate.vars.values.ListValue;
import com.github.fge.uritemplate.vars.values.MapValue;
import com.github.fge.uritemplate.vars.values.ScalarValue;
import com.github.fge.uritemplate.vars.values.VariableValue;

import java.util.Iterator;
import java.util.Map;

public final class Util
{
    // TODO: improve and integrate in main sources?
    private Util()
    {
    }

    public static VariableValue fromJson(final JsonNode node)
    {
        if (node.isTextual())
            return new ScalarValue(node.textValue());
        if (node.isArray()) {
            final ListValue.Builder builder = ListValue.newBuilder();
            for (final JsonNode element: node)
                builder.add(element.textValue());
            return builder.build();
        }
        if (node.isObject()) {
            final MapValue.Builder builder = MapValue.newBuilder();
            final Iterator<Map.Entry<String, JsonNode>> iterator
                = node.fields();
            Map.Entry<String, JsonNode> entry;
            while (iterator.hasNext()) {
                entry = iterator.next();
                builder.put(entry.getKey(), entry.getValue().textValue());
            }
            return builder.build();
        }
        throw new RuntimeException("cannot bind JSON to variable value");
    }
}
