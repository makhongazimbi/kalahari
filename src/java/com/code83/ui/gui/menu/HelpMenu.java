package com.code83.ui.gui.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.code83.ui.gui.commands.CommandRegister;
import com.code83.ui.gui.commands.OpenAboutWindow;
import com.code83.ui.gui.themes.Icons;
import com.code83.ui.gui.themes.Theme;
import com.code83.ui.gui.themes.ThemeFactory;
import com.code83.utils.LaunchBrowser;
import com.code83.utils.Urls;

/**
 * The "Help" menu option.
 *
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version $Id: HelpMenu.java 873 2012-05-09 23:30:38Z mngazimb $
 * @since 0.1
 * @see MenuBar
 */
public class HelpMenu extends JMenu {

    /**
     * Logger.
     */
    private Logger logger = LoggerFactory.getLogger(HelpMenu.class);

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -3033438404090882676L;

    /**
     * Help menu constructor.
     * @param Menu name
     */
    public HelpMenu (String name) {
        super(name);

        MenuItemEventListener listener = new MenuItemEventListener();

        ThemeFactory theme = Theme.instance().getThemeFactory();
        Icons icons = theme.getIcons();

        //JMenuItem help = new JMenuItem("Help Contents",
        //        new ImageIcon(icons.HELP_ICON_16x16()));
        //help.setName("help");
        //help.addActionListener(listener);
        //this.add(help);

        //JMenuItem faq = new JMenuItem("FAQ...",
        //        new ImageIcon(icons.FAQ_ICON_16x16()));
        //faq.setName("faq");
        //faq.addActionListener(listener);
        //this.add(faq);

        //JMenuItem bug = new JMenuItem("Report a Bug",
        //        new ImageIcon(icons.BUG_ICON_16x16()));
        //bug.setName("bug_report");
        //bug.addActionListener(listener);
        //this.add(bug);

        //this.addSeparator();

        //JMenuItem release = new JMenuItem("Release Notes");
        //release.setName("release");
        //release.addActionListener(listener);
        //this.add(release);

        //JMenuItem updates = new JMenuItem("Check for Updates...");
        //updates.setName("updates");
        //updates.addActionListener(listener);
        //this.add(updates);

        //JMenuItem online = new JMenuItem("Get Help Online...");
        //online.setName("online_help");
        //online.addActionListener(listener);
        //this.add(online);

        //this.addSeparator();

        JMenuItem about = new JMenuItem("About Kalahari",
                new ImageIcon(icons.INFO_ICON_16x16()));
        about.setName("about");
        about.addActionListener(listener);
        this.add(about);

        CommandRegister commands = CommandRegister.instance();
        commands.addCommand("open_about_window", new OpenAboutWindow());
    }

    /**
     * An inner class that processes events on the menu items.
     */
    private class MenuItemEventListener implements ActionListener {

        /**
         * Perform an action when a help menu item is clicked.
         * @param e Action event
         */
        public void actionPerformed (ActionEvent e) {
            String id = ((JMenuItem) e.getSource()).getName();
            CommandRegister commands = CommandRegister.instance();

            //if (id.equals("bug_report")) {
            //    LaunchBrowser.openURL(Urls.BUG_REPORT);
            //} else if (id.equals("online_help")) {
            //    LaunchBrowser.openURL(Urls.ONLINE_HELP);
            //} else if (id.equals("help")) {
            //    System.out.println("help");
            //} else if (id.equals("about")) {
            if (id.equals("about")) {
                HelpMenu.this.logger.debug("Help menu 'about' option clicked");
                commands.getCommand("open_about_window").execute();
            //} else if (id.equals("faq")) {
            //    System.out.println("FAQ");
            }
        }
    }
}
