package com.code83.ui.gui.commands;

import javax.swing.SwingUtilities;

import com.code83.ui.gui.GuiFrame;
import com.code83.ui.gui.panels.Home;
import com.code83.ui.gui.panels.MainPanel;


/**
 * 
 *   GoHome.java 711 2009-10-22 21:29:36Z mngazimb $
 * 
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version $Id: GoHome.java 865 2011-12-15 03:35:16Z mngazimb $
 * @since 0.1
 *
 */
public class GoHome implements Command {
	private MainPanel panel;
	
	public GoHome(MainPanel pnl){
		panel = pnl;
	}
	
	public void execute() {
		final Runnable command = new Runnable () {
			public void run () {
				panel.switchPanel(new Home());
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
