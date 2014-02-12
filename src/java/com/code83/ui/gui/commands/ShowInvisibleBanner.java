package com.code83.ui.gui.commands;

import com.code83.ui.gui.GuiFrame;
import com.code83.ui.gui.panels.StatusPanel;
import com.code83.utils.RealTimeSwing;


/**
 * 
 *   ShowInvisibleBanner.java 737 2010-03-18 17:16:30Z mngazimb $
 * 
 * @author Makho Ngazimbi
 * @version $Id: ShowInvisibleBanner.java 865 2011-12-15 03:35:16Z mngazimb $
 * @since 0.1
 *
 */
public class ShowInvisibleBanner implements Command {
	private StatusPanel banner;
	
	public ShowInvisibleBanner (StatusPanel panel) {
		banner = panel;
	}
	
	public void execute () {
		final Runnable command = new Runnable () {
			public void run () {
				banner.invisible();
				GuiFrame.instance().validate();
			}
		};
		RealTimeSwing.invokeNow(command);
	}

}
