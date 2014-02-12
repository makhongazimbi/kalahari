package com.code83.ui.gui.commands;

import javax.swing.SwingUtilities;

import com.code83.ui.gui.GuiFrame;
import com.code83.ui.gui.panels.MainPanel;
import com.code83.ui.gui.panels.P2pPanel;


/**
 * Display P2P panel
 * 
 *   ShowP2pPanel.java 741 2010-03-26 01:37:41Z mngazimb $
 * 
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version $Id: ShowP2pPanel.java 865 2011-12-15 03:35:16Z mngazimb $
 * @since 0.1
 *
 */
public class ShowP2pPanel implements Command {
	private MainPanel panel;
	
	public ShowP2pPanel (MainPanel pnl) {
		panel = pnl;
	}
	
	public void execute () {
		final Runnable command = new Runnable () {
			public void run () {
				panel.switchPanel(new P2pPanel());
				GuiFrame.instance().validate();
			}
		};
		
		Thread dispatchThread = new Thread() {
		     public void run() {
		         try {
		             SwingUtilities.invokeAndWait(command);
		         }
		         catch (Exception e) {
		             e.printStackTrace();
		         }
		     }
		 };
		 dispatchThread.start();
	}

}
