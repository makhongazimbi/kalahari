package com.code83.data.accessLayer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.code83.data.provider.Provider;
import com.code83.data.provider.sqlite.MyLibraryProvider;
import com.code83.data.provider.sqlite.SettingsProvider;
import com.code83.data.provider.sqlite.Sqlite;
import com.code83.modules.auxilliary.Shutdown;


/**
 * Data access class.
 * 
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version $Id: DataAccessObject.java 865 2011-12-15 03:35:16Z mngazimb $
 * @since 0.1
 */
public class DataAccessObject implements Shutdown {

    /**
     * Data provider.
     */
    private final Provider provider;

    /**
     * Logger.
     */
    private final Logger logger = LoggerFactory
            .getLogger(DataAccessObject.class);

    /**
     * Default constructor.
     */
    public DataAccessObject () {
        this.provider = Sqlite.instance();
    }

    /**
     * Close provider.
     */
    public final void shutdown () {
        this.logger.info("Shutting down Data Access Object");
        this.provider.close();
    }

    /**
     * Get library DAO.
     * 
     * @return library DAO
     */
    public final MyLibraryProvider getMyLibraryDao () {
        return this.provider.getMyLibraryProvider();
    }

    /**
     * Get settings DAO.
     * 
     * @return settings DAO
     */
    public final SettingsProvider getSettingsDao () {
        return this.provider.getSettingsProvider();
    }

    /**
     * Reset provider.
     */
    public final void reset () {
        this.provider.reset();
    }
}
