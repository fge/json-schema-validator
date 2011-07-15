/*
 * Copyright (c) 2011, Francis Galiegue <fgaliegue@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package eel.kitchen.jsonschema.validators;

import eel.kitchen.jsonschema.validators.type.IntegerValidator;
import eel.kitchen.util.JasonHelper;
import org.codehaus.jackson.JsonNode;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

public class BrokenIntegerSchemasTest
{
    private JsonNode schemas;
    private Validator v;
    private List<String> messages;

    @BeforeClass
    public void setUp()
        throws IOException
    {
        schemas = JasonHelper.load("broken-integer-schemas.json");
    }

    @Test
    public void testBrokenMinimum()
    {
        v = new IntegerValidator().setSchema(schemas.get("broken-minimum"));

        assertFalse(v.setup());

        messages = v.getMessages();
        assertEquals(messages.size(), 1);
        assertEquals(messages.get(0), "minimum is of type number, "
            + "expected [integer]");
    }

    @Test
    public void testBrokenExclusiveMinimum()
    {
        v = new IntegerValidator()
            .setSchema(schemas.get("broken-exclusiveMinimum"));

        assertFalse(v.setup());

        messages = v.getMessages();
        assertEquals(messages.size(), 1);
        assertEquals(messages.get(0), "exclusiveMinimum is of type integer, "
            + "expected [boolean]");
    }

    @Test
    public void testBrokenMaximum()
    {
        v = new IntegerValidator().setSchema(schemas.get("broken-maximum"));

        assertFalse(v.setup());

        messages = v.getMessages();
        assertEquals(messages.size(), 1);
        assertEquals(messages.get(0),
            "maximum is of type string, " + "expected [integer]");
    }

    @Test
    public void testBrokenExclusiveMaximum()
    {
        v = new IntegerValidator()
            .setSchema(schemas.get("broken-exclusiveMaximum"));

        assertFalse(v.setup());

        messages = v.getMessages();
        assertEquals(messages.size(), 1);
        assertEquals(messages.get(0),
            "exclusiveMaximum is of type number, " + "expected [boolean]");
    }

    @Test
    public void testInvertedMinMax()
    {
        v = new IntegerValidator().setSchema(schemas.get("inverted-minmax"));

        assertFalse(v.setup());

        messages = v.getMessages();
        assertEquals(messages.size(), 1);
        assertEquals(messages.get(0), "minimum is greater than maximum");
    }

    @Test
    public void testImpossibleMatch()
    {
        v = new IntegerValidator().setSchema(schemas.get("impossible-match"));

        assertFalse(v.setup());

        messages = v.getMessages();
        assertEquals(messages.size(), 1);
        assertEquals(messages.get(0), "schema can never validate: minimum and"
            + " maximum are equal but are excluded from matching");
    }

    @Test
    public void testBrokenDivisor()
    {
        v = new IntegerValidator().setSchema(schemas.get("broken-divisor"));

        assertFalse(v.setup());

        messages = v.getMessages();
        assertEquals(messages.size(), 1);
        assertEquals(messages.get(0), "divisibleBy is of type string, "
            + "expected [integer]");
    }

    @Test
    public void testZeroDivisor()
    {
        v = new IntegerValidator().setSchema(schemas.get("zero-divisor"));

        assertFalse(v.setup());

        messages = v.getMessages();
        assertEquals(messages.size(), 1);
        assertEquals(messages.get(0), "divisibleBy is 0");
    }
}
