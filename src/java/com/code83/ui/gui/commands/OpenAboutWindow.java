package com.code83.ui.gui.commands;

import com.code83.ui.gui.popups.About;

/**
 * 
 * $Id: OpenAboutWindow.java 865 2011-12-15 03:35:16Z mngazimb $
 * 
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version $Id: OpenAboutWindow.java 865 2011-12-15 03:35:16Z mngazimb $
 * @since 0.1
 */
public class OpenAboutWindow implements Command {

	public void execute() {
		About about = new About();
		about.setVisible(true);
	}

}
