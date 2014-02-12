package com.code83.ui.gui.commands;

import com.code83.ui.gui.GuiFrame;
import com.code83.ui.gui.panels.MainPanel;
import com.code83.ui.gui.panels.SearchTabs;
import com.code83.utils.RealTimeSwing;


/**
 * ShowSearchTabs.java 748 2010-04-02 14:30:47Z mngazimb $
 *
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version $Id: ShowSearchTabs.java 892 2012-09-13 21:27:48Z mngazimb $
 * @since 0.1
 */
public class ShowSearchTabs implements Command {

    private MainPanel panel;

    public ShowSearchTabs (MainPanel main) {
        panel = main;
    }

    public void execute () {
        final Runnable command = new Runnable () {
            public void run () {
                SearchTabs tabs = SearchTabs.instance();
                tabs.showTabs();
                panel.switchPanel(tabs);
                GuiFrame.instance().validate();
            }
        };
        RealTimeSwing.invokeNow(command);
    }

}
