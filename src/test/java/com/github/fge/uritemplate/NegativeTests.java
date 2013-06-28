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

import com.beust.jcommander.internal.Lists;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.uritemplate.vars.VariableMap;
import com.github.fge.uritemplate.vars.VariableMapBuilder;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.*;

public final class NegativeTests
{
    private JsonNode data;
    private VariableMap vars;

    @BeforeClass
    public void initData()
        throws IOException
    {
        data = JsonLoader.fromResource("/negative-tests.json");
        final JsonNode node = data.get("Failure Tests").get("variables");

        final VariableMapBuilder builder = VariableMap.newBuilder();
        final Iterator<Map.Entry<String, JsonNode>> iterator = node.fields();
        Map.Entry<String, JsonNode> entry;

        while (iterator.hasNext()) {
            entry = iterator.next();
            builder.addValue(entry.getKey(), Util.fromJson(entry.getValue()));
        }

        vars = builder.freeze();
    }

    @DataProvider
    public Iterator<Object[]> getData()
    {
        final JsonNode node = data.get("Failure Tests").get("testcases");
        final List<Object[]> list = Lists.newArrayList();

        for (final JsonNode element: node)
            list.add(new Object[] { element.get(0).textValue() });

        return list.iterator();
    }

    @Test(dataProvider = "getData")
    public void illegalTemplatesAreMarkedAsSuch(final String input)
    {
        try {
            new URITemplate(input).toString(vars);
            fail("No exception thrown!!");
        } catch (URITemplateParseException ignored) {
        } catch (URITemplateException ignored) {
        }
    }
}

