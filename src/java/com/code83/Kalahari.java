package com.code83;
import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.code83.data.accessLayer.DataAccessObject;
import com.code83.modules.auxilliary.ShutdownHook;
import com.code83.modules.communication.Listener;
import com.code83.modules.communication.Responder;
import com.code83.modules.status.Status;
import com.code83.ui.cli.CommandLine;
import com.code83.ui.gui.GuiFrame;
import com.code83.ui.gui.GuiFrame.LookAndFeels;
import com.code83.utils.Splash;

/**
 * The main controller class for Kalahari.
 *
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version 0.1 SVN: $Id: Kalahari.java 903 2012-09-14 22:10:04Z mngazimb $
 * @since 0.1
 */
public final class Kalahari {

    /**
     * Logger.
     */
    private static Logger logger
            = LoggerFactory.getLogger(Kalahari.class);

    /**
     * Default constructor.
     */
    private Kalahari () {
    }

    /**
     * Main program entry point.
     * @param args Arguments array
     */
    public static void main (String[] args) {
        Kalahari.logger.debug("My Sender ID: [" + Status.getInstance()
                .getNomadStatus().getNomadId() + "]");

        boolean cmdline = false;
        if (args.length > 0 && args[0].equals("cmd")) {
            cmdline = true;
        }

        /* start status updater to show what's going on */
        // Status status = Status.instance();
        // status.setStatus("Loading status updater...");
        /* show splash */
        Splash splash = Splash.instance();
        if (!cmdline) {
            splash.display();
        }

        /* add shutdown hook */
        // status.setStatus("Loading shutdown hook...");
        ShutdownHook hook = new ShutdownHook();
        hook.addShutdownObject(Responder.instance());
        hook.addShutdownObject(Listener.instance());
        hook.addShutdownObject(new DataAccessObject());
        Runtime.getRuntime().addShutdownHook(hook);

        /* start file tracker */
        // status.setStatus("Loading file tracker...");
        /* start listener */
        // status.setStatus("Loading listener...");
        Listener listener = Listener.instance();
        listener.start();

        /* start responder (heart beat thread) */
        // status.setStatus("Loading responder...");
        Responder responder = Responder.instance();
        responder.start();

        /* start UI */
        // status.setStatus("Loading user interface...");
        if (cmdline) {
            Kalahari.logger.info("Starting Kalahari in command line mode");
            CommandLine cl = new CommandLine();
            cl.start();
        } else {
            Kalahari.logger.info("Starting Kalahari in GUI mode");
            // TODO how to handle popup display time?
            // Kalahari.pause(1000);
            splash.close();
            // Kalahari.pause(500);

            SwingUtilities.invokeLater(new Runnable() {
                public void run () {
                    GuiFrame.setLookAndFeel(LookAndFeels.NIMBUS);
                    GuiFrame frame = GuiFrame.instance();
                    frame.setVisible(true);
                }
            });
        }
    }

}
