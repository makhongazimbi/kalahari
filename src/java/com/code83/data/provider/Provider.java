package com.code83.data.provider;

import com.code83.data.provider.sqlite.MyLibraryProvider;
import com.code83.data.provider.sqlite.SettingsProvider;

/**
 * Data provider interface. This allows different types of databases
 * to be used so long as they implement this interface
 *
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version $Id: Provider.java 865 2011-12-15 03:35:16Z mngazimb $
 * @since 0.1
 */
public interface Provider {

    /**
     * Close provider.
     */
    void close();

    /**
     * Get library DAO.
     * @return library DAO
     */
    MyLibraryProvider getMyLibraryProvider();

    /**
     * Get settings DAO.
     * @return settings DAO
     */
    SettingsProvider getSettingsProvider();

    /**
     * Reset provider.
     */
    void reset();

}
