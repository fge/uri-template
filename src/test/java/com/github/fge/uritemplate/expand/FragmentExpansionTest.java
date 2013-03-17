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

public final class FragmentExpansionTest
    extends Section3ExpansionTests
{
    @DataProvider
    @Override
    public Iterator<Object[]> getData()
    {
        final List<Object[]> list = Lists.newArrayList();

        // Section 3.2.4
        list.add(new Object[]{"{#var}", "#value"});
        list.add(new Object[]{"{#hello}", "#Hello%20World!"});
        list.add(new Object[]{"{#half}", "#50%25"});
        list.add(new Object[]{"foo{#empty}", "foo#"});
        list.add(new Object[]{"foo{#undef}", "foo"});
        list.add(new Object[]{"{#x,hello,y}", "#1024,Hello%20World!,768"});
        list.add(new Object[]{"{#path,x}/here", "#/foo/bar,1024/here"});
        list.add(new Object[]{"{#path:6}/here", "#/foo/b/here"});
        list.add(new Object[]{"{#list}", "#red,green,blue"});
        list.add(new Object[]{"{#list*}", "#red,green,blue"});
        list.add(new Object[]{"{#keys}", "#semi,;,dot,.,comma,,"});
        list.add(new Object[]{"{#keys*}", "#semi=;,dot=.,comma=,"});

        return list.iterator();
    }
}
