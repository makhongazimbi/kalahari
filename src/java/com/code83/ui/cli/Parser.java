package com.code83.ui.cli;

import java.util.HashMap;
import java.util.Map;

/**
 * Set the value of empty command. If the parser input is the empty string this
 * method should be called with value TRUE, otherwise the parser method should
 * call this method with value FAL /** Parser for command line user interface.
 * 
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version 0.1 SVN $Id: Parser.java 865 2011-12-15 03:35:16Z mngazimb $
 * @since 0.1
 */
public class Parser {

    /**
     * Map of commands.
     */
    private final Map<String, Command> commands;

    /**
     * Command to be parsed.
     */
    private Command command;

    /**
     * Default constructor.
     */
    public Parser () {
        this.commands = new HashMap<String, Command>();
    }

    /**
     * An inner class representing a command.
     * 
     * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
     * @version 0.1 SVN $Id: Parser.java 865 2011-12-15 03:35:16Z mngazimb $
     * @since 0.1
     */
    public static class Command {

        /**
         * Command name.
         */
        private final String name;

        /**
         * Command alias.
         */
        private final Character alias;

        /**
         * Map of command switch aliases.
         */
        private final Map<Character, String> aliases;

        /**
         * Map of switch values.
         */
        private final Map<String, String> values;

        /**
         * Default constructor.
         */
        public Command (final String name, final char alias) {
            this.name = name;
            this.alias = alias;
            this.aliases = new HashMap<Character, String>();
            this.values = new HashMap<String, String>();
        }

        /**
         * Add a switch with no values.
         * 
         * @param shortForm
         *            Single character short form
         * @param longForm
         *            String long form of the command
         */
        protected void addSwitch (final char shortForm, final String longForm) {
            this.aliases.put(shortForm, longForm);
        }

        /**
         * Get the value of a named switch.
         * 
         * @param name
         *            Name of switch
         * @return Value of switch.
         */
        protected String getSwitchValue (final String name) {
            return this.values.get(name);
        }

        /**
         * Add the value of a named switch.
         * 
         * @param name
         *            Name of switch
         * @param value
         *            Value of the switch
         * @return True if switch value successfully added, false otherwise
         */
        protected boolean addSwitchValue (String name, final String value) {
            boolean switchExists = false;
            String nameKey = name;
            if (nameKey.length() == 1) {
                Character key = nameKey.charAt(0);
                switchExists = this.aliases.containsKey(key);
                name = this.aliases.get(key);
            } else {
                switchExists = this.aliases.containsValue(name);
            }

            this.values.put(name, value);
            return switchExists;
        }

        /**
         * Get the long form alias of a switch given the short form name.
         * 
         * @return String long form
         */
        public String getSwitchAlias (final Character name) {
            return this.aliases.get(name);
        }

        /**
         * Check if the given short name is the alias for this command.
         * 
         * @param name
         *            Short name of the command
         * @return True if this is the short name of the given command.
         */
        public boolean isCommand (final char name) {
            return this.alias.equals(name);
        }

        /**
         * Get long form name.
         * 
         * @return Name
         */
        public String getName () {
            return this.name;
        }

        /**
         * Get the alias for this command.
         * 
         * @return Alias
         */
        public Character getAlias () {
            return this.alias;
        }

        /**
         * Get the name of this command.
         * 
         * @return Command name
         */
        @Override
        public String toString () {
            return this.name;
        }

    }

    /**
     * An class representing a general parser exception.
     * 
     * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
     * @version 0.1 SVN: $Id: Parser.java 865 2011-12-15 03:35:16Z mngazimb $
     * @since 0.1
     */
    public static class ParserException extends Exception {

        /**
         * Serial version UID.
         */
        private static final long serialVersionUID = -2892055694080698184L;

        /**
         * Default constructor
         */
        public ParserException () {
            super();
        }

        /**
         * Constructor that accepts message.
         * 
         * @param message
         *            Exception message
         */
        public ParserException (final String message) {
            super(message);
        }

    }

    public static class UnknownCommandException extends ParserException {

        /**
         * Default version serial ID
         */
        private static final long serialVersionUID = 1571152260023852156L;

    }

    /**
     * Parse the user command provided at the command line.
     * 
     * @param command
     *            Command entered
     * @throws ParserException
     */
    public void parse (final String command) throws ParserException {
        // Reset current command to null.
        this.command = null;
        String raw = command.trim();

        if (raw.isEmpty()) {
            return;
        }

        if (!raw.contains(" ")) {
            // Simple case switch-less command
            this.setCurrentCommand(raw);
        } else {
            String[] cmnd = raw.split(" ", 2);
            this.setCurrentCommand(cmnd[0]);
            this.parseSwitches(cmnd[1]);
        }
    }

