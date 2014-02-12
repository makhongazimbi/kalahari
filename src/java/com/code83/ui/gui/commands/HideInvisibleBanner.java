package com.code83.ui.gui.commands;

import com.code83.ui.gui.GuiFrame;
import com.code83.ui.gui.panels.StatusPanel;
import com.code83.utils.RealTimeSwing;


/**
 * 
 * $Id: HideInvisibleBanner.java 865 2011-12-15 03:35:16Z mngazimb $
 * 
 * @author Makho Ngazimbi
 * @version $Id: HideInvisibleBanner.java 865 2011-12-15 03:35:16Z mngazimb $
 * @since 0.1
 *
 */
public class HideInvisibleBanner implements Command {
	private StatusPanel banner;
	
	public HideInvisibleBanner (StatusPanel panel) {
		banner = panel;
	}

	public void execute () {
		final Runnable command = new Runnable () {
			public void run () {
				banner.visible();
				GuiFrame.instance().validate();
			}
		};
		RealTimeSwing.invokeNow(command);
	}

}
