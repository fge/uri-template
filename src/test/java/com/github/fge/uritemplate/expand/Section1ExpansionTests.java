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

import com.github.fge.uritemplate.vars.values.ListValue;
import com.github.fge.uritemplate.vars.values.MapValue;
import com.github.fge.uritemplate.vars.values.ScalarValue;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.testng.annotations.DataProvider;

import java.util.Iterator;
import java.util.List;

public final class Section1ExpansionTests
    extends AbstractExpansionTest
{
    public Section1ExpansionTests()
    {
        vars.put("var", new ScalarValue("value"));
        vars.put("hello", new ScalarValue("Hello World!"));
        vars.put("path", new ScalarValue("/foo/bar"));
        vars.put("empty", new ScalarValue(""));
        vars.put("x", new ScalarValue("1024"));
        vars.put("y", new ScalarValue("768"));
        vars.put("list", new ListValue(ImmutableList.of("red", "green",
            "blue")));
        vars.put("keys", new MapValue(ImmutableMap.of("semi", ";",
            "dot", ".", "comma", ",")));
    }

    @DataProvider
    @Override
    public Iterator<Object[]> getData()
    {
        final List<Object[]> list = Lists.newArrayList();

        list.add(new Object[]{"{var}", "value"});
        list.add(new Object[]{"{hello}", "Hello%20World%21"});
        list.add(new Object[]{"{+var}", "value"});
        list.add(new Object[]{"{+hello}", "Hello%20World!"});
        list.add(new Object[]{"{+path}/here", "/foo/bar/here"});
        list.add(new Object[]{"here?ref={+path}", "here?ref=/foo/bar"});
        list.add(new Object[]{"X{#var}", "X#value"});
        list.add(new Object[]{"X{#hello}", "X#Hello%20World!"});
        list.add(new Object[]{"map?{x,y}", "map?1024,768"});
        list.add(new Object[]{"{x,hello,y}", "1024,Hello%20World%21,768"});
        list.add(new Object[]{"{+x,hello,y}", "1024,Hello%20World!,768"});
        list.add(new Object[]{"{+path,x}/here", "/foo/bar,1024/here"});
        list.add(new Object[]{"{#x,hello,y}", "#1024,Hello%20World!,768"});
        list.add(new Object[]{"{#path,x}/here", "#/foo/bar,1024/here"});
        list.add(new Object[]{"X{.var}", "X.value"});
        list.add(new Object[]{"X{.x,y}", "X.1024.768"});
        list.add(new Object[]{"{/var}", "/value"});
        list.add(new Object[]{"{/var,x}/here", "/value/1024/here"});
        list.add(new Object[]{"{;x,y}", ";x=1024;y=768"});
        list.add(new Object[]{"{;x,y,empty}", ";x=1024;y=768;empty"});
        list.add(new Object[]{"{?x,y}", "?x=1024&y=768"});
        list.add(new Object[]{"{?x,y,empty}", "?x=1024&y=768&empty="});
        list.add(new Object[]{"?fixed=yes{&x}", "?fixed=yes&x=1024"});
        list.add(new Object[]{"{&x,y,empty}", "&x=1024&y=768&empty="});
        list.add(new Object[]{"{var:3}", "val"});
        list.add(new Object[]{"{var:30}", "value"});
        list.add(new Object[]{"{list}", "red,green,blue"});
        list.add(new Object[]{"{list*}", "red,green,blue"});
        list.add(new Object[]{"{keys}", "semi,%3b,dot,.,comma,%2c"});
        list.add(new Object[]{"{keys*}", "semi=%3b,dot=.,comma=%2c"});
        list.add(new Object[]{"{+path:6}/here", "/foo/b/here"});
        list.add(new Object[]{"{+list}", "red,green,blue"});
        list.add(new Object[]{"{+list*}", "red,green,blue"});
        list.add(new Object[]{"{+keys}", "semi,;,dot,.,comma,,"});
        list.add(new Object[]{"{+keys*}", "semi=;,dot=.,comma=,"});
        list.add(new Object[]{"{#path:6}/here", "#/foo/b/here"});
        list.add(new Object[]{"{#list}", "#red,green,blue"});
        list.add(new Object[]{"{#list*}", "#red,green,blue"});
        list.add(new Object[]{"{#keys}", "#semi,;,dot,.,comma,,"});
        list.add(new Object[]{"{#keys*}", "#semi=;,dot=.,comma=,"});
        list.add(new Object[]{"X{.var:3}", "X.val"});
        list.add(new Object[]{"X{.list}", "X.red,green,blue"});
        list.add(new Object[]{"X{.list*}", "X.red.green.blue"});
        list.add(new Object[]{"X{.keys}", "X.semi,%3b,dot,.,comma,%2c"});
        list.add(new Object[]{"X{.keys*}", "X.semi=%3b.dot=..comma=%2c"});
        list.add(new Object[]{"{/var:1,var}", "/v/value"});
        list.add(new Object[]{"{/list}", "/red,green,blue"});
        list.add(new Object[]{"{/list*}", "/red/green/blue"});
        list.add(new Object[]{"{/list*,path:4}", "/red/green/blue/%2ffoo"});
        list.add(new Object[]{"{/keys}", "/semi,%3b,dot,.,comma,%2c"});
        list.add(new Object[]{"{/keys*}", "/semi=%3b/dot=./comma=%2c"});
        list.add(new Object[]{"{;hello:5}", ";hello=Hello"});
        list.add(new Object[]{"{;list}", ";list=red,green,blue"});
        list.add(new Object[]{"{;list*}", ";list=red;list=green;list=blue"});
        list.add(new Object[]{"{;keys}", ";keys=semi,%3b,dot,.,comma,%2c"});
        list.add(new Object[]{"{;keys*}", ";semi=%3b;dot=.;comma=%2c"});
        list.add(new Object[]{"{?var:3}", "?var=val"});
        list.add(new Object[]{"{?list}", "?list=red,green,blue"});
        list.add(new Object[]{"{?list*}", "?list=red&list=green&list=blue"});
        list.add(new Object[]{"{?keys}", "?keys=semi,%3b,dot,.,comma,%2c"});
        list.add(new Object[]{"{?keys*}", "?semi=%3b&dot=.&comma=%2c"});
        list.add(new Object[]{"{&var:3}", "&var=val"});
        list.add(new Object[]{"{&list}", "&list=red,green,blue"});
        list.add(new Object[]{"{&list*}", "&list=red&list=green&list=blue"});
        list.add(new Object[]{"{&keys}", "&keys=semi,%3b,dot,.,comma,%2c"});
        list.add(new Object[]{"{&keys*}", "&semi=%3b&dot=.&comma=%2c"});

        return list.iterator();
    }
}
