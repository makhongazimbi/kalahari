package com.code83.ui.gui.commands;

import com.code83.ui.gui.GuiFrame;
import com.code83.ui.gui.panels.ContentPanel;

/**
 * 
 *   ShowSideBar.java 692 2009-09-15 21:54:36Z mngazimb $
 * 
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version $Id: ShowSideBar.java 865 2011-12-15 03:35:16Z mngazimb $
 * @since 0.1
 * @see Command
 *
 */
public class ShowSideBar implements Command {
	private ContentPanel panel;
	
	public ShowSideBar(ContentPanel content){
		panel = content;
	}
	
	public void execute() {
		panel.showSideBar();
		GuiFrame.instance().validate();
	}

}
