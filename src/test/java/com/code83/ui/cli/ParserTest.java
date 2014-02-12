package com.code83.ui.cli;

import junit.framework.Assert;

import org.junit.Test;

import com.code83.ui.cli.Parser;

/**
 * Unit tests for command line parser class.
 * 
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version 0.1 SVN: $Id: ParserTest.java 865 2011-12-15 03:35:16Z mngazimb $
 * @since 0.1
 */
public class ParserTest {

    @Test
    public void standardOptions () {
        Parser parser = new Parser();

        try {
            Parser.Command search = parser.addCommand("search", 's');

            parser.addCommandSwitch(search, 'f', "file");
            parser.addCommandSwitch(search, 't', "type");
            parser.addCommand("invisible", 'i');

            parser.parse("invisible");
            Assert.assertTrue("Current command should be [invisible]", parser
                    .isCommand("invisible"));

            parser.parse("i");
            Assert.assertTrue("Current command should be [invisible]", parser
                    .isCommand("invisible"));
        } catch (Parser.ParserException e) {
            Assert.fail("Invisible command should have been ok");
        }

        try {
            parser.parse("search -f my world --type music");
            if (parser.isCommand("search")) {
                String fileName = parser.getSwitchValue("file");
                String type = parser.getSwitchValue("type");
                Assert.assertEquals("Failed to match filename", "my world",
                        fileName);
                Assert.assertEquals("Failed to match file type", "music", type);
            } else {
                Assert.fail("Command should have been [search]");
            }
        } catch (Parser.ParserException pe) {
            Assert.fail("An unexpected exception was caught parsing "
                    + "command [search]. Exception [" + pe.getMessage() + "]");
        }

        try {
            parser.parse("s --file my world --t music");
            if (parser.isCommand("search")) {
                String fileName = parser.getSwitchValue("file");
                String type = parser.getSwitchValue("type");
                Assert.assertEquals("Failed to match filename", "my world",
                        fileName);
                Assert.assertEquals("Failed to match file type", "music", type);
            } else {
                Assert.fail("Command should have been [search]");
            }
        } catch (Parser.ParserException pe) {
            Assert.fail("An unexpected exception was caught parsing "
                    + "command [search]. Exception [" + pe.getMessage() + "]");
        }

    }

    @Test
    public void realSwitchNoData () {
        Parser parser = new Parser();

        try {
            Parser.Command invisible = parser.addCommand("invisible", 'i');
            invisible.addSwitch('t', "test");

            parser.parse("invisible --test");
            String value = parser.getSwitchValue("test");
            Assert.assertNull("Value of switch [test] should be null. Got ["
                    + value + "]", value);

            parser.parse("i -t");
            value = parser.getSwitchValue("test");
            Assert.assertNull("Value of switch [test] should be null. Got ["
                    + value + "]", value);
        } catch (Parser.ParserException e) {
            Assert.fail("Invisible command should have been ok");
        }
    }

    @Test
    public void undefinedSwitchNoData () {
        Parser parser = new Parser();

        try {
            parser.addCommand("invisible", 'i');

            parser.parse("invisible --test");
            Assert.fail("Invisible command should have caused an exception "
                    + "for undefined switch [test]");
        } catch (Parser.ParserException e) {
            // Do nothing here.
        }
    }

    @Test
    public void undefinedSwitchWithData () {
        Parser parser = new Parser();

        try {
            parser.addCommand("invisible", 'i');

            parser.parse("invisible --test hello world");
            Assert.fail("Invisible command should have caused an exception "
                    + "for undefined switch [test]");
        } catch (Parser.ParserException e) {
            // Do nothing here.
        }
    }

    @Test
    public void badSwitchFormat () {
        Parser parser = new Parser();

        try {
            Parser.Command command = parser.addCommand("invisible", 'i');
            command.addSwitch('t', "test");

            parser.parse("invisible -test hello world");
            Assert.fail("[-test] should have been a bad switch error");
        } catch (Parser.ParserException e) {
            // Do nothing here.
        }

        try {
            Parser.Command command = parser.addCommand("invisible", 'i');
            command.addSwitch('t', "test");

            parser.parse("invisible --t buzz aldrin");
            Assert.fail("[--t] should have been a bad switch error");
        } catch (Parser.ParserException e) {
            // Do nothing here.
        }
    }
}
