package com.code83.ui.gui.menu;

import javax.swing.JMenuBar;

/**
 * The menu bar for the GUI.
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version $Id: MenuBar.java 872 2012-05-09 20:08:52Z mngazimb $
 * @since 0.1
 *
 */
public class MenuBar extends JMenuBar {

    private static final long serialVersionUID = 884220488619977098L;
    private ViewMenu view;

    public MenuBar () {
        this.formatMenuBar();
    }

    public void formatMenuBar () {

        /* file menu */
        FileMenu filemenu = new FileMenu("File");

        /* view */
        this.view = new ViewMenu("View");

        /* tools */
        ToolsMenu tools = new ToolsMenu("Tools");

        /* help menu */
        HelpMenu helpmenu = new HelpMenu("Help");

        this.add(filemenu);
        this.add(this.view);
        this.add(tools);
        this.add(helpmenu);
    }

}
