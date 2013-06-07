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

import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.msgsimple.source.MessageSource;
import com.github.fge.msgsimple.source.PropertiesMessageSource;

import java.io.IOException;

public final class URITemplateMessages
{
    private static final MessageBundle BUNDLE;

    static {
        final String resourcePath
            = "/com/github/fge/uritemplate/messages.properties";
        final MessageSource source;

        try {
            source = PropertiesMessageSource.fromResource(resourcePath);
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }

        BUNDLE = MessageBundle.newBuilder().appendSource(source).freeze();
    }

    private URITemplateMessages()
    {
    }

    public static MessageBundle get()
    {
        return BUNDLE;
    }
}
