package com.code83.data.provider.sqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.code83.utils.DateTime;
import com.code83.utils.ExceptionHandling;
import com.code83.utils.LibraryItem;

/**
 * Access stored data for MyLibrary.
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version $Id: MyLibraryProvider.java 865 2011-12-15 03:35:16Z mngazimb $
 * @since 0.1
 */
public class MyLibraryProvider implements ProviderInterface {

    /**
     * Connection object.
     */
    private final Connection connection;

    /**
     * Logger.
     */
    private final Logger logger;

    /**
     * Constructor.
     * @param conn Connection object
     */
    public MyLibraryProvider (Connection conn) {
        this.connection = conn;
        Class<?> classObj = this.getClass();
        this.logger = LoggerFactory.getLogger(classObj);
        this.setUp();
    }

    /**
     * Add a file or a folder to the Library.
     * @param path Path to the file or folder
     * @param isFolder True if the path is a folder, false otherwise
     * @return 0 when added successfully, 1 if the path already exists. -1 if
     *         unable to add or if an error occurred
     */
    public final int addToLibrary (String path) {
        try {
            PreparedStatement count = this.connection
                    .prepareStatement("select count(*) as rowcount from MyLibrary "
                            + "where path = ?");
            count.setString(1, path);
            ResultSet r = count.executeQuery();
            r.next();
            int rows = r.getInt("rowcount");
            r.close();
            if (rows > 0) {
                return 1;
            }

            PreparedStatement prep = this.connection
                    .prepareStatement(
                    "insert into MyLibrary (path, date_added) values "
                    + "(?, ?);");
            prep.setString(1, path);
            DateTime date = new DateTime();
            prep.setLong(2, date.getCurrentTimeInMilliseconds());
            prep.addBatch();

            prep.executeBatch();
        } catch (SQLException e) {
            this.logger.error(ExceptionHandling.getStackTrace(e));
            return -1;
        }
        return 0;
    }

    /**
     * Remove a path from the library.
     * @param path Path to remove from library
     * @return True if removed, false otherwise
     */
    public final boolean removeFromLibrary (String path) {
        try {
            PreparedStatement prep = this.connection
                    .prepareStatement("delete from MyLibrary where path = ?;");
            prep.setString(1, path);
            prep.execute();
        } catch (SQLException e) {
            this.logger.error(ExceptionHandling.getStackTrace(e));
            return false;
        }
        return true;
    }

    /**
     * Fetch library.
     * @return ArrayList of LibraryItems
     */
    public final List<LibraryItem> getLibraryData () {
        ArrayList<LibraryItem> libraryData = new ArrayList<LibraryItem>();
        try {
            Statement stat = this.connection.createStatement();
            ResultSet rs = stat.executeQuery("select id, path, date_added, "
                    + "type from MyLibrary;");
            while (rs.next()) {
                String id = rs.getString("id");
                String path = rs.getString("path");
                //String dateCreated = rs.getString("date_added");
                long dateTime = rs.getLong("date_added");
                DateTime created = new DateTime();
                created.setTimeInMilliseconds(dateTime);
                String dateCreated = created.toString();
                String type = rs.getString("type");
                LibraryItem libItem = new LibraryItem(id, path, dateCreated,
                        type);
                libraryData.add(libItem);
            }
            rs.close();
        } catch (SQLException e) {
            this.logger.error(ExceptionHandling.getStackTrace(e));
        }
        return libraryData;
    }

    /**
     * Search library for given inputs.
     * @param searchTerm The search term
     * @return ArrayList of LibraryItem from the library that somehow matched
     *         the search term
     */
    public final ArrayList<LibraryItem> searchLibrary (String searchTerm) {
        ArrayList<LibraryItem> libraryData = new ArrayList<LibraryItem>();
        try {
            searchTerm = "%" + searchTerm + "%";
            PreparedStatement statement = this.connection.prepareStatement(
                    "select id, path, date_added, type from MyLibrary where "
                    + "path like ?;");
            statement.setString(1, searchTerm);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String id = rs.getString("id");
                String path = rs.getString("path");
                String dateCreated = rs.getString("date_added");
                String type = rs.getString("type");
                LibraryItem libItem = new LibraryItem(id, path, dateCreated,
                        type);
                libraryData.add(libItem);
            }
            rs.close();
        } catch (SQLException e) {
            this.logger.error(ExceptionHandling.getStackTrace(e));
        }
        return libraryData;
    }

    /**
     * Create MyLibrary table.
     */
    public void setUp () {
        try {
            Statement stat = this.connection.createStatement();
            stat.execute("create table if not exists MyLibrary "
                    + "(id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "path, date_added, type, valid, checksum);");
        } catch (SQLException e) {
            this.logger.error(ExceptionHandling.getStackTrace(e));
        }
    }

    /**
     * Drop tables and recreate them.
     */
    public void tearDown () {
        try {
            Statement stat = this.connection.createStatement();
            stat.executeUpdate("drop table if exists MyLibrary;");
            this.setUp();
        } catch (SQLException sqle) {
            this.logger.error("An error occurred drop MyLibrary table", sqle);
        }
    }

}
