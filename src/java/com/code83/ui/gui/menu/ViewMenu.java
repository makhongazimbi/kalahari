package com.code83.ui.gui.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import com.code83.ui.gui.commands.Command;
import com.code83.ui.gui.commands.CommandRegister;

/**
 * The "View" menu option.
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version $Id: ViewMenu.java 873 2012-05-09 23:30:38Z mngazimb $
 * @since 0.1
 * @see MenuBar
 */
public class ViewMenu extends JMenu {

    private static final long serialVersionUID = 4215522239920708787L;
    private JMenuItem downloads;
    private JMenuItem sidebar;

    /**
     * View menu constructor.
     */
    public ViewMenu (String name) {
        super(name);

        MenuItemEventListener listener = new MenuItemEventListener();

        //ThemeFactory theme = Theme.instance().getThemeFactory();
        //Icons icons = theme.getIcons();

        //JMenuItem themes = new JMenuItem("Themes...",
        //        new ImageIcon(icons.THEMES_ICON_16x16()));
        //themes.setName("themes");
        //themes.addActionListener(listener);
        //this.add(themes);

        //this.addSeparator();

        this.sidebar = new JMenuItem("Hide Sidebar");
        this.sidebar.setName("hide-sidebar");
        this.sidebar.addActionListener(listener);
        this.add(this.sidebar);

        this.downloads = new JMenuItem("Hide Transfers Tray");
        this.downloads.setName("hide-downloads");
        this.downloads.addActionListener(listener);
        this.add(this.downloads);

        //CommandRegister commands = CommandRegister.instance();
        //commands.addCommand("choose_themes", new ChooseTheme());
    }

    /**
     * An inner class that processes events on the menu items.
     */
    private class MenuItemEventListener implements ActionListener {

        /**
         * Perform specified  action where menu item is clicked.
         */
        public void actionPerformed (ActionEvent e) {
            CommandRegister commands = CommandRegister.instance();
            String id = ((JMenuItem) e.getSource()).getName();

            if (id.equalsIgnoreCase("hide-downloads")) {
                ViewMenu.this.downloads.setText("Show Transfers Tray");
                ViewMenu.this.downloads.setName("show-downloads");
                Command hide = commands.getCommand("hide_download_tray");
                hide.execute();
            } else if (id.equalsIgnoreCase("show-downloads")) {
                ViewMenu.this.downloads.setText("Hide Transfers Tray");
                ViewMenu.this.downloads.setName("hide-downloads");
                Command show = commands.getCommand("show_download_tray");
                show.execute();
            } else if (id.equalsIgnoreCase("hide-sidebar")) {
                ViewMenu.this.sidebar.setText("Show Sidebar");
                ViewMenu.this.sidebar.setName("show-sidebar");
                Command hide = commands.getCommand("hide_sidebar");
                hide.execute();
            } else if (id.equalsIgnoreCase("show-sidebar")) {
                ViewMenu.this.sidebar.setText("Hide Sidebar");
                ViewMenu.this.sidebar.setName("hide-sidebar");
                Command show = commands.getCommand("show_sidebar");
                show.execute();
            //} else if (id.equalsIgnoreCase("themes")) {
            //    Command show = commands.getCommand("choose_themes");
            //    show.execute();
            }
        }
    }
}
