package com.code83.modules.auxilliary;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.code83.ui.gui.commands.Command;
import com.code83.ui.gui.commands.CommandRegister;


/**
 * This is a shutdown hook class. It performs all actions required if Kalahari
 * shuts down for any reason.
 * 
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version 0.1 SVN: $Id: ShutdownHook.java 865 2011-12-15 03:35:16Z mngazimb $
 * @since 0.1
 */
public class ShutdownHook extends Thread {

    /**
     * A collection of objects that need to be shutdown.
     */
    private final List<Shutdown> objs = new ArrayList<Shutdown>();

    /**
     * Logger.
     */
    private final Logger logger = LoggerFactory.getLogger(ShutdownHook.class);

    /**
     * Constructor.
     */
    public ShutdownHook () {
        super("Shutdown Hook");
    }

    /**
     * Add an object to the list of objects that need to be shutdown.
     * 
     * @param obj
     *            Object to be shutdown.
     */
    public void addShutdownObject (Shutdown obj) {
        this.objs.add(obj);
    }

    /**
     * Tasks required to be performed during shutdown will be executed here.
     */
    @Override
    public void run () {
        this.logger.info("Shutting down Kalahari");

        CommandRegister commands = CommandRegister.instance();
        Command windowDimmensions = commands
                .getCommand("save_window_dimensions");
        if (windowDimmensions != null) {
            windowDimmensions.execute();
        }

        for (Shutdown obj : this.objs) {
            obj.shutdown();
        }
    }

    public static void main (String[] args) {
        ShutdownHook hook = new ShutdownHook();
        Runtime.getRuntime().addShutdownHook(hook);

        System.out.println("Starting Kalahari...");
        /* some task that will run for a while */
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
