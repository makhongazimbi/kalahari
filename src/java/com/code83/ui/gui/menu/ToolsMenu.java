package com.code83.ui.gui.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.code83.ui.gui.commands.CommandRegister;
import com.code83.ui.gui.themes.Icons;
import com.code83.ui.gui.themes.Theme;
import com.code83.ui.gui.themes.ThemeFactory;

/**
 * The "Tools" menu option.
 *
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version $Id: ToolsMenu.java 874 2012-05-10 16:56:46Z mngazimb $
 * @since 0.1
 * @see MenuBar
 */
public class ToolsMenu extends JMenu {

    /**
     * Logger.
     */
    private Logger logger = LoggerFactory.getLogger(ToolsMenu.class);

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -2442293705078073321L;

    /**
     * Constructor.
     * @param name Menu item name
     */
    public ToolsMenu (String name) {
        super(name);

        MenuItemEventListener listener = new MenuItemEventListener();

        ThemeFactory theme = Theme.instance().getThemeFactory();
        Icons icons = theme.getIcons();

        JMenuItem transfers = new JMenuItem("Transfers", new ImageIcon(icons
                .SHARING_ICON_16x16()));
        transfers.addActionListener(listener);
        transfers.setName("transfers");
        this.add(transfers);

        JMenuItem filespace = new JMenuItem("Filespace", new ImageIcon(icons
                .FOLDER_ICON_16x16()));
        filespace.addActionListener(listener);
        filespace.setName("filespace");
        this.add(filespace);

        //JMenuItem p2p = new JMenuItem("P2P Network", new ImageIcon(icons
        //        .P2P_ICON_16x16()));
        //p2p.addActionListener(listener);
        //p2p.setName("p2p");
        //this.add(p2p);

        this.addSeparator();

        //JMenuItem advanced_search = new JMenuItem("Advanced Search...",
        //        new ImageIcon(icons.SEARCH_ICON_16x16()));
        //advanced_search.addActionListener(listener);
        //advanced_search.setName("search");
        //this.add(advanced_search);

        //this.addSeparator();

        JMenuItem settings = new JMenuItem("Configure...", new ImageIcon(icons
                .CONFIGURE_ICON_16x16()));
        settings.addActionListener(listener);
        settings.setName("configure");
        this.add(settings);

        //JMenuItem reset = new JMenuItem("Reset Defaults");
        //reset.addActionListener(listener);
        //reset.setName("reset");
        //this.add(reset);
    }

    /**
     * An inner class that processes events on the menu items.
     */
    private class MenuItemEventListener implements ActionListener {

        /**
         * Perform an action when a menu item is clicked.
         * @param e Action event
         */
        public void actionPerformed (ActionEvent e) {
            CommandRegister commands = CommandRegister.instance();
            String id = ((JMenuItem) e.getSource()).getName();

            if (id.equals("transfers")) {
                ToolsMenu.this.logger.debug("Transfers menu item clicked");
                commands.getCommand("show_transfers").execute();
            } else if (id.equals("configure")) {
                ToolsMenu.this.logger.debug("Configure menu item clicked. NOT YET IMPLEMENTED");
            //} else if (id.equals("defaults")) {
            //    System.out.println("defaults");
            //} else if (id.equals("search")) {
            //    commands.getCommand("show_search").execute();
            //} else if (id.equals("p2p")) {
            //    commands.getCommand("show_p2p").execute();
            } else if (id.equals("filespace")) {
                ToolsMenu.this.logger.debug("Filespace menu item clicked");
                commands.getCommand("show_filespace").execute();
            }
        }
    }
}
