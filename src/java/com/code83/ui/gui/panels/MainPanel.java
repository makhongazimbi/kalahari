package com.code83.ui.gui.panels;

import java.awt.GridLayout;

import javax.swing.JPanel;

import com.code83.ui.gui.commands.CommandRegister;
import com.code83.ui.gui.commands.GoHome;
import com.code83.ui.gui.commands.ShowFilespace;
import com.code83.ui.gui.commands.ShowMyLibrary;
import com.code83.ui.gui.commands.ShowP2pPanel;
import com.code83.ui.gui.commands.ShowSearchTabs;
import com.code83.ui.gui.commands.ShowSearchWindow;
import com.code83.ui.gui.commands.ShowTransfers;


/**
 * Main display container panel for GUI.
 * 
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version $Id: MainPanel.java 865 2011-12-15 03:35:16Z mngazimb $
 * @since 0.1
 */
public class MainPanel extends JPanel {

    private static final long serialVersionUID = -8567624731508970155L;
    private JPanel currentpanel;

    /**
     * Constructor.
     */
    public MainPanel () {
        this.setLayout(new GridLayout(1, 1));
        this.currentpanel = new Home();
        this.add(this.currentpanel);

        CommandRegister commands = CommandRegister.instance();
        commands.addCommand("my_library", new ShowMyLibrary(this));
        commands.addCommand("show_p2p", new ShowP2pPanel(this));
        commands.addCommand("go_home", new GoHome(this));
        commands.addCommand("search_screen", new ShowSearchTabs(this));
        commands.addCommand("show_search", new ShowSearchWindow(this));
        commands.addCommand("show_transfers", new ShowTransfers(this));
        commands.addCommand("show_filespace", new ShowFilespace(this));
    }

    /**
     * Switch panel being displayed.
     * 
     * @param panel
     *            Panel to add to main container panel.
     */
    public synchronized void switchPanel (JPanel panel) {
        this.remove(this.currentpanel);
        this.add(panel);
        this.currentpanel = panel;
        this.currentpanel.invalidate();
    }
}
