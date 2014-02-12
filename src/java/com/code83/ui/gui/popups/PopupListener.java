package com.code83.ui.gui.popups;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import com.code83.ui.gui.GuiFrame;


/**
 *   PopupListener.java 787 2010-04-18 16:25:54Z mngazimb $
 * 
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version $Id: PopupListener.java 865 2011-12-15 03:35:16Z mngazimb $
 * @since 0.1
 *
 */
public class PopupListener implements WindowListener {

	public void windowActivated (WindowEvent e) {}

	public void windowClosed (WindowEvent e) {
		GuiFrame.instance().setEnabled(true);
	}

	public void windowClosing (WindowEvent e) {}
	public void windowDeactivated (WindowEvent e) {}
	public void windowDeiconified (WindowEvent e) {}
	public void windowIconified (WindowEvent e) {}
	public void windowOpened (WindowEvent e) {}

}
