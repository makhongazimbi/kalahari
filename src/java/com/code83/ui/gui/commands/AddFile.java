package com.code83.ui.gui.commands;

import java.awt.Component;
import java.io.File;

import javax.swing.JFileChooser;

import com.code83.utils.library.LibraryActions;

/**
 * Adds a file to the library.
 *
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version $Id: AddFile.java 865 2011-12-15 03:35:16Z mngazimb $
 * @since 0.1
 *
 */
public class AddFile implements Command {

    private Component parent;

    public AddFile (final Component prnt) {
        this.parent = prnt;
    }

    public void execute () {
        JFileChooser jfc = new JFileChooser();
        jfc.showOpenDialog(this.parent);
        File chosen = jfc.getSelectedFile();
        if (chosen != null) {
            LibraryActions.addFileToLibrary(chosen);
        }
    }

}
