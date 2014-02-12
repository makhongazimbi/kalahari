package com.code83.ui.gui.commands;

import com.code83.ui.gui.popups.Themes;

/**
 * 
 *   ChooseTheme.java 696 2009-09-23 16:54:17Z mngazimb $
 * 
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version $Id: ChooseTheme.java 865 2011-12-15 03:35:16Z mngazimb $
 * @since 0.1
 */
public class ChooseTheme implements Command {
	
	public void execute() {
		Themes themes = new Themes();
		themes.setVisible(true);
	}
	
}
