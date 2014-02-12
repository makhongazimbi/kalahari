package com.code83.ui.gui.panels;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.code83.ui.gui.commands.CommandRegister;
import com.code83.ui.gui.commands.StatusUpdate;

/**
 * A thread responsible for updating the status panel.
 *
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version $Id: StatusUpdateThread.java 905 2012-09-14 22:49:26Z mngazimb $
 * @since 0.1
 */
public class StatusUpdateThread extends Thread {
    /**
     * FIXME put in config file
     */
    private static final int REFRESH_PERIOD = 5000;
    /**
     * Logger.
     */
    private Logger logger = LoggerFactory
            .getLogger(StatusUpdateThread.class);

    /**
     * Constructor.
     */
    public StatusUpdateThread (StatusPanel panel) {
        super("K Status Update Thread");
        CommandRegister commands = CommandRegister.instance();
        commands.addCommand("status_update", new StatusUpdate(panel));
    }

    /**
     * Run thread.
     */
    public void run () {
        while (true) {
            try {
                this.logger.debug("Status update thread running");
                CommandRegister commands = CommandRegister.instance();
                commands.getCommand("status_update").execute();
                Thread.sleep(REFRESH_PERIOD);
            } catch (InterruptedException e) {
                e.printStackTrace();
                this.logger.error("Thread sleep error occurred", e);
            }
        }
    }
}
