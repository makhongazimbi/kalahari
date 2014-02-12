package com.code83.utils.messages;

/**
 * GoodBye message that is sent by a nomad intending to leave
 * the network.
 *
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version $Id: GoodBye.java 883 2012-09-11 22:31:48Z mngazimb $
 * @since 0.1
 * @see Message, {@link DefaultMessage}
 *
 */
public class GoodBye extends Message<String> {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 6663226655538161691L;

    /**
     * Messgae payload.
     */
    private static final String payload = "GOOD_BYE";

    /**
     * Get the payload.
     * @return Goodbye message.
     */
    @Override
    public String getPayload () {
      return GoodBye.payload;
    }

    /**
     * Set the payload. This method does nothing.
     * @param load Payload
     */
    @Override
    public void setPayload (String load) {
    }

    /**
     * Get the string representation of this message.
     * @return Goodbye message
     */
    public String toString () {
      return GoodBye.payload;
    }

}
