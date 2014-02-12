package com.code83.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Utility class for Handling functionality involving dates and time.
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version $Id: DateTime.java 865 2011-12-15 03:35:16Z mngazimb $
 * @since 0.1
 */
public class DateTime {

    /**
     * Calendar object.
     */
    protected Calendar calendar;

    /**
     * Default constructor.
     */
    public DateTime () {
        long now = System.currentTimeMillis();
        this.calendar = Calendar.getInstance();
        this.calendar.setTimeInMillis(now);
    }

    /**
     * Get time in milliseconds since the UNIX epoch.
     * @return Time in milliseconds
     */
    public long getCurrentTimeInMilliseconds () {
        return this.calendar.getTimeInMillis();
    }

    /**
     * Set the time in milliseconds since the UNIX epoch.
     * @param millis Time in milliseconds
     */
    public void setTimeInMilliseconds (long millis) {
        this.calendar.setTimeInMillis(millis);
    }

    /**
     * Get a string representation of the time.
     */
    public String toString () {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return formatter.format(this.calendar.getTime());
    }

}
