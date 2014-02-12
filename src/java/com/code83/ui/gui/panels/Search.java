package com.code83.ui.gui.panels;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import com.code83.modules.filemods.FileDescriptor;
import com.code83.modules.filemods.FileSearch;
import com.code83.ui.gui.commands.CommandRegister;
import com.code83.ui.gui.commands.search.ShowAudioSearchForm;
import com.code83.ui.gui.commands.search.ShowDocumentSearchForm;
import com.code83.ui.gui.commands.search.ShowImageSearchForm;
import com.code83.ui.gui.commands.search.ShowProgramSearchForm;
import com.code83.ui.gui.commands.search.ShowVideoSearchForm;
import com.code83.ui.gui.panels.forms.AudioSearchForm;
import com.code83.ui.gui.themes.Colors;
import com.code83.ui.gui.themes.Icons;
import com.code83.ui.gui.themes.Theme;
import com.code83.ui.gui.themes.ThemeFactory;



/**
 * Search panel.
 *
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version 0.1 SVN: $Id: Search.java 865 2011-12-15 03:35:16Z mngazimb $
 * @since 0.1
 */
public class Search extends JPanel {
    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -6844416058651750700L;

    /**
     * Constructor.
     */
    public Search () {
        SearchButtonListener listener = new SearchButtonListener();
        ThemeFactory theme = Theme.instance().getThemeFactory();
        Colors colors = theme.getColors();
        Icons icons = theme.getIcons();
        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout());
        buttons.setBackground(colors.STATUS_PANEL_INVISIBLE());

        JLabel audio = new JLabel(new ImageIcon(icons.AUDIO_ICON_16X16()));
        audio.addMouseListener(listener);
        audio.setToolTipText("Audio File Search");
        audio.setName("audio");

        JLabel video = new JLabel(new ImageIcon(icons.VIDEO_ICON_16X16()));
        video.addMouseListener(listener);
        video.setToolTipText("Video File Search");
        video.setName("video");

        JLabel image = new JLabel(new ImageIcon(icons.IMAGES_ICON_16X16()));
        image.addMouseListener(listener);
        image.setToolTipText("Image File Search");
        image.setName("image");

        JLabel document =
                new JLabel(new ImageIcon(icons.DOCUMENTS_ICON_16X16()));
        document.addMouseListener(listener);
        document.setToolTipText("Document File Search");
        document.setName("document");

        JLabel program =
                new JLabel(new ImageIcon(icons.PROGRAMS_ICON_16X16()));
        program.addMouseListener(listener);
        program.setToolTipText("Program File Search");
        program.setName("program");

        // Display icon to icon to Go to search results page if there are any
        // search results to display
        FileSearch fileSearch = FileSearch.instance();
        List<FileDescriptor> searches = fileSearch.getSearches();
        if (searches.size() > 0) {
            JPanel goToSearchResults = new JPanel();
            goToSearchResults.setBackground(colors.STATUS_PANEL_INVISIBLE());
            goToSearchResults.setLayout(new BorderLayout());
            JPanel searchResults = new JPanel();
            searchResults.setBackground(colors.STATUS_PANEL_INVISIBLE());
            searchResults.setLayout(new BorderLayout());
            JLabel resultsIcon =
                    new JLabel(
                    new ImageIcon(icons.SEARCH_RESULTS_ICON_16X16()));
            searchResults.add(resultsIcon, BorderLayout.LINE_START);
            resultsIcon.addMouseListener(listener);
            resultsIcon.setToolTipText("Previous search results");
            resultsIcon.setName("search_results");
            Component spaceLeft = Box.createRigidArea(new Dimension(10, 0));
            searchResults.add(spaceLeft, BorderLayout.CENTER);
            Component spaceRight = Box.createRigidArea(new Dimension(5, 0));
            Border verticalTab = BorderFactory.createMatteBorder(
                    0, 0, 0, 1, colors.FOOTER_PANEL_BORDER());
            searchResults.setBorder(verticalTab);
            goToSearchResults.add(searchResults, BorderLayout.LINE_START);
            goToSearchResults.add(spaceRight, BorderLayout.CENTER);
            buttons.add(goToSearchResults);
        }
        buttons.add(audio);
        buttons.add(video);
        buttons.add(image);
        buttons.add(document);
        buttons.add(program);

        JPanel header = new JPanel();
        header.setLayout(new BorderLayout());
        setLayout(new BorderLayout());

        JPanel titleBar = new JPanel();
        titleBar.setLayout(new FlowLayout());
        JLabel title = new JLabel("Search");
        titleBar.setBackground(colors.STATUS_PANEL_INVISIBLE());
        titleBar.add(title);

        JPanel spacer = new JPanel();
        spacer.setLayout(new BoxLayout(spacer, BoxLayout.PAGE_AXIS));
        spacer.setBackground(colors.STATUS_PANEL_INVISIBLE());
        int width = buttons.getPreferredSize().width;
        spacer.add(Box.createRigidArea(new Dimension(width, 0)));

        header.setBackground(colors.STATUS_PANEL_INVISIBLE());
        header.add(buttons, BorderLayout.LINE_START);
        header.add(titleBar, BorderLayout.CENTER);
        header.add(spacer, BorderLayout.LINE_END);
        Border border = BorderFactory.createMatteBorder(
                0, 0, 1, 0, colors.FOOTER_PANEL_BORDER());
        header.setBorder(border);
        add(header, BorderLayout.PAGE_START);

        JPanel searchForm = new JPanel();
        searchForm.setLayout(new GridLayout(1, 1));
        searchForm.add(new AudioSearchForm());
        add(searchForm, BorderLayout.CENTER);

        CommandRegister commands = CommandRegister.instance();
        commands.addCommand("show-audio-search-form",
                new ShowAudioSearchForm(searchForm));
        commands.addCommand("show-video-search-form",
                new ShowVideoSearchForm(searchForm));
        commands.addCommand("show-image-search-form",
                new ShowImageSearchForm(searchForm));
        commands.addCommand("show-document-search-form",
                new ShowDocumentSearchForm(searchForm));
        commands.addCommand("show-program-search-form",
                new ShowProgramSearchForm(searchForm));
    }

    private class SearchButtonListener implements MouseListener {

        public void mouseClicked (MouseEvent e) {
            CommandRegister commands = CommandRegister.instance();
            String id = ((Component) e.getSource()).getName();
            System.out.println(id);
            if (id.equals("audio")) {
                commands.getCommand("show-audio-search-form").execute();
            } else if (id.equals("video")) {
                commands.getCommand("show-video-search-form").execute();
            } else if (id.equals("image")) {
                commands.getCommand("show-image-search-form").execute();
            } else if (id.equals("document")) {
                commands.getCommand("show-document-search-form").execute();
            } else if (id.equals("program")) {
                commands.getCommand("show-program-search-form").execute();
            } else if (id.equals("search_results")) {
                commands.getCommand("search_screen").execute();
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

}
