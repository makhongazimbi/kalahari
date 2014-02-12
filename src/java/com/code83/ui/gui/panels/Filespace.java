package com.code83.ui.gui.panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import com.code83.data.accessLayer.DataAccessObject;
import com.code83.data.provider.sqlite.MyLibraryProvider;
import com.code83.ui.gui.themes.Colors;
import com.code83.ui.gui.themes.Theme;
import com.code83.ui.gui.themes.ThemeFactory;
import com.code83.utils.LibraryItem;
import net.iharder.dnd.FileDrop;

/**
 * Filespace panel.
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version 0.1 SVN: $Id: Filespace.java 865 2011-12-15 03:35:16Z mngazimb $
 * @since 0.1
 */
public class Filespace extends JPanel {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -1262819596214103320L;

    /**
     * Filespace body.
     */
    private final JPanel filespaceBody = new JPanel(new GridLayout(1, 1));

    /**
     * Table model.
     */
    private final DefaultTableModel tableModel = new DefaultTableModel();

    /**
     * Date format.
     */
    public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";

    /**
     * Constructor.
     */
    public Filespace () {
        this.setLayout(new BorderLayout());
        this.addHeader();
        this.addBody();
    }

    /**
     * Add header bar to filespace panel.
     */
    protected void addHeader () {
        ThemeFactory theme = Theme.instance().getThemeFactory();
        Colors colors = theme.getColors();

        JPanel header = new JPanel();
        header.setLayout(new BorderLayout());
        this.setLayout(new BorderLayout());

        JPanel titleBar = new JPanel();
        titleBar.setLayout(new FlowLayout());
        JLabel title = new JLabel("Filespace");
        titleBar.setBackground(colors.STATUS_PANEL_INVISIBLE());
        titleBar.add(title);

        header.setBackground(colors.STATUS_PANEL_INVISIBLE());
        header.add(titleBar, BorderLayout.CENTER);

        Border border = BorderFactory.createMatteBorder(0, 0, 1, 0, colors
                .FOOTER_PANEL_BORDER());
        header.setBorder(border);
        this.add(header, BorderLayout.PAGE_START);
    }

    /**
     * Add filespace body.
     */
    protected void addBody () {
        final JTable table = this.getFilespaceTable();
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        this.filespaceBody.add(scrollPane);
        this.add(this.filespaceBody, BorderLayout.CENTER);

        // Add existing files to table view
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        //model.addRow(new Object[] { file, path, date });
        //model.
        DataAccessObject dao = new DataAccessObject();
        MyLibraryProvider libraryProvider = dao.getMyLibraryDao();
        List<LibraryItem> data = libraryProvider.getLibraryData();
        for (LibraryItem item : data ) {
            //System.out.println(item);
            model.addRow(new Object[] { item.getFileName(), item.getPath(),
                    item.getDateAdded() });
        }

        //FileDrop fileDrop = 
        new FileDrop(System.out,
                table, new FileDrop.Listener() {
            public void filesDropped (java.io.File[] files) {
                DataAccessObject dao = new DataAccessObject();
                MyLibraryProvider libraryProvider = dao.getMyLibraryDao();

                DefaultTableModel model = (DefaultTableModel) table.getModel();
                for (int i = 0; i < files.length; i++ ) {
                    try {
                        //String file = files[i].getName();
                        String path = files[i].getCanonicalPath();
                        //DateTime date = new DateTime();
                        //model.addRow(new Object[] { file, path, date });
                        // 1. Add a file to library
                        libraryProvider.addToLibrary(path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } // end for: through each dropped file

                // 2. Delete all files from table.
                int rows = model.getRowCount();
                for (int i = 0; i < rows; i++ ) {
                    // Keep removing the 0th row instead of the ith row. On
                    // every removal the number of available indices will 
                    // decrease by one.
                    model.removeRow(0);
                }
                // 3. Get items from database and display them.
                List<LibraryItem> data = libraryProvider.getLibraryData();
                for (LibraryItem item : data ) {
                    //System.out.println(item);
                    model.addRow(new Object[] { item.getFileName(),
                            item.getPath(), item.getDateAdded() });
                }
            } // end filesDropped
        }); // end FileDrop.Listener

    }

    public void populateFilespace () {

    }

    /**
     * Create filespace table.
     * 
     * @return JTable
     */
    private JTable getFilespaceTable () {
        this.tableModel.addColumn("File");
        this.tableModel.addColumn("Path");
        this.tableModel.addColumn("Date Added");
        JTable table = new JTable(this.tableModel);
        table.getModel().addTableModelListener(new FilespaceChangeListener());
        ThemeFactory theme = Theme.instance().getThemeFactory();
        Colors colors = theme.getColors();
        table.setBackground(colors.CELL_BKGRND_DROP_NOT_SELECTED());
        return table;
    }

    /**
     * Change event listener for filespace table.
     * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
     */
    private class FilespaceChangeListener implements TableModelListener {

        /**
         * Do something when the table changes.
         */
        public void tableChanged (TableModelEvent e) {
        }

    }

}
