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

package com.github.fge.uritemplate.expression;

import com.github.fge.uritemplate.CharMatchers;
import com.github.fge.uritemplate.URITemplateException;
import com.github.fge.uritemplate.vars.specs.VariableSpec;
import com.github.fge.uritemplate.vars.values.ValueType;
import com.github.fge.uritemplate.vars.values.VariableValue;
import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.primitives.UnsignedBytes;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

public final class TemplateExpression
    implements URITemplateExpression
{
    private static final Joiner COMMA = Joiner.on(',');

    private final ExpressionType expressionType;
    private final List<VariableSpec> variableSpecs;

    public TemplateExpression(final ExpressionType expressionType,
        final List<VariableSpec> variableSpecs)
    {
        this.expressionType = expressionType;
        this.variableSpecs = ImmutableList.copyOf(variableSpecs);
    }

    @Override
    public String expand(final Map<String, VariableValue> vars)
        throws URITemplateException
    {
        /*
         * List which will be joined with the expression type's joining
         * character, yielding the final result
         */
        final List<String> list = Lists.newArrayList();

        /*
         * Walk the varspecs list: if the name of the variable exists in the
         * variable map, add the rendered string to the list
         */
        String varname;
        VariableValue value;
        String rendered;
        for (final VariableSpec varspec: variableSpecs) {
            varname = varspec.getName();
            value = vars.get(varname);
            if (value == null)
                continue;
            rendered = varspec.render(expressionType, value);
            if (rendered != null)
                list.add(rendered);
        }

        /*
         * If the list is empty, return the empty string. Otherwise, return the
         * prefix followed by all joined rendered strings.
         */
        if (list.isEmpty())
            return "";

        final Joiner joiner = Joiner.on(expressionType.separator);
        final String joined = joiner.join(list);
        return expressionType.prefix + joined;
    }

    @Override
    public String expand2(final Map<String, VariableValue> vars)
        throws URITemplateException
    {
        /*
         * Near exact reproduction of the suggested algorithm, with two
         * differences:
         *
         * - parsing errors are treated elsewhere;
         * - the possibility of varspecs both exploded and prefixed is left
         *   open; not here: it is one or the other, not both.
         */

        // Expanded values
        final List<String> expansions = Lists.newArrayList();

        VariableValue value;

        /*
         * Walk over the defined varspecs for this template
         */
        for (final VariableSpec varspec: variableSpecs) {
            value = vars.get(varspec.getName());
            // No such variable: continue
            if (value == null)
                continue;
            if (value.getType() == ValueType.SCALAR) {
                expansions.add(expandString(varspec, value.getScalarValue()));
                continue;
            }
            if (value.isEmpty())
                if (varspec.isExploded() || !expressionType.named)
                    continue;
            expansions.add(varspec.isExploded()
                ? expandExplode(varspec.getName(), value)
                : expandNormal(varspec.getName(), value));
        }

        if (expansions.isEmpty())
            return "";
        final Joiner joiner = Joiner.on(expressionType.separator);
        // Where the final result is stored
        final StringBuilder sb = new StringBuilder(expressionType.prefix);
        joiner.appendTo(sb, expansions);
        return sb.toString();
    }

    @Override
    public int hashCode()
    {
        return 31 * expressionType.hashCode() + variableSpecs.hashCode();
    }

    @Override
    public boolean equals(final Object obj)
    {
        if (obj == null)
            return false;
        if (this == obj)
            return true;
        if (getClass() != obj.getClass())
            return false;
        final TemplateExpression other = (TemplateExpression) obj;
        return expressionType == other.expressionType
            && variableSpecs.equals(other.variableSpecs);
    }

    /*
     * Expand the value if the variable is a simple string
     */
    private String expandString(final VariableSpec varspec, final String value)
    {
        String ret = "";
        if (expressionType.named) {
            // Note: variable names do not contain any character susceptible to
            // be percent encoded, so we leave them intact
            ret += varspec.getName();
            if (value.isEmpty())
                return ret + expressionType.ifEmpty;
            ret += '=';
        }
        final int len = value.length();
        final int prefixLen = varspec.getPrefixLength();
        final String val = prefixLen == -1 ? value
            : value.substring(0, Math.min(len, prefixLen));
        ret += pctEncode(val);
        return ret;
    }

    /*
     * Expand a list or map if no explode modifier
     */
    private String expandNormal(final String name,  final VariableValue value)
    {
        final StringBuilder sb = new StringBuilder();

        if (expressionType.named) {
            sb.append(name);
            if (value.isEmpty())
                return sb.append(expressionType.ifEmpty).toString();
            sb.append('=');
        }

        final List<String> list = Lists.newArrayList();

        if (value.getType() == ValueType.ARRAY)
            for (final String s: value.getListValue())
                list.add(pctEncode(s));
        else // map
            for (final Map.Entry<String, String> entry:
                value.getMapValue().entrySet()) {
                list.add(pctEncode(entry.getKey()));
                list.add(pctEncode(entry.getValue()));
            }

        COMMA.appendTo(sb, list);
        return sb.toString();
    }

    /*
     * Expand a list or map if an explode modifier is given
     */
    private String expandExplode(final String name, final VariableValue value)
    {
        return expressionType.named ? expandNamed(name, value)
            : expandUnnamed(name, value);
    }

    /*
     * On explode: expand if the template type is named
     */
    private String expandNamed(final String name, final VariableValue value)
    {
        final Joiner joiner = Joiner.on(expressionType.separator);
        final List<String> elements = Lists.newArrayList();

        StringBuilder element;

        if (value.getType() == ValueType.ARRAY) {
            for (final String s: value.getListValue()) {
                element = new StringBuilder(name);
                if (s.isEmpty())
                    element.append(expressionType.ifEmpty);
                else
                    element.append('=').append(pctEncode(s));
                elements.add(element.toString());
            }
        } else { // map
            String s;
            for (final Map.Entry<String, String> entry:
                value.getMapValue().entrySet()) {
                element = new StringBuilder(pctEncode(entry.getKey()));
                s = entry.getValue();
                if (s.isEmpty())
                    element.append(expressionType.ifEmpty);
                else
                    element.append('=').append(pctEncode(s));
                elements.add(element.toString());
            }
        }

        return joiner.join(elements);
    }

    /*
     * On explode: expand if the template type is unnamed
     */
    private String expandUnnamed(final String name, final VariableValue value)
    {
        final Joiner joiner = Joiner.on(expressionType.separator);
        final List<String> elements = Lists.newArrayList();

        if (value.getType() == ValueType.ARRAY)
            for (final String s: value.getListValue())
                elements.add(pctEncode(s));
        else // map
            for (final Map.Entry<String, String> entry:
                value.getMapValue().entrySet()) {
                elements.add(pctEncode(entry.getKey()));
                elements.add(pctEncode(entry.getKey()));
            }

        return joiner.join(elements);
    }

    /*
     * Do a percent encoding of a string value
     */
    private String pctEncode(final String s)
    {
        final CharMatcher matcher = expressionType.rawExpand
            ? CharMatchers.RESERVED_PLUS_UNRESERVED : CharMatchers.UNRESERVED;

        final StringBuilder sb = new StringBuilder(s.length());
        for (final char c: s.toCharArray())
            sb.append(matcher.matches(c) ? c : encodeChar(c));
        return sb.toString();
    }

    /*
     * Do a percent encoding of a single character
     */
    private static String encodeChar(final char c)
    {
        final String tmp = new String(new char[] { c });
        final byte[] bytes = tmp.getBytes(Charset.forName("UTF-8"));
        final StringBuilder sb = new StringBuilder();
        for (final byte b: bytes)
            sb.append('%').append(UnsignedBytes.toString(b, 16));
        return sb.toString();
    }
}
