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

package com.github.fge.uritemplate.expand;

import com.beust.jcommander.internal.Lists;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.uritemplate.URITemplate;
import com.github.fge.uritemplate.URITemplateException;
import com.github.fge.uritemplate.Util;
import com.github.fge.uritemplate.vars.VariableMap;
import com.github.fge.uritemplate.vars.VariableMapBuilder;
import com.github.fge.uritemplate.vars.values.VariableValue;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.*;

@Test
public abstract class AbstractExpansionTest
{
    private final JsonNode testNode;
    private final VariableMap vars;

    protected AbstractExpansionTest(final String resourceName)
        throws IOException
    {
        final String resourcePath = "/expand/" + resourceName + ".json";
        testNode = JsonLoader.fromResource(resourcePath);

        final Iterator<Map.Entry<String, JsonNode>> iterator
            = testNode.get("vars").fields();
        final VariableMapBuilder builder = VariableMap.newBuilder();

        Map.Entry<String, JsonNode> entry;
        String varName;
        VariableValue value;
        while (iterator.hasNext()) {
            entry = iterator.next();
            varName = entry.getKey();
            value = Util.fromJson(entry.getValue());
            builder.addValue(varName, value);
        }

        vars = builder.freeze();
    }

    @DataProvider
    public final Iterator<Object[]> getData()
    {
        final List<Object[]> list = Lists.newArrayList();
        final Iterator<Map.Entry<String, JsonNode>> iterator
            = testNode.get("tests").fields();

        Map.Entry<String, JsonNode> entry;

        while (iterator.hasNext()) {
            entry = iterator.next();
            list.add(new Object[] { entry.getKey(),
                entry.getValue().textValue() });
        }

        return list.iterator();
    }

    @Test(dataProvider = "getData")
    public final void expansionsAreCorrect(final String input,
        final String expected)
        throws URITemplateException
    {
        final URITemplate template = new URITemplate(input);
        final String actual = template.toString(vars);

        assertEquals(expected, actual, "expansion differs from expectations");
    }
}

