package com.code83.ui.gui.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import com.code83.modules.filemods.FileDescriptor;
import com.code83.modules.filemods.FileSearch;
import com.code83.ui.gui.commands.Command;
import com.code83.ui.gui.commands.CommandRegister;
import com.code83.ui.gui.themes.Colors;
import com.code83.ui.gui.themes.Icons;
import com.code83.ui.gui.themes.Theme;
import com.code83.ui.gui.themes.ThemeFactory;


/**
 * Search input bar.
 *
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version $Id: SearchBar.java 892 2012-09-13 21:27:48Z mngazimb $
 * @since 0.1
 */
public class SearchBar extends JPanel {

    private static final long serialVersionUID = 6530804362140978544L;
    // private static final String[] types = { "All", "Audio", "Video",
    // "Images",
    // "Documents", "Programs" };
    private final JTextField search = new JTextField();

    // private JComboBox searchtype;

    public SearchBar () {
        ThemeFactory theme = Theme.instance().getThemeFactory();
        Colors colors = theme.getColors();
        Icons icons = theme.getIcons();

        this.setBackground(colors.SEARCH_PANEL_BACKGROUND());
        Color color = colors.SEARCH_PANEL_BORDER();
        Border border = BorderFactory.createMatteBorder(0, 0, 1, 0,
                color);
        this.setBorder(border);
        this.setLayout(new FlowLayout(FlowLayout.LEFT));

        /* add home icon */
        JLabel home = new JLabel(
                new ImageIcon(icons.HOME_ICON_32x32()));
        home.setName("home");
        home.addMouseListener(new SearchPanelListener());

        /* search file type */
        // SearchListRenderer renderer = new SearchListRenderer();
        // renderer.setPreferredSize(new Dimension(80, 22));

        // this.searchtype = new JComboBox(types);
        // this.searchtype.setRenderer(renderer);

        /* add search field */
        this.search.setColumns(15);
        this.search.addKeyListener(new SearchKeyListener());

        /* add search button */
        JButton searchbutton = new JButton("Search");
        searchbutton.requestFocusInWindow();
        searchbutton.addActionListener(new SearchButtonListener());

        this.add(home);

        /* add some spacing */
        this.add(Box.createRigidArea(new Dimension(110, 0)));
        // this.add(this.searchtype);
        this.add(this.search);
        this.add(searchbutton);
    }

    /**
     * Execute a file search if something has been entered in the
     * search textbox.
     */
    private void runSearch () {
        String searchString = this.search.getText();

        if (searchString.length() > 0) {
            // int selectedIndex = this.searchtype.getSelectedIndex();
            String searchType = "All";
            // if (selectedIndex < SearchBar.types.length) {
            // searchType = SearchBar.types[selectedIndex];
            // }
            FileSearch fileSearch = FileSearch.instance();
            fileSearch.search(new FileDescriptor(searchString, searchType));
            CommandRegister commands = CommandRegister.instance();
            Command command = commands.getCommand("search_screen");
            command.execute();
        }
    }

    /**
     * Seach button listener.
     */
    private class SearchButtonListener implements ActionListener {

        public void actionPerformed (ActionEvent e) {
            SearchBar.this.runSearch();
        }

    }

    /**
     * Listener for search key press.
     */
    private class SearchKeyListener implements KeyListener {

        public void keyPressed (KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                SearchBar.this.runSearch();
            }
        }

        public void keyReleased (KeyEvent e) {
        }

        public void keyTyped (KeyEvent e) {
        }

    }

    private class SearchPanelListener implements MouseListener {

        public void mouseClicked (MouseEvent e) {
            CommandRegister commands = CommandRegister.instance();
            String id = ((JLabel) e.getSource()).getName();
            if (id.equals("home")) {
                commands.getCommand("go_home").execute();
            }
        }

        public void mouseEntered (MouseEvent e) {
        }

        public void mouseExited (MouseEvent e) {
        }

        public void mousePressed (MouseEvent e) {
        }

        public void mouseReleased (MouseEvent e) {
        }

    }

    // private class SearchListRenderer extends JLabel implements
    // ListCellRenderer {
    // private static final long serialVersionUID = 9152745321945501691L;
    //
    // public SearchListRenderer () {
    // this.setOpaque(false);
    // }
    //
    // public Component getListCellRendererComponent (JList list,
    // Object value, int index, boolean isSelected,
    // boolean cellHasFocus) {
    // ThemeFactory theme = Theme.instance().getThemeFactory();
    // Colors colors = theme.getColors();
    // Icons icons = theme.getIcons();
    //
    // String item = value.toString();
    // if (item.equals("All")) {
    // this.setIcon(new ImageIcon(icons.WILD_CARD_ICON_16X16()));
    // } else if (item.equals("Audio")) {
    // this.setIcon(new ImageIcon(icons.AUDIO_ICON_16X16()));
    // } else if (item.equals("Video")) {
    // this.setIcon(new ImageIcon(icons.VIDEO_ICON_16X16()));
    // } else if (item.equals("Images")) {
    // this.setIcon(new ImageIcon(icons.IMAGES_ICON_16X16()));
    // } else if (item.equals("Documents")) {
    // this.setIcon(new ImageIcon(icons.DOCUMENTS_ICON_16X16()));
    // } else if (item.equals("Programs")) {
    // this.setIcon(new ImageIcon(icons.PROGRAMS_ICON_16X16()));
    // }
    //
    // this.setText(item);
    // this.setBackground(isSelected ? colors.CELL_BKGRND_DROP_SELECTED()
    // : colors.CELL_BKGRND_DROP_NOT_SELECTED());
    // this.setForeground(isSelected ? colors.CELL_FRGRND_DROP_SELECTED()
    // : colors.CELL_FRGRND_DROP_NOT_SELECTED());
    // return this;
    // }
    // }
}
