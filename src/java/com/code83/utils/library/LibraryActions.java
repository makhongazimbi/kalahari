package com.code83.utils.library;

import java.io.File;
import java.util.ArrayList;

import com.code83.data.accessLayer.DataAccessObject;
import com.code83.data.provider.sqlite.MyLibraryProvider;
import com.code83.utils.LibraryItem;

/**
 * Actions that can be performed on the library
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version $Id: LibraryActions.java 865 2011-12-15 03:35:16Z mngazimb $
 * @since 0.1
 */
public class LibraryActions {

    /**
     * 
     * @param path The path name of the file
     * @return 0 file added successfully, -1 error occurred, 1 file already 
     * 		exists in library
     */
    public static int addFileToLibrary (File file) {
        DataAccessObject dao = new DataAccessObject();
        MyLibraryProvider libraryDao = dao.getMyLibraryDao();
        String path = file.getAbsolutePath();
        if (file.isFile()) {
            // Only add the actual files (not folders) within this 
            // folder. Exclude any hidden files
            if (!file.isHidden()) {
                return libraryDao.addToLibrary(path);
            } else {
                // TODO log or notify
            }
        } else if (file.isDirectory()) {
            return libraryDao.addToLibrary(path);
        } else {
            // TODO log could not be added
        }
        return -1;
    }

    /**
     * 
     * @param folder The folder to add to the library
     */
    public static void addFolderToLibrary (File folder) {
        if (folder.isDirectory()) {
            AddFolderThread worker = new AddFolderThread(folder);
            worker.start();
        }
    }

    private static class AddFolderThread extends Thread {

        private File folder;

        public AddFolderThread (File f) {
            this.folder = f;
        }

        public void run () {
            if (this.folder.isDirectory()) {
                addFileToLibrary(this.folder);
                File[] files = this.folder.listFiles();
                for (File file : files ) {
                    // TODO depending on return status log appropriately
                    if (file.isFile() && !file.isHidden()) {
                        addFileToLibrary(file);
                    }
                }
            }
        }

    }

    public static void getMyLibrary () {
        /*
        DataAccessObject dao = new DataAccessObject();
        MyLibraryDao libraryDao = dao.getMyLibraryDao();
        ArrayList<LibraryItem> library = libraryDao.getLibraryData();
        for (LibraryItem item : library) {
        	System.out.println(item);
        }*/
    }

    public static void searchMyLibrary (String search) {
        DataAccessObject dao = new DataAccessObject();
        MyLibraryProvider libraryDao = dao.getMyLibraryDao();
        ArrayList<LibraryItem> library = libraryDao.searchLibrary(search);
        System.out.println("Search term [" + search + "]");
        for (LibraryItem item : library ) {
            System.out.println(item);
        }
        System.out.println("\n\n\n");
    }

}
