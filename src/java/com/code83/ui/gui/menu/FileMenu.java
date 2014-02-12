package com.code83.ui.gui.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import com.code83.ui.gui.commands.CommandRegister;
import com.code83.ui.gui.themes.Icons;
import com.code83.ui.gui.themes.Theme;
import com.code83.ui.gui.themes.ThemeFactory;

/**
 * The "File" menu option.
 *
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version $Id: FileMenu.java 873 2012-05-09 23:30:38Z mngazimb $
 * @since 0.1
 * @see MenuBar
 */
public class FileMenu extends JMenu {

    private static final long serialVersionUID = -4255999446644203905L;
    private JCheckBoxMenuItem invisible;
    //private Command addfile;
    //private Command addfolder;

    /**
     * File menu constructor.
     * @param name Menu name
     */
    public FileMenu (String name) {
        super(name);
        //this.addfile = new AddFile(this);
        //this.addfolder = new AddFolder(this);

        MenuItemEventListener listener = new MenuItemEventListener();

        //JMenuItem library = new JMenuItem("My Library...");
        //library.setName("library");
        //library.addActionListener(listener);
        //this.add(library);

        //JMenu recent_downloads = new JMenu("Recent Downloads");
        //recent_downloads.setName("recent_dloads");
        //recent_downloads.addActionListener(listener);

        //JMenuItem downloads = new JMenuItem("(empty)");
        //recent_downloads.add(downloads);
        //downloads.setEnabled(false);
        //this.add(recent_downloads);

        //this.invisible = new JCheckBoxMenuItem("Invisible");
        //this.invisible.setName("invisible");
        //this.invisible.addActionListener(listener);
        //this.add(this.invisible);

        //this.addSeparator();

        ThemeFactory theme = Theme.instance().getThemeFactory();
        Icons icons = theme.getIcons();

        //JMenuItem add_file = new JMenuItem("Add File to Library...",
        //        new ImageIcon(icons.FILE_ICON_16x16()));
        //add_file.setName("add_file");
        //add_file.addActionListener(listener);
        //this.add(add_file);

        //JMenuItem add_folder = new JMenuItem("Add Folder to Library...",
        //        new ImageIcon(icons.FOLDER_ICON_16x16()));
        //add_folder.setName("add_folder");
        //add_folder.addActionListener(listener);
        //this.add(add_folder);

        //this.addSeparator();

        //JMenuItem close = new JMenuItem("Close Window",
        //        new ImageIcon(icons.CLOSE_ICON_16x16()));
        //close.setName("close");
        //close.addActionListener(listener);
        //this.add(close);

        //JMenuItem quit_after = new JMenuItem("Exit After Transfers");
        //quit_after.setName("exit_after_transfers");
        //quit_after.addActionListener(listener);
        //this.add(quit_after);

        JMenuItem quit = new JMenuItem("Exit",
                new ImageIcon(icons.EXIT_ICON_16x16()));
        quit.setName("exit");
        quit.addActionListener(listener);
        this.add(quit);
    }

    /**
     * An inner class that processes events on the menu items.
     */
    private class MenuItemEventListener implements ActionListener {

        /**
        * Perform actions specified when menu item is clicked.
        * @param e Action event
        */
        public void actionPerformed (ActionEvent e) {
            //CommandRegister commands = CommandRegister.instance();
            String id = ((JMenuItem) e.getSource()).getName();

            //if (id.equals("recent_dloads")) {
            //    System.out.println("Recent downloads");
            //} else if (id.equals("invisible")) {
            //    FileMenu.this.invisible.setName("visible");
            //    commands.getCommand("invisible_mode").execute();
            //    commands.getCommand("show_invisible_banner").execute();
            //} else if (id.equals("visible")) {
            //    FileMenu.this.invisible.setName("invisible");
            //    commands.getCommand("visible_mode").execute();
            //    commands.getCommand("hide_invisible_banner").execute();
            //} else if (id.equals("add_file")) {
            //    FileMenu.this.addfile.execute();
            //} else if (id.equals("add_folder")) {
            //    FileMenu.this.addfolder.execute();
            //} else if (id.equals("exit_after_transfers")) {
            //    System.exit(0);
            //} else if (id.equals("exit")) {
            if (id.equals("exit")) {
                System.exit(0);
            //} else if (id.equals("library")) {
            //    commands.getCommand("my_library").execute();
            //} else if (id.equals("close")) {
            //    GuiFrame.instance().reloadGui();
            }
        }

    }
}
