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
import com.github.fge.uritemplate.URITemplate;
import com.github.fge.uritemplate.URITemplateException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;

import static org.testng.Assert.*;

public abstract class ExpansionTest
{
    @DataProvider
    public abstract Iterator<Object[]> getData();

    @Test(dataProvider = "getData")
    public final void expansionsAreCorrect(final String input,
        final String expected)
        throws URITemplateException
    {
        final URITemplate template = new URITemplate(input);
        final String actual = template.expand(SampleVars.get());

        assertEquals(expected, actual, "expansion differs from expectations");
    }
}

