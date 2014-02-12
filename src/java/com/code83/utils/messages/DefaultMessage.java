package com.code83.utils.messages;

import java.io.IOException;
import java.net.InetAddress;
import java.util.UUID;

/**
 * An interface for a Kalahari network message object.
 *
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version 0.1 SVN: $Id: DefaultMessage.java 883 2012-09-11 22:31:48Z mngazimb $
 * @since 0.1
 */
public interface DefaultMessage<T> {

    public void setReceiver (InetAddress receiver);

    public InetAddress getSenderIp ();

    public InetAddress getReceiver ();

    public void setPayload (T load);

    public T getPayload ();

    public UUID getSenderId ();

    public byte[] getBytes () throws IOException;

    /*
     * perhaps add a function that is able to return an integer version
     * number for this client so that compatibility can be determined with
     * other clients.
     */
}
