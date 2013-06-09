/*
 * Copyright (c) 2013, Francis Galiegue <fgaliegue@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Lesser GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Lesser GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.fge.uritemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.uritemplate.vars.values.ListValue;
import com.github.fge.uritemplate.vars.values.MapValue;
import com.github.fge.uritemplate.vars.values.ScalarValue;
import com.github.fge.uritemplate.vars.values.VariableValue;
import com.google.common.collect.Lists;

import java.util.Iterator;
import java.util.List;
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
            final List<String> list = Lists.newArrayList();
            for (final JsonNode element: node)
                list.add(element.textValue());
            return ListValue.of(list);
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
