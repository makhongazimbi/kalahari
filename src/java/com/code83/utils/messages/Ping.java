package com.code83.utils.messages;


/**
 * A message that can be unicast to a nomad to solicit a heart beat.
 *
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version 0.1 SVN: $Id: Ping.java 883 2012-09-11 22:31:48Z mngazimb $
 * @since 0.1
 * @see Message, {@link DefaultMessage}
 *
 */
public class Ping extends Message<String> {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 938367201020238278L;

    /**
     * Get the payload.
     */
    @Override
    public String getPayload () {
        return null;
    }

    /**
     * Set the payload
     */
    @Override
    public void setPayload (String load) {
    }

    /**
     * Type of message.
     * @return Message type
     */
    @Override
    public String toString () {
        return "PING";
    }

}
