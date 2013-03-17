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

import com.github.fge.uritemplate.SampleVars;
import com.github.fge.uritemplate.URITemplateException;
import com.github.fge.uritemplate.expression.URITemplateExpression;
import com.github.fge.uritemplate.parse.ExpressionParser;
import com.github.fge.uritemplate.parse.TemplateParser;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.nio.CharBuffer;
import java.util.Iterator;

import static org.testng.Assert.assertEquals;

public abstract class ExpansionTest
{
    protected static final TemplateParser PARSER = new ExpressionParser();

    @DataProvider
    public abstract Iterator<Object[]> getData();

    @Test(dataProvider = "getData")
    public final void expansionsAreCorrect(final String input,
        final String expected)
        throws URITemplateException
    {
        final CharBuffer buffer = CharBuffer.wrap(input).asReadOnlyBuffer();
        final URITemplateExpression expression = PARSER.parse(buffer);
        final String actual = expression.expand(SampleVars.get());

        assertEquals(expected, actual, "expansion differs from expectations");
    }
}

