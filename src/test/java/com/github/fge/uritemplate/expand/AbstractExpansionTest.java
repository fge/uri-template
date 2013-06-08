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

package com.github.fge.uritemplate.expand;

import com.beust.jcommander.internal.Lists;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.github.fge.uritemplate.URITemplate;
import com.github.fge.uritemplate.URITemplateException;
import com.github.fge.uritemplate.Util;
import com.github.fge.uritemplate.vars.VariableMap;
import com.github.fge.uritemplate.vars.VariableMapBuilder;
import com.github.fge.uritemplate.vars.values.VariableValue;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.*;

public abstract class AbstractExpansionTest
{
    private static final ObjectReader READER = new ObjectMapper()
        .enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)
        .reader();

    private final JsonNode testNode;
    private final VariableMap vars;

    protected AbstractExpansionTest(final String resourceName)
        throws IOException
    {

        final String resourcePath = "/expand/" + resourceName + ".json";
        final InputStream in
            = AbstractExpansionTest.class.getResourceAsStream(resourcePath);
        testNode = READER.readTree(in);
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
        final String actual = template.expand(vars);

        assertEquals(expected, actual, "expansion differs from expectations");
    }
}

