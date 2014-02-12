package com.code83.utils.messages;

import com.code83.modules.filemods.FileDescriptor;

/**
 * A search request object.
 *
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version SVN 0.1 $Id: Request.java 883 2012-09-11 22:31:48Z mngazimb $
 * @since 0.1
 * @see Message, {@link DefaultMessage}
 */
public class Request extends Message<FileDescriptor> {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -9015312257816293907L;

    /**
     * The search file attributes.
     */
    private FileDescriptor payload = null;

    /**
     * Request ID.
     */
    private int requestId = -7;

    /**
     * Default constructor.
     */
    public Request () {
        super();
    }

    /**
     * Set the request ID.
     * @param id Request ID
     */
    public void setRequestId (int id) {
        this.requestId = id;
    }

    public int getRequestId () {
        return this.requestId;
    }

    @Override
    public FileDescriptor getPayload () {
        return this.payload;
    }

    @Override
    public void setPayload (FileDescriptor load) {
        this.payload = load;
    }

    @Override
    public String toString () {
        return "REQUEST";
    }

    /**
     * Get UI displayable print print string.
     *
     * @return String
     */
    public String getPrettyPrint () {
        return "ID [" + this.requestId + "] Search Phrase: ["
                + this.payload.getSearchString() + "] Time ["
                + this.getTimeStamp() + "]";
    }

}
