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

public final class SimpleExpansionTest
    extends ExpansionTest
{
    @DataProvider
    @Override
    public Iterator<Object[]> getData()
    {
        final List<Object[]> list = Lists.newArrayList();

        list.add(new Object[]{"{var}", "value"});
        list.add(new Object[]{"{hello}", "Hello%20World%21"});
        list.add(new Object[]{"{half}", "50%25"});
        list.add(new Object[]{"O{empty}X", "OX"});
        list.add(new Object[]{"O{undef}X", "OX"});
        list.add(new Object[]{"{x,y}", "1024,768"});
        list.add(new Object[]{"{x,hello,y}", "1024,Hello%20World%21,768"});
        list.add(new Object[]{"?{x,empty}", "?1024,"});
        list.add(new Object[]{"?{x,undef}", "?1024"});
        list.add(new Object[]{"?{undef,y}", "?768"});
        list.add(new Object[]{"{var:3}", "val"});
        list.add(new Object[]{"{var:30}", "value"});
        list.add(new Object[]{"{list}", "red,green,blue"});
        list.add(new Object[]{"{list*}", "red,green,blue"});
        list.add(new Object[]{"{keys}", "semi,%3B,dot,.,comma,%2C"});
        list.add(new Object[]{"{keys*}", "semi=%3B,dot=.,comma=%2C"});

        return list.iterator();
    }
}
