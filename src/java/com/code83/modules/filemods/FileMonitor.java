package com.code83.modules.filemods;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * Monitors shared folders for addition or removal of files from paths 
 * contained in the database.
 * This will be useful if a user directly adds/remove files to the file path 
 * not via the add to library feature but via the file system directly. This
 * will keep the library up to date.
 * The thread will check on existing files and folders in the library to make
 * sure that they still exist on the file system. If the file has been
 * modified after the last modify time we have on record then the file
 * hash should be recalculated and updated in the db.
 * 
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version 0.1 SVN: $Id: FileMonitor.java 865 2011-12-15 03:35:16Z mngazimb $
 * @since 0.1
 * @see FileSearch
 */
public class FileMonitor extends Thread {

    /**
     * Whether to delay running thread or not.
     */
    private boolean delay;
    /**
     * Delay time in milliseconds.
     */
    private static final long DELAY_TIME = 60 * 1000L;
    /**
     * Time to wait before repeating the search;
     */
    private static final long MONITOR_REPEAT_INTERVAL = 60 * 60 * 1000L;

    /**
     * Constructor.
     * @param dly Whether to delay execution or not. Delay should be set to
     * false if this is a user requested file monitoring.
     */
    public FileMonitor (boolean dly) {
        super("File Monitor");
        this.delay = dly;
    }

    /**
     * Run thread.
     */
    public void run () {
        // 1. Does not need to run all the time. And does not need to run at 
        // immediately at system startup.
        if (this.delay) {
            try {
                Thread.sleep(FileMonitor.DELAY_TIME);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                // log
                e.printStackTrace();
            }
        }

        while (true) {
            // 1. Get a list of all the files in the database. 
            // 2. Loop over each file and folder, update db with files.
            List<File> files = new LinkedList<File>();
            for (File file : files ) {
                // Check what ever needs to be checked.
                file.lastModified();
            }

            try {
                Thread.sleep(FileMonitor.MONITOR_REPEAT_INTERVAL);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                // TODO log
                e.printStackTrace();
            }
        }
        //FIXME complete
    }

}
