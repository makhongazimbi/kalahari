package com.code83.ui.gui.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import com.code83.ui.gui.commands.CommandRegister;
import com.code83.ui.gui.themes.Colors;
import com.code83.ui.gui.themes.Icons;
import com.code83.ui.gui.themes.Theme;
import com.code83.ui.gui.themes.ThemeFactory;


/**
 * Sidebar GUI panel.
 * 
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version $Id: SideBarPanel.java 865 2011-12-15 03:35:16Z mngazimb $
 * @since 0.1
 */
public class SideBarPanel extends JPanel {

    private static final long serialVersionUID = -5026935518370384977L;
    private static final int SIDE_BAR_WIDTH = 180;
    private static final int SIDE_BAR_ITEM_HEIGHT = 30;

    /**
     * Constructor.
     */
    public SideBarPanel () {
        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(layout);

        ThemeFactory theme = Theme.instance().getThemeFactory();
        Colors colors = theme.getColors();
        Icons icons = theme.getIcons();

        Color color = colors.SIDEBAR_PANEL_BORDER();
        Border border = BorderFactory.createMatteBorder(0, 0, 0, 1, color);
        this.setBorder(border);
        this.setBackground(colors.SIDEBAR_PANEL_BACKGROUND());

        ItemListener listener = new ItemListener();

        /*
         * JPanel search = new SideBarItem(icons.SEARCH_ICON_16x16(), "Search");
         * search.addMouseListener(listener); search.setName("search");
         * search.setToolTipText("View search options"); this.add(search);
         */

        JPanel transfers = new SideBarItem(icons.SHARING_ICON_16x16(),
                "Transfers");
        transfers.addMouseListener(listener);
        transfers.setName("transfers");
        transfers.setToolTipText("View uploads and downloads in progress");
        this.add(transfers);

        // JPanel library = new SideBarItem(icons.FOLDER_ICON_16x16(),
        // "My Library");
        // library.addMouseListener(listener);
        // library.setName("library");
        // library.setToolTipText("View files in your library");
        // this.add(library);
        //
        // JPanel p2p = new SideBarItem(icons.P2P_ICON_16x16(), "P2P Network");
        // p2p.addMouseListener(listener);
        // p2p.setName("p2p");
        // p2p.setToolTipText("View your local peer to peer network");
        // this.add(p2p);

        JPanel space = new SideBarItem(icons.FOLDER_ICON_16x16(), "Filespace");
        space.addMouseListener(listener);
        space.setName("filespace");
        space.setToolTipText("View files you are sharing");
        this.add(space);
    }

    /**
     * Sidebar item object.
     * 
     * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
     */
    private class SideBarItem extends JPanel {

        private static final long serialVersionUID = -4165509682293659616L;

        /**
         * Constructor.
         * 
         * @param icon
         *            Name of icon to represent sidebar item
         * @param text
         *            Display text of the icon
         */
        public SideBarItem (String icon, String text) {
            ThemeFactory theme = Theme.instance().getThemeFactory();
            Colors colors = theme.getColors();
            this.setBackground(colors.SIDEBAR_PANEL_BACKGROUND());
            this.setMaximumSize(new Dimension(SideBarPanel.SIDE_BAR_WIDTH,
                    SideBarPanel.SIDE_BAR_ITEM_HEIGHT));
            this.setLayout(new FlowLayout(FlowLayout.LEFT));
            JLabel img = new JLabel(new ImageIcon(icon));
            JLabel txt = new JLabel(text);
            this.add(img);
            this.add(txt);
        }

    }

    /**
     * Listener for clicked menu choices in the sidebar.
     * 
     * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
     * 
     */
    private class ItemListener implements MouseListener {

        public void mouseClicked (MouseEvent e) {
            CommandRegister commands = CommandRegister.instance();
            String id = ((JPanel) e.getSource()).getName();
            if (id.equals("library")) {
                commands.getCommand("my_library").execute();
            } else if (id.equals("p2p")) {
                commands.getCommand("show_p2p").execute();
            } else if (id.equals("transfers")) {
                commands.getCommand("show_transfers").execute();
            } else if (id.equals("search")) {
                commands.getCommand("show_search").execute();
            } else if (id.equals("filespace")) {
                commands.getCommand("show_filespace").execute();
            }
        }

        public void mouseEntered (MouseEvent e) {
            ThemeFactory theme = Theme.instance().getThemeFactory();
            Colors colors = theme.getColors();
            JPanel sideBarItem = (JPanel) e.getSource();
            sideBarItem.setBackground(colors.SIDEBAR_ITEM_HIGHLIGHT());
        }

        public void mouseExited (MouseEvent e) {
            JPanel sideBarItem = (JPanel) e.getSource();
            ThemeFactory theme = Theme.instance().getThemeFactory();
            Colors colors = theme.getColors();
            sideBarItem.setBackground(colors.SIDEBAR_PANEL_BACKGROUND());
        }

        public void mousePressed (MouseEvent e) {
        }

        public void mouseReleased (MouseEvent e) {
        }

    }
}
