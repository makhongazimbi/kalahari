package com.code83.ui.gui.commands;

import com.code83.ui.gui.GuiFrame;
import com.code83.ui.gui.panels.FooterPanel;
import com.code83.utils.RealTimeSwing;


/**
 * 
 *   HideDownloadTray.java 724 2009-11-26 16:35:35Z mngazimb $
 * 
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version $Id: HideDownloadTray.java 865 2011-12-15 03:35:16Z mngazimb $
 * @since 0.1
 * @see Command
 *
 */
public class HideDownloadTray implements Command {
	private FooterPanel panel;
	
	public HideDownloadTray(FooterPanel footer) {
		panel = footer;
	}
	
	public void execute() {
		final Runnable command = new Runnable () {
			public void run () {
				panel.hideDowloadPanel();
				GuiFrame.instance().validate();
			}
		};
		RealTimeSwing.invokeNow(command);
	}

}
