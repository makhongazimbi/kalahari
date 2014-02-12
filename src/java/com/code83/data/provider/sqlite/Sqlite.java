package com.code83.data.provider.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.code83.data.provider.Provider;
import com.code83.utils.ExceptionHandling;

/**
 * A top level controller class for sqlite functionality. Includes methods to
 * setup database, reset database, and return various table DAOs.
 * 
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version $Id: Sqlite.java 865 2011-12-15 03:35:16Z mngazimb $
 * @since 0.1
 */
public class Sqlite implements Provider {
    private static Sqlite instance = null;
    private final Connection connection;
    private static Logger logger;
    private final MyLibraryProvider myLibraryProvider;
    private final SettingsProvider settingsProvider;

    /**
     * Constructor.
     * 
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    private Sqlite () throws ClassNotFoundException, SQLException {
        Class<?> classObj = this.getClass();
        Sqlite.logger = LoggerFactory.getLogger(classObj);
        Class.forName("org.sqlite.JDBC");
        this.connection = DriverManager
                .getConnection("jdbc:sqlite:data/kalahari.db");
        this.myLibraryProvider = new MyLibraryProvider(this.connection);
        this.settingsProvider = new SettingsProvider(this.connection);
    }

    /**
     * Create class instance
     * 
     * @return Sqlite
     */
    public static Sqlite instance () {
        if (Sqlite.instance == null) {
            try {
                Sqlite.instance = new Sqlite();
            } catch (ClassNotFoundException cnfe) {
                Sqlite.logger.error("Error creating class instance", cnfe);
            } catch (SQLException sqle) {
                Sqlite.logger.error("Error creating class instance", sqle);
            }
        }
        return Sqlite.instance;
    }

    /**
     * Close connection.
     */
    public void close () {
        try {
            this.connection.close();
        } catch (SQLException e) {
            Sqlite.logger.error(ExceptionHandling.getStackTrace(e));
        }
    }

    /**
     * Drop all tables and recreate them.
     */
    public void reset () {
        this.settingsProvider.tearDown();
        this.myLibraryProvider.tearDown();
    }

    /**
     * Get library provider.
     */
    public MyLibraryProvider getMyLibraryProvider () {
        return this.myLibraryProvider;
    }

    /**
     * Get settings provider.
     */
    public SettingsProvider getSettingsProvider () {
        return this.settingsProvider;
    }

}
