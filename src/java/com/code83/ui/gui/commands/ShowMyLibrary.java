package com.code83.ui.gui.commands;

import javax.swing.SwingUtilities;

import com.code83.ui.gui.GuiFrame;
import com.code83.ui.gui.panels.MainPanel;
import com.code83.ui.gui.panels.MyLibrary;


/**
 * Show My Library command.
 * 
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version $Id: ShowMyLibrary.java 865 2011-12-15 03:35:16Z mngazimb $
 * @since 0.1
 */
public class ShowMyLibrary implements Command {
    private final MainPanel panel;

    /**
     * Constructor.
     * 
     * @param pnl
     *            Mainpanel container.
     */
    public ShowMyLibrary (MainPanel pnl) {
        this.panel = pnl;
    }

    /**
     * Execute command.
     */
    public void execute () {
        final Runnable command = new Runnable() {
            public void run () {
                ShowMyLibrary.this.panel.switchPanel(new MyLibrary());
                GuiFrame.instance().validate();
            }
        };

        Thread dispatchThread = new Thread() {
            @Override
            public void run () {
                try {
                    SwingUtilities.invokeAndWait(command);
                } catch (Exception e) {
                    // TODO log
                    e.printStackTrace();
                }
            }
        };
        dispatchThread.start();
    }

}