    /**
     * Parse switch values.
     */
    protected void parseSwitches (final String switches) throws ParserException {
        // 1. Split by - or --
        // 2. Arrays will contain switch name followed by value string
        // 3. For each array element check if allow switch name, then store
        // in values table. If switch not allowed then throw exception
        // 4. Think about how to handle value containing - or --. Maybe allow
        // 5. escape character '\'...
        this.checkCommandNonNull();
        this.addSwitchValues(switches);
    }

    /**
     * Recursively adds switch keys and values to command switch map.
     * 
     * @param values
     *            A string containing the switches and values
     * @throws ParserException
     */
    protected void addSwitchValues (final String values) throws ParserException {
        // recursion base case. Quit when values string doesn't contain any
        // hyphens
        if (!values.contains("-")) {
            return;
        }
        int startIdx = values.indexOf("-");
        int endIdx = values.indexOf("-", startIdx + 2);
        endIdx = (endIdx < 0) ? values.length() : endIdx;
        String switchValue = values.substring(startIdx, endIdx);
        String remainder = values.substring(endIdx);

        String[] keyValues = switchValue.split(" ", 2);
        String key = this.trimSwitchHyphens(keyValues[0]);
        String value = keyValues.length > 1 ? keyValues[1].trim() : null;

        if (!this.command.addSwitchValue(key, value)) {
            throw new ParserException("Undefined switch [" + key + "]");
        }

        this.addSwitchValues(remainder);
    }

    /**
     * Remove hyphen from switch names.
     * 
     * @param raw
     *            Switch name
     * @return Switch without hyphen
     * @throws ParserException
     */
    protected String trimSwitchHyphens (final String raw)
            throws ParserException {
        String switchRegex = "[A-Za-z1-9]";

        if (raw.startsWith("--") && raw.length() > 2
                && raw.substring(2, 3).matches(switchRegex)) {
            return raw.substring(2).trim();
        } else if (raw.startsWith("-") && raw.length() == 2
                && raw.substring(1).matches(switchRegex)) {
            return raw.substring(1).trim();
        }
        throw new ParserException("Undefined switch [" + raw + "]");
    }

    /**
     * Set the current command being parsed.
     * 
     * @return void
     */
    private void setCurrentCommand (String command) throws ParserException {
        if (command.length() == 1) {
            for (Command cmnd : this.commands.values()) {
                if (cmnd.getAlias().equals(command.charAt(0))) {
                    command = cmnd.getName();
                    break;
                }
            }
        }

        this.command = this.commands.get(command);
        if (null == this.command && !command.isEmpty()) {
            throw new UnknownCommandException();
        }
    }

    /**
     * Add a command to the list of commands this parser can handle.
     * 
     * @param name
     *            Name of the command
     * @param alias
     *            Alias of the command
     * @return The command added
     */
    public Command addCommand (final String name, final char alias)
            throws ParserException {
        Command command = new Command(name, alias);
        Command previous = this.commands.put(name, command);
        if (previous != null) {
            // TODO also check if the short form name has also been defined.
            throw new ParserException("A command with the name [" + previous
                    + "] already exists");
        }
        return command;
    }

    /**
     * Add a command switch.
     * 
     * @param command
     * @param shortForm
     * @param longForm
     * @return
     */
    public Command addCommandSwitch (final Command command,
            final char shortForm, final String longForm) {
        command.addSwitch(shortForm, longForm);
        return command;
    }

    /**
     * Returns true if the named command is actually the current parsed command.
     * If no commands have been parsed then this method will throw the named
     * exception.
     * 
     * @param name
     *            Name of the command
     * @return True if this is the named exception, false otherwise.
     * @throws ParserException
     */
    public boolean isCommand (final String name) throws ParserException {
        this.checkCommandNonNull();
        return name.equals(this.command.toString());
    }

    /**
     * Get the value of a named switch.
     * 
     * @param name
     *            Name of switch
     * @return Value of switch
     * @throws ParserException
     */
    public String getSwitchValue (String name) throws ParserException {
        this.checkCommandNonNull();
        return this.command.getSwitchValue(name);
    }

    /**
     * Get the value of a switch as an integer.
     * 
     * @param name
     *            Name of the switch
     * @return Value as int
     */
    public int getSwitchValueAsInt (String name) throws ParserException {
        String value = this.getSwitchValue(name);
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException nfe) {
            throw new ParserException("Invalid integer value: " + value);
        }
    }

    /**
     * Make sure that current command is not null else throw named exception.
     * 
     * @throws ParserException
     */
    protected void checkCommandNonNull () throws ParserException {
        if (this.command == null) {
            throw new ParserException("Current command has not been "
                    + "defined and can not be parsed");
        }
    }

}
