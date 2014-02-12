package com.code83.utils.messages;

import java.io.File;

/**
 * A packet object that can be used to send Files across the Kalahari
 * network.
 *
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version 0.1 SVN: $Id: Packet.java 883 2012-09-11 22:31:48Z mngazimb $
 * @since 0.1
 * @see DefaultMessage, {@link Message}
 *
 */
public class Packet extends Message<File> {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -3337927312713394203L;
    /**
     * File. TODO should this really be a byte array?
     */
    private File file;

    /**
     * Constructor.
     */
    public Packet () {
        super();
    }

    /**
     * Get the payload.
     */
    @Override
    public File getPayload () {
        return this.file;
    }

    /**
     * Set the payload.
     */
    @Override
    public void setPayload (File load) {
        this.file = load;
    }

    /**
     * Type of message.
     * @return Message type
     */
    @Override
    public String toString () {
        return "PACKET";
    }
}
