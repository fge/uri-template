package com.github.fge.uritemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.uritemplate.vars.values.ListValue;
import com.github.fge.uritemplate.vars.values.MapValue;
import com.github.fge.uritemplate.vars.values.ScalarValue;
import com.github.fge.uritemplate.vars.values.VariableValue;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

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
            return new ListValue(list);
        }
        if (node.isObject()) {
            final Map<String, String> map = Maps.newHashMap();
            final Iterator<Map.Entry<String, JsonNode>> iterator
                = node.fields();
            Map.Entry<String, JsonNode> entry;
            while (iterator.hasNext()) {
                entry = iterator.next();
                map.put(entry.getKey(), entry.getValue().textValue());
            }
            return new MapValue(map);
        }
        throw new RuntimeException("cannot bind JSON to variable value");
    }
}
