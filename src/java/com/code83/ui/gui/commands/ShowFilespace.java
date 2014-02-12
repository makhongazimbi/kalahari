package com.code83.ui.gui.commands;

import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.code83.ui.gui.GuiFrame;
import com.code83.ui.gui.panels.Filespace;
import com.code83.ui.gui.panels.MainPanel;


/**
 * Show filespace command.
 * 
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version $Id: ShowFilespace.java 865 2011-12-15 03:35:16Z mngazimb $
 * @since 0.1
 */
public class ShowFilespace implements Command {

    /**
     * Logger.
     */
    private final Logger logger = LoggerFactory.getLogger(ShowFilespace.class);

    /**
     * Main container panel.
     */
    private final MainPanel panel;

    /**
     * Constructor.
     * 
     * @param pnl
     */
    public ShowFilespace (MainPanel pnl) {
        this.panel = pnl;
    }

    /**
     * Execute command.
     */
    public void execute () {
        final Runnable command = new Runnable() {
            public void run () {
                ShowFilespace.this.panel.switchPanel(new Filespace());
                GuiFrame.instance().validate();
            }
        };

        Thread dispatchThread = new Thread() {
            @Override
            public void run () {
                try {
                    SwingUtilities.invokeAndWait(command);
                } catch (Exception e) {
                    ShowFilespace.this.logger.error(
                            "An error occurred switching panels", e);
                }
            }
        };
        dispatchThread.start();
    }

}
