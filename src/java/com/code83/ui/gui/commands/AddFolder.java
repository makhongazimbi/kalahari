package com.code83.ui.gui.commands;

import java.awt.Component;
import java.io.File;


import com.code83.utils.library.LibraryActions;
import com.l2fprod.common.swing.JDirectoryChooser;

/**
 * Adds a folder and all its contents to the library
 * 
 *   AddFolder.java 787 2010-04-18 16:25:54Z mngazimb $
 * 
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version $Id: AddFolder.java 865 2011-12-15 03:35:16Z mngazimb $
 * @since 0.1
 *
 */
public class AddFolder implements Command {
	
	private Component parent;
	
	public AddFolder (Component prnt) {
		parent = prnt;
	}
	
	public void execute () {
		JDirectoryChooser jdc = new JDirectoryChooser();
		jdc.showOpenDialog(parent);
		File chosen = jdc.getSelectedFile();
		if (chosen!=null) {
			LibraryActions.addFolderToLibrary(chosen);
		}
	}
	
}
