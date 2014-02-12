package com.code83.modules.filemods;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;

import com.code83.modules.communication.Responder;
import com.code83.utils.messages.MessageFactory;
import com.code83.utils.messages.Request;


/**
 * This class search the network for files in response to user requests.
 *
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version 0.1 SVN: $Id: FileSearch.java 883 2012-09-11 22:31:48Z mngazimb $
 * @since 0.1
 * @see FileMonitor
 */
public class FileSearch {
    private static FileSearch fileSearch = null;
    private int searchCounter = 0;
    private Map<Integer, FileDescriptor> searches =
            new Hashtable<Integer, FileDescriptor>();

    /**
     * Constructor.
     */
    private FileSearch () {
    }

    public static FileSearch instance () {
        if (fileSearch == null) {
            fileSearch = new FileSearch();
        }
        return fileSearch;
    }

    public void search (FileDescriptor descriptor) {
        descriptor.setId(this.searchCounter);
        this.searches.put(new Integer(this.searchCounter), descriptor);
        this.searchCounter += 1;
        // start a search thread
        SearchThread search = new SearchThread(descriptor);
        search.start();
    }

    public List<FileDescriptor> getSearches () {
        List<FileDescriptor> searchList = new Vector<FileDescriptor>();
        SortedSet<Integer> ids = new TreeSet<Integer>(this.searches.keySet());
        for (Integer id : ids ) {
            searchList.add(this.searches.get(id));
        }
        return searchList;
    }

    public void removeSearch (int searchId) {
        this.searches.remove(new Integer(searchId));
    }

    private class SearchThread extends Thread {

        private FileDescriptor fileDescriptor;
        /**
         * Number of milliseconds to wait between sending file requests.
         */
        private int waitTime = 30 * 1000;
        /**
         * Number of times to repeat the search.
         */
        private int repeatSearch = 2;

        protected SearchThread (FileDescriptor descriptor) {
            this.fileDescriptor = descriptor;
        }

        public void run () {
            Responder responder = Responder.instance();
            Request request = MessageFactory.createRequest();
            request.setPayload(this.fileDescriptor);
            for (int i = 0; i < this.repeatSearch; i++ ) {
                responder.sendToAll(request);
                try {
                    Thread.sleep(this.waitTime);
                } catch (InterruptedException e) {
                    // TODO log
                    e.printStackTrace();
                }
            }
        }

    }

}
