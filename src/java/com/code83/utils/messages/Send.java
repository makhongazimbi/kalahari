package com.code83.utils.messages;


/**
 * A message containing the ID of the file for which to ask another nomad to
 * send.
 *
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version 0.1 SVN: $Id: Send.java 883 2012-09-11 22:31:48Z mngazimb $
 * @since 0.1
 * @see Message, {@link DefaultMessage}
 */
public class Send extends Message<String> {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 5928149132043103513L;
    /**
     * The file ID to send.
     */
    private String fileId;

    /**
     * @return The file ID.
     */
    @Override
    public String getPayload () {
        return this.fileId;
    }

    /**
     * Set the file ID.
     * @param id The id
     */
    @Override
    public void setPayload (String id) {
        this.fileId = id;
    }

    /**
     * @return The type of message.
     */
    @Override
    public String toString () {
        return "SEND";
    }

}
