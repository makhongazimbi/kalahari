package com.code83.data.provider.sqlite;

import java.awt.Dimension;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.code83.utils.ExceptionHandling;
import com.code83.utils.Splash;

/**
 * Access stored data for Kalahari settings.
 * 
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version $Id: SettingsProvider.java 865 2011-12-15 03:35:16Z mngazimb $
 * @since 0.1
 */
public class SettingsProvider implements ProviderInterface {

    /**
     * Database connection.
     */
    private final Connection connection;

    /**
     * Logger.
     */
    private static Logger logger;

    /**
     * Constructor.
     *
     * @param conn Database connection
     */
    public SettingsProvider (Connection conn) {
        this.connection = conn;
        Class<?> classObj = this.getClass();
        SettingsProvider.logger = LoggerFactory.getLogger(classObj);
        this.setUp();
    }

    /**
     * Save window dimensions.
     * @param x X coordinate
     * @param y Y coordinate
     * @param width Window width
     * @param height Windonw height
     */
    public void saveWindowDimensions (int x, int y, int width, int height) {
        this.saveDimension("WINDOW_X_LOCATION", x);
        this.saveDimension("WINDOW_Y_LOCATION", y);
        this.saveDimension("WINDOW_HEIGHT", height);
        this.saveDimension("WINDOW_WIDTH", width);
    }

    /**
     * Save a single dimension.
     * @param setting Setting name
     * @param value Value of the dimension
     */
    private void saveDimension (String setting, int value) {
        try {
            PreparedStatement prep = this.connection
                    .prepareStatement("update Settings set value = ? where setting = ?;");

            prep.setInt(1, value);
            prep.setString(2, setting);
            prep.addBatch();

            prep.executeBatch();
        } catch (SQLException e) {
            SettingsProvider.logger.error(ExceptionHandling.getStackTrace(e));
        }
    }

    /**
     * Get window dimensions.
     * @return Array of dimensions.
     */
    public int[] getWindowDimensions () {
        int x_location = Integer.parseInt(this.getSetting("WINDOW_X_LOCATION"));
        int y_location = Integer.parseInt(this.getSetting("WINDOW_Y_LOCATION"));
        int height = Integer.parseInt(this.getSetting("WINDOW_HEIGHT"));
        int width = Integer.parseInt(this.getSetting("WINDOW_WIDTH"));

        int[] dimensions = { x_location, y_location, width, height };
        return dimensions;
    }

    /**
     * Get a setting from the database.
     * @param setting Setting name
     * @return Setting value
     */
    private String getSetting (String setting) {
        String value = "";
        try {
            PreparedStatement prep = this.connection
                    .prepareStatement("select value from Settings where setting = ?;");
            prep.setString(1, setting);
            ResultSet rs = prep.executeQuery();

            while (rs.next()) {
                value = rs.getString("value");
            }
            rs.close();
        } catch (SQLException e) {
            SettingsProvider.logger.error(ExceptionHandling.getStackTrace(e));
        }
        return value;
    }

    /**
     * Get the currently defined Kalahari GUI theme
     * @return Theme name
     */
    public String getTheme () {
        return this.getSetting("THEME");
    }

    /**
     * Set the current Kalahari GUI theme
     * @param theme The theme name
     */
    public void setTheme (String theme) {
        try {
            PreparedStatement prep = this.connection
                    .prepareStatement("update Settings set value = ? where setting = 'THEME';");

            prep.setString(1, theme);
            prep.addBatch();

            prep.executeBatch();
        } catch (SQLException e) {
            SettingsProvider.logger.error(ExceptionHandling.getStackTrace(e));
        }
    }

    /**
     * Create and populate settings table.
     */
    public void setUp () {
        // create tables if they do not exist and insert default data
        try {
            Statement stat = this.connection.createStatement();
            stat.execute("create table if not exists Settings "
                    + "(setting PRIMARY KEY DESC, value);");

            Statement statement = this.connection.createStatement();
            statement.execute("select count(*) as count from Settings;");
            ResultSet rs = statement.getResultSet();
            rs.next();
            int settingsCount = rs.getInt("count");
            if (settingsCount < 1) {
                PreparedStatement prep = this.connection
                        .prepareStatement("insert into Settings (setting, value) "
                                + "values (?, ?);");

                // Create Nomad ID
                UUID nomadId = UUID.randomUUID();
                prep.setString(1, "NOMAD_ID");
                prep.setString(2, nomadId.toString());
                prep.addBatch();

                Dimension screen = Splash.getPrimaryScreenDimension();
                int width = new Double(screen.getWidth()).intValue();
                int height = new Double(screen.getHeight()).intValue();

                prep.setString(1, "WINDOW_X_LOCATION");
                prep.setInt(2, 0);
                prep.addBatch();
                prep.setString(1, "WINDOW_Y_LOCATION");
                prep.setInt(2, 0);
                prep.addBatch();
                prep.setString(1, "WINDOW_WIDTH");
                prep.setInt(2, width);
                prep.addBatch();
                prep.setString(1, "WINDOW_HEIGHT");
                prep.setInt(2, height);
                prep.addBatch();
                prep.setString(1, "THEME");
                prep.setString(2, "COOL_TANGO");
                prep.addBatch();

                prep.executeBatch();
            }

        } catch (SQLException e) {
            SettingsProvider.logger.error(ExceptionHandling.getStackTrace(e));
        }
    }

    /**
     * Drop tables and recreate them.
     */
    public void tearDown () {
        try {
            Statement stat = this.connection.createStatement();
            stat.executeUpdate("drop table if exists Settings;");
            this.setUp();
        } catch (SQLException sqle) {
            SettingsProvider.logger.error(
                    "An error occurred drop Settings table", sqle);
        }
    }

}
