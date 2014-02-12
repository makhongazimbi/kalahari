package com.code83.ui.gui.commands;

//import javax.swing.SwingUtilities;

import com.code83.ui.gui.GuiFrame;
import com.code83.ui.gui.panels.StatusPanel;
import com.code83.utils.RealTimeSwing;


/**
 * Command that updates status panel.
 * @author Makho Ngazimbi
 * @version $Id: StatusUpdate.java 905 2012-09-14 22:49:26Z mngazimb $
 * @since 0.1
 */
public class StatusUpdate implements Command {
    private StatusPanel panel;

    /**
     * Default constructor.
     */
    public StatusUpdate (StatusPanel pnl) {
        panel = pnl;
    }

    /**
     * Update status panel.
     */
    public void execute () {
        Runnable update = new Runnable() {
            public void run () {
                panel.update();
                GuiFrame.instance().validate();

            }
        };
        RealTimeSwing.invokeOnce("status_update", 0, null, update);
    }

}
