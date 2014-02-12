package com.code83.ui.gui.commands;

import javax.swing.SwingUtilities;

import com.code83.ui.gui.GuiFrame;
import com.code83.ui.gui.panels.MainPanel;
import com.code83.ui.gui.panels.Transfers;


/**
 * 
 *   ShowTransfers.java 748 2010-04-02 14:30:47Z mngazimb $
 * 
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version $Id: ShowTransfers.java 865 2011-12-15 03:35:16Z mngazimb $
 * @since 0.1
 *
 */
public class ShowTransfers implements Command {

	private MainPanel panel;
	
	public ShowTransfers (MainPanel p) {
		panel = p;
	}
	
	public void execute () {
		final Runnable command = new Runnable () {
			public void run () {
				panel.switchPanel(new Transfers());
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
