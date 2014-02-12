package com.code83.ui.gui.commands;

import com.code83.data.accessLayer.DataAccessObject;
import com.code83.data.provider.sqlite.SettingsProvider;
import com.code83.ui.gui.GuiFrame;


/**
 * 
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version $Id: SaveWindowDimensions.java 865 2011-12-15 03:35:16Z mngazimb $   SaveWindowDimensions.java 743 2010-03-29 05:35:23Z mngazimb $
 * @since 0.1
 *
 */
public class SaveWindowDimensions implements Command {
	private GuiFrame guiframe; 
	
	public SaveWindowDimensions (GuiFrame gui) {
		guiframe = gui;
	}
	
	public void execute () {
		int x = guiframe.getX();
		int y = guiframe.getY();
		int width = guiframe.getWidth();
		int height = guiframe.getHeight();
		DataAccessObject dao = new DataAccessObject();
		SettingsProvider settingsDao = dao.getSettingsDao();
		settingsDao.saveWindowDimensions(x, y, width, height);
	}

}
