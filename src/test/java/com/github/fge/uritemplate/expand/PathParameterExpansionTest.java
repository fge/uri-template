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

import com.google.common.collect.Lists;
import org.testng.annotations.DataProvider;

import java.util.Iterator;
import java.util.List;

public final class PathParameterExpansionTest
    extends ExpansionTest
{
    @DataProvider
    @Override
    public Iterator<Object[]> getData()
    {
        final List<Object[]> list = Lists.newArrayList();

        // Section 3.2.7
        list.add(new Object[]{"{;who}", ";who=fred"});
        list.add(new Object[]{"{;half}", ";half=50%25"});
        list.add(new Object[]{"{;empty}", ";empty"});
        list.add(new Object[]{"{;v,empty,who}", ";v=6;empty;who=fred"});
        list.add(new Object[]{"{;v,bar,who}", ";v=6;who=fred"});
        list.add(new Object[]{"{;x,y}", ";x=1024;y=768"});
        list.add(new Object[]{"{;x,y,empty}", ";x=1024;y=768;empty"});
        list.add(new Object[]{"{;x,y,undef}", ";x=1024;y=768"});
        list.add(new Object[]{"{;hello:5}", ";hello=Hello"});
        list.add(new Object[]{"{;list}", ";list=red,green,blue"});
        list.add(new Object[]{"{;list*}", ";list=red;list=green;list=blue"});
        list.add(new Object[]{"{;keys}", ";keys=semi,%3b,dot,.,comma,%2c"});
        list.add(new Object[]{"{;keys*}", ";semi=%3b;dot=.;comma=%2c"});

        return list.iterator();
    }
}
