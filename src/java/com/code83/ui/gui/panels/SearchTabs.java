package com.code83.ui.gui.panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;
import javax.swing.SwingWorker;
import javax.swing.border.Border;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.code83.modules.filemods.FileDescriptor;
import com.code83.modules.filemods.FileSearch;
import com.code83.modules.status.Status;
import com.code83.ui.gui.themes.Colors;
import com.code83.ui.gui.themes.Icons;
import com.code83.ui.gui.themes.Theme;
import com.code83.ui.gui.themes.ThemeFactory;
import com.code83.utils.ButtonTabComponent;


/**
 * File search tabs class.
 *
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version $Id: SearchTabs.java 894 2012-09-13 22:17:40Z mngazimb $
 * @since 0.1
 */
public class SearchTabs extends JPanel {

    private static final long serialVersionUID = 8900021574715841284L;
    private static SearchTabs tabs = null;
    private JTabbedPane tabbedPane = new JTabbedPane();
    private int tabCounter;
    /**
     * Logger.
     */
    private Logger logger = LoggerFactory.getLogger(SearchTabs.class);

    /**
     * Default constructor.
     */
    private SearchTabs () {
        super(new GridLayout(1, 1));
        this.add(this.tabbedPane);
        //The following line enables to use scrolling tabs.
        this.tabbedPane.setTabLayoutPolicy(
                JTabbedPane.SCROLL_TAB_LAYOUT);
    }

    /**
     * Get instance of this object.
     * @return Get this object
     */
    public static SearchTabs instance () {
        if (tabs == null) {
            tabs = new SearchTabs();
        }
        return tabs;
    }

    /**
     * Show search tabs.
     */
    public void showTabs () {
        FileSearch fileSearch = FileSearch.instance();
        List<FileDescriptor> searches = fileSearch.getSearches();
        this.tabbedPane.removeAll();
        this.tabCounter = 0;

        for (FileDescriptor search : searches) {
            JComponent panel = this.makeTabContents(this.tabCounter,
                    search);
            String tabTitle = search.getSearchString();
            this.tabbedPane.addTab(tabTitle, panel);
            this.tabbedPane.setTabComponentAt(this.tabCounter,
                    new ButtonTabComponent(this.tabbedPane,
                            search.getId()));
            this.tabbedPane.setSelectedComponent(panel);
            this.tabCounter += 1;
        }
    }

    /**
     * Make tab contents.
     * @param tabId Tab ID
     * @param file File description for the file.
     * @return Tab contents.
     */
    private JComponent makeTabContents (int tabId,
            FileDescriptor file) {
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BorderLayout());

        JPanel titleBar = new JPanel();
        titleBar.setLayout(new GridLayout(1, 1));

        ThemeFactory theme = Theme.instance().getThemeFactory();
        Colors colors = theme.getColors();
        Icons icons = theme.getIcons();

        JPanel leftSide = new JPanel();
        FlowLayout leftAlign = new FlowLayout();
        leftAlign.setAlignment(FlowLayout.LEFT);
        leftSide.setLayout(leftAlign);
        leftSide.setBackground(colors.STATUS_PANEL_INVISIBLE());

        JPanel rightSide = new JPanel();
        FlowLayout rightAlign = new FlowLayout();
        rightAlign.setAlignment(FlowLayout.RIGHT);
        rightSide.setLayout(rightAlign);
        rightSide.setBackground(colors.STATUS_PANEL_INVISIBLE());

        JLabel searchType = new JLabel();
        String type = file.getSearchType();
        if (type.equals("All")) {
            searchType.setIcon(
                    new ImageIcon(icons.WILD_CARD_ICON_16X16()));
        } else if (type.equals("Audio")) {
            searchType.setIcon(
                    new ImageIcon(icons.AUDIO_ICON_16X16()));
        } else if (type.equals("Video")) {
            searchType.setIcon(
                    new ImageIcon(icons.VIDEO_ICON_16X16()));
        } else if (type.equals("Images")) {
            searchType.setIcon(
                    new ImageIcon(icons.IMAGES_ICON_16X16()));
        } else if (type.equals("Documents")) {
            searchType.setIcon(
                    new ImageIcon(icons.DOCUMENTS_ICON_16X16()));
        } else if (type.equals("Programs")) {
            searchType.setIcon(
                    new ImageIcon(icons.PROGRAMS_ICON_16X16()));
        }
        searchType.setToolTipText(type + " file type search");
        leftSide.add(searchType);
        JLabel title = new JLabel(file.getSearchString());
        leftSide.add(title);

        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setToolTipText("Search progress");
        JLabel reload = new JLabel(new ImageIcon(icons.RELOAD_ICON_16X16()));
        reload.setToolTipText("Repeat search");
        JLabel stop = new JLabel(new ImageIcon(icons.STOP_ICON_ENABLED_16X16()));
        stop.setToolTipText("Stop search");
        rightSide.add(progressBar);
        rightSide.add(reload);
        rightSide.add(stop);

        titleBar.add(leftSide);
        titleBar.add(rightSide);

        searchPanel.add(titleBar, BorderLayout.PAGE_START);
        Border border = BorderFactory.createMatteBorder(
                0, 0, 1, 0, colors.FOOTER_PANEL_BORDER());
        titleBar.setBorder(border);

        SearchTask task = new SearchTask();
        task.addPropertyChangeListener(
                new ProgressListener(progressBar));
        task.execute();
        return searchPanel;
    }

    private class SearchTask extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground () throws Exception {
            Random random = new Random();
            int progress = 0;
            this.setProgress(0);
            while (progress < 100) {
                //Sleep for up to one second.
                try {
                    Thread.sleep(random.nextInt(1000));
                } catch (InterruptedException ignore) {
                }
                //Make random progress.
                progress += random.nextInt(10);
                this.setProgress(Math.min(progress, 100));
            }
            return null;
        }

        @Override
        public void done () {
            SearchTabs.this.logger.debug("Search task complete. \n" +
                    Status.getInstance().getReplyStatus());
        }

    }

    private class ProgressListener implements PropertyChangeListener {

        private JProgressBar progressBar;

        public ProgressListener (JProgressBar progress) {
            this.progressBar = progress;
        }

        public void propertyChange (PropertyChangeEvent evt) {
            if ("progress" == evt.getPropertyName()) {
                int progress = (Integer) evt.getNewValue();
                this.progressBar.setValue(progress);
            }
        }

    }

}
