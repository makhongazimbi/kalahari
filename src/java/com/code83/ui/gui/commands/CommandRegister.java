package com.code83.ui.gui.commands;

import java.util.Hashtable;
import java.util.Map;

/**
 * A class that stores command objects.
 * 
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version $Id: CommandRegister.java 865 2011-12-15 03:35:16Z mngazimb $
 * @since 0.1
 * @see Command
 *
 */
public class CommandRegister {
    private static CommandRegister commandregister = null;
    private Map<String, Command> commands;

    private CommandRegister () {
        this.commands = new Hashtable<String, Command>();
    }

    public static CommandRegister instance () {
        if (commandregister == null) {
            commandregister = new CommandRegister();
        }
        return commandregister;
    }

    public boolean addCommand (final String id, final Command command) {
        if (this.commands.containsKey(id)) {
            return false;
        } else {
            this.commands.put(id, command);
        }

        return true;
    }

    public Command getCommand (final String id) {
        return this.commands.get(id);
    }

}
