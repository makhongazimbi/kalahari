package com.code83.ui.gui.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import com.code83.ui.gui.commands.CommandRegister;
import com.code83.ui.gui.commands.HideSideBar;
import com.code83.ui.gui.commands.ShowSideBar;

/**
 * The panel that contains all the other panels.
 * 
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version $Id: ContentPanel.java 865 2011-12-15 03:35:16Z mngazimb $
 * @since 0.1
 */
public class ContentPanel extends JPanel {

    private static final long serialVersionUID = -3196201579558035802L;
    private static final int SEARCH_PANEL_HEIGHT = 50;
    private static final int SIDE_BAR_WIDTH = 180;
    private SideBarPanel side;

    public ContentPanel (final int width, final int height) {
        this.setPreferredSize(new Dimension(width, height));

        this.setLayout(new BorderLayout());

        SearchBar search = new SearchBar();
        this.side = new SideBarPanel();
        FooterPanel footer = new FooterPanel();
        MainPanel main = new MainPanel();

        search.setPreferredSize(new Dimension(width, SEARCH_PANEL_HEIGHT));
        this.side.setPreferredSize(new Dimension(SIDE_BAR_WIDTH, height));

        this.add(search, BorderLayout.NORTH);
        this.add(this.side, BorderLayout.WEST);
        this.add(footer, BorderLayout.SOUTH);
        this.add(main, BorderLayout.CENTER);

        CommandRegister commands = CommandRegister.instance();
        commands.addCommand("hide_sidebar", new HideSideBar(this));
        commands.addCommand("show_sidebar", new ShowSideBar(this));
    }

    public void hideSideBar () {
        this.remove(this.side);
    }

    public void showSideBar () {
        this.add(this.side, BorderLayout.WEST);
    }
}
