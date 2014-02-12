package com.code83.utils.messages;

import com.code83.modules.filemods.FileDescriptor;

/**
 * Search reply message.
 *
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version 0.1 SVN: $Id: Reply.java 883 2012-09-11 22:31:48Z mngazimb $
 * @since 0.1
 * @see Message, {@link DefaultMessage}
 */
public class Reply extends Message<FileDescriptor> {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -3500770254135449032L;
    /**
     * File descriptor.
     */
    private FileDescriptor payload;
    /**
     * Search ID.
     */
    private int searchId;

    /**
     * Constructor.
     */
    public Reply () {
        super();
    }

    /**
     * Get the message payload.
     * @return Message payload
     */
    @Override
    public FileDescriptor getPayload () {
        return this.payload;
    }

    /**
     * Set the message payload.
     * @param load Set the message payload.
     */
    @Override
    public void setPayload (FileDescriptor load) {
        this.payload = load;
    }

    /**
     * Get the search ID.
     * @return Search ID
     */
    public int getSearchId () {
        return this.searchId;
    }

    /**
     * Set the search ID.
     * @param id Search ID
     */
    public void setSearchId (int id) {
        this.searchId = id;
    }

    /**
     * String representation of this message.
     * @return Message type
     */
    @Override
    public String toString () {
        return "REPLY";
    }

}
