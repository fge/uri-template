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

import com.beust.jcommander.internal.Lists;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.uritemplate.vars.VariableMap;
import com.github.fge.uritemplate.vars.VariableMapBuilder;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.*;

public final class SpecExamplesBySectionTest
{
    private JsonNode data;

    @BeforeClass
    public void initData()
        throws IOException
    {
        data = JsonLoader.fromResource("/spec-examples-by-section.json");
    }

    @DataProvider
    public Iterator<Object[]> getData()
    {
        final List<Object[]> list = Lists.newArrayList();

        VariableMapBuilder builder;
        Iterator<Map.Entry<String, JsonNode>> iterator;
        Map.Entry<String, JsonNode> entry;

        for (final JsonNode node: data) { // Cycle through values
            builder = VariableMap.newBuilder();
            iterator = node.get("variables").fields();
            while (iterator.hasNext()) {
                entry = iterator.next();
                if (entry.getValue().isNull())
                    continue;
                builder.addValue(entry.getKey(),
                    Util.fromJson(entry.getValue()));
            }
            for (final JsonNode n: node.get("testcases"))
                list.add(new Object[]{ n.get(0).textValue(),
                    builder.freeze(), n.get(1) });
        }

        Collections.shuffle(list);

        return list.iterator();
    }

    @Test(dataProvider = "getData")
    public void illegalTemplatesAreMarkedAsSuch(final String tmpl,
        final VariableMap vars, final JsonNode resultNode)
        throws URITemplateException
    {
        final URITemplate template = new URITemplate(tmpl);
        final String actual = template.toString(vars);

        if (resultNode.isTextual()) {
            assertEquals(actual, resultNode.textValue());
            return;
        }

        if (!resultNode.isArray())
            throw new RuntimeException("Didn't expect that");

        boolean found = false;
        for (final JsonNode node: resultNode)
            if (node.textValue().equals(actual))
                found = true;

        assertTrue(found, "no value matched expansion");
    }
}

