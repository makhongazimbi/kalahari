package com.code83.ui.gui.panels;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import com.code83.data.accessLayer.DataAccessObject;
import com.code83.data.provider.sqlite.MyLibraryProvider;
import com.code83.ui.gui.panels.forms.AudioSearchForm;
import com.code83.ui.gui.themes.Colors;
import com.code83.ui.gui.themes.Icons;
import com.code83.ui.gui.themes.Theme;
import com.code83.ui.gui.themes.ThemeFactory;
import com.code83.utils.LibraryItem;
import com.code83.utils.library.LibraryActions;

/**
 * MyLibrary view panel
 * 
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version $Id: MyLibrary.java 865 2011-12-15 03:35:16Z mngazimb $
 * @since 0.1
 */
public class MyLibrary extends JPanel {

    private static final long serialVersionUID = 5223801404958855346L;
    private final JPanel myLibraryBody = new JPanel(new GridLayout(1, 1));
    private static final int SEARCH_FIELD_COLUMNS = 15;
    private static final String SEARCH_FIELD_TEXT = "Search...";

    public MyLibrary () {
        this.setLayout(new BorderLayout());
        this.addHeader();
        this.addBody();
    }

    private void addHeader () {
        LibraryMouseListener listener = new LibraryMouseListener();
        ThemeFactory theme = Theme.instance().getThemeFactory();
        Colors colors = theme.getColors();
        Icons icons = theme.getIcons();
        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout());
        buttons.setBackground(colors.STATUS_PANEL_INVISIBLE());

        JLabel all = new JLabel(new ImageIcon(icons.WILD_CARD_ICON_16X16()));
        all.addMouseListener(listener);
        all.setToolTipText("Show all files");
        all.setName("all");

        JLabel folders = new JLabel(new ImageIcon(icons.FOLDER_ICON_16x16()));
        folders.addMouseListener(listener);
        folders.setToolTipText("Show folders only");
        folders.setName("folders");

        JLabel audio = new JLabel(new ImageIcon(icons.AUDIO_ICON_16X16()));
        audio.addMouseListener(listener);
        audio.setToolTipText("Audio files only");
        audio.setName("audio");

        JLabel video = new JLabel(new ImageIcon(icons.VIDEO_ICON_16X16()));
        video.addMouseListener(listener);
        video.setToolTipText("Video files only");
        video.setName("video");

        JLabel image = new JLabel(new ImageIcon(icons.IMAGES_ICON_16X16()));
        image.addMouseListener(listener);
        image.setToolTipText("Image files only");
        image.setName("image");

        JLabel document = new JLabel(
                new ImageIcon(icons.DOCUMENTS_ICON_16X16()));
        document.addMouseListener(listener);
        document.setToolTipText("Document files only");
        document.setName("document");

        JLabel program = new JLabel(new ImageIcon(icons.PROGRAMS_ICON_16X16()));
        program.addMouseListener(listener);
        program.setToolTipText("Program files only");
        program.setName("program");

        buttons.add(all);
        buttons.add(folders);
        buttons.add(audio);
        buttons.add(video);
        buttons.add(image);
        buttons.add(document);
        buttons.add(program);

        JPanel header = new JPanel();
        header.setLayout(new BorderLayout());
        this.setLayout(new BorderLayout());

        JPanel titleBar = new JPanel();
        titleBar.setLayout(new FlowLayout());
        JLabel title = new JLabel("My Library");
        titleBar.setBackground(colors.STATUS_PANEL_INVISIBLE());
        titleBar.add(title);

        JTextField search = new JTextField(MyLibrary.SEARCH_FIELD_TEXT);
        search.setColumns(MyLibrary.SEARCH_FIELD_COLUMNS);
        search.setName("search_field");
        search.addKeyListener(new SearchTextFieldListener());
        search.addMouseListener(new LibraryMouseListener());

        header.setBackground(colors.STATUS_PANEL_INVISIBLE());
        header.add(buttons, BorderLayout.LINE_START);
        header.add(titleBar, BorderLayout.CENTER);
        header.add(search, BorderLayout.LINE_END);

        Border border = BorderFactory.createMatteBorder(0, 0, 1, 0, colors
                .FOOTER_PANEL_BORDER());
        header.setBorder(border);
        this.add(header, BorderLayout.PAGE_START);

        JPanel searchForm = new JPanel();
        searchForm.setLayout(new GridLayout(1, 1));
        searchForm.add(new AudioSearchForm());
        this.add(searchForm, BorderLayout.CENTER);
    }

    private void addBody () {
        JTable table = this.getLibraryTable();
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        this.myLibraryBody.add(scrollPane);
        this.add(this.myLibraryBody, BorderLayout.CENTER);
    }

    protected static void clearSearchField (JTextField field) {
        if (field.getText().trim().equals(MyLibrary.SEARCH_FIELD_TEXT)) {
            field.setText("");
        }
    }

    protected static void defaultSearchField (JTextField field) {
        if (field.getText().trim().equals("")) {
            field.setText(MyLibrary.SEARCH_FIELD_TEXT);
        }
    }

    private JTable getLibraryTable () {
        DataAccessObject dao = new DataAccessObject();
        MyLibraryProvider libDao = dao.getMyLibraryDao();
        List<LibraryItem> library = libDao.getLibraryData();
        Object[][] data = new Object[library.size()][4];

        int index = 0;
        for (LibraryItem item : library ) {
            Object[] row = { item.getId(), item.getPath(),
                    item.getDateAdded(),
                    item.getType() };
            data[index] = row;
            index += 1;
        }
        String[] columnNames = { "ID", "Path", "Date Added", "Type" };
        JTable table = new JTable(data, columnNames);
        table.getModel().addTableModelListener(new LibraryChangeListener());
        ThemeFactory theme = Theme.instance().getThemeFactory();
        Colors colors = theme.getColors();
        table.setBackground(colors.CELL_BKGRND_DROP_NOT_SELECTED());

        return table;
    }

    private class LibraryChangeListener implements TableModelListener {

        public void tableChanged (TableModelEvent e) {

        }

    }

    private class LibraryMouseListener implements MouseListener {

        public void mouseClicked (MouseEvent e) {
            String id = ((Component) e.getSource()).getName();
            if (id.equals("search_field")) {
                JTextField searchField = (JTextField) e.getSource();
                MyLibrary.clearSearchField(searchField);
            }
        }

        public void mouseEntered (MouseEvent e) {
        }

        public void mouseExited (MouseEvent e) {
            String id = ((Component) e.getSource()).getName();
            if (id.equals("search_field")) {
                JTextField searchField = (JTextField) e.getSource();
                MyLibrary.defaultSearchField(searchField);
            }
        }

        public void mousePressed (MouseEvent e) {
        }

        public void mouseReleased (MouseEvent e) {
        }
    }

    private class SearchTextFieldListener implements KeyListener {

        public void keyPressed (KeyEvent e) {
            this.keyTyped(e);
        }

        public void keyReleased (KeyEvent e) {
            String id = ((Component) e.getSource()).getName();
            if (id.equals("search_field")) {
                JTextField searchField = (JTextField) e.getSource();
                String search = searchField.getText();
                LibraryActions.searchMyLibrary(search);
            }
        }

        public void keyTyped (KeyEvent e) {
            String id = ((Component) e.getSource()).getName();
            if (id.equals("search_field")) {
                JTextField searchField = (JTextField) e.getSource();
                MyLibrary.clearSearchField(searchField);
            }
        }

    }

}
