package com.code83.data.provider.sqlite;

/**
 * Interace that all provider classes must implement.
 * 
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version 0.1 SVN: $Id: ProviderInterface.java 847 2011-09-20 02:13:24Z
 *          mngazimb $
 * @since 0.1
 */
public interface ProviderInterface {

    /**
     * Create table required by a particular provider and perform any other
     * setup that's required such as populating the table.
     */
    void setUp ();

    /**
     * Drop tables and recreate them.
     */
    void tearDown ();

}
