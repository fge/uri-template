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

public final class PathExpansionTest
    extends ExpansionTest
{
    @DataProvider
    @Override
    public Iterator<Object[]> getData()
    {
        final List<Object[]> list = Lists.newArrayList();

        // Section 3.2.6
        list.add(new Object[]{"{/who}", "/fred"});
        list.add(new Object[]{"{/who,who}", "/fred/fred"});
        list.add(new Object[]{"{/half,who}", "/50%25/fred"});
        list.add(new Object[]{"{/who,dub}", "/fred/me%2ftoo"});
        list.add(new Object[]{"{/var}", "/value"});
        list.add(new Object[]{"{/var,empty}", "/value/"});
        list.add(new Object[]{"{/var,undef}", "/value"});
        list.add(new Object[]{"{/var,x}/here", "/value/1024/here"});
        list.add(new Object[]{"{/var:1,var}", "/v/value"});
        list.add(new Object[]{"{/list}", "/red,green,blue"});
        list.add(new Object[]{"{/list*}", "/red/green/blue"});
        list.add(new Object[]{"{/list*,path:4}", "/red/green/blue/%2ffoo"});
        list.add(new Object[]{"{/keys}", "/semi,%3b,dot,.,comma,%2c"});
        list.add(new Object[]{"{/keys*}", "/semi=%3b/dot=./comma=%2c"});

        return list.iterator();
    }
}
