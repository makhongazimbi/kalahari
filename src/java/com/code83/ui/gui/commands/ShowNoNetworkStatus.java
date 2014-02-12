package com.code83.ui.gui.commands;

import com.code83.ui.gui.GuiFrame;
import com.code83.ui.gui.panels.StatusPanel;
import com.code83.utils.RealTimeSwing;


/**
 *   ShowNoNetworkStatus.java 771 2010-04-12 00:51:34Z mngazimb $
 *  
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @since 0.1
 * @version $Id: ShowNoNetworkStatus.java 865 2011-12-15 03:35:16Z mngazimb $
 *
 */
public class ShowNoNetworkStatus implements Command {

	private StatusPanel status;
	
	public ShowNoNetworkStatus (StatusPanel panel) {
		status = panel;
	}
	
	public void execute () {
		final Runnable command = new Runnable () {
			public void run () {
				status.noNetworkStatus();
				GuiFrame.instance().validate();
			}
		};
		RealTimeSwing.invokeNow(command);
	}

}
