package com.code83.ui.gui.commands;

import com.code83.ui.gui.GuiFrame;
import com.code83.ui.gui.panels.FooterPanel;

/**
 * A class that encapsulates the logic to show a download tray that has been
 * hidden.
 * 
 *   ShowDownloadTray.java 692 2009-09-15 21:54:36Z mngazimb $
 * 
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version $Id: ShowDownloadTray.java 865 2011-12-15 03:35:16Z mngazimb $
 * @since 0.1
 * @see Command
 *
 */
public class ShowDownloadTray implements Command {
	private FooterPanel panel;
	
	public ShowDownloadTray(FooterPanel footer){
		panel = footer;
	}
	
	public void execute() {
		panel.showDownloadPanel();
		GuiFrame.instance().validate();
	}

}
