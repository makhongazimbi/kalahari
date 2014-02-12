package com.code83.data.accessLayer;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.code83.data.accessLayer.DataAccessObject;
import com.code83.data.provider.sqlite.MyLibraryProvider;
import com.code83.utils.LibraryItem;

/**
 * MyLibrary unit tests.
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version $Id: MyLibraryTests.java 865 2011-12-15 03:35:16Z mngazimb $
 * @since 0.1
 */
public class MyLibraryTests {

    @Test
    public void testAddRemoveFetchLibraryPath () {
        DataAccessObject dao = new DataAccessObject();
        MyLibraryProvider myLibraryDao = dao.getMyLibraryDao();
        String fakePath = "/a/fake/path/";
        int status = myLibraryDao.addToLibrary(fakePath);
        assertEquals("Expected status zero when adding to library, but got [" +
                status + "]", 0, status);

        boolean foundIt = false;
        List<LibraryItem> library = myLibraryDao.getLibraryData();
        assertTrue("Library size should be at least 1", library.size() > 0);
        for (LibraryItem item : library ) {
            if (item.path.equals(fakePath)) {
                foundIt = true;
            }
        }
        assertTrue("Fake path that was added should have been found. " +
                "Either adding it failed, or fetching it failed", foundIt);

    }

}
