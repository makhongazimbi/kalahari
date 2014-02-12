package com.code83.utils.messages;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.net.InetAddress;
import java.util.Calendar;
import java.util.UUID;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.code83.modules.status.Status;

/**
 * A class that represents a Message object.
 *
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version $Id: Message.java 895 2012-09-13 23:07:35Z mngazimb $
 * @since 0.1
 * @see DefaultMessage
 */
public abstract class Message<T> implements DefaultMessage<T>,
        Serializable {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = -8242894830724802933L;

    /**
     * Get the timestamp indicating when this message was created.
     */
    private String timeStamp;

    /**
     * Sender IP address
     */
    private InetAddress senderAddress;

    /**
     * Receiver IP address
     */
    private InetAddress receiverAddress;

    /**
     * Sender UID
     */
    protected UUID senderId;

    /**
     * Receiver UID
     */
    private UUID receiverId;

    /**
     * Unicast URN
     */
    private String unicastUrn;

    /**
     * Receiver URN
     */
    private String receiverUrn;

    /**
     * Default constructor
     */
    public Message () {
        this.init();
    }

    /**
     * Constructor that accepts an IP address
     *
     * @param receiver
     *            Receiver IP address
     */
    public Message (InetAddress receiver) {
        this.receiverAddress = receiver;
        this.init();
    }

    protected void init () {
        this.setSenderId();
        this.setTimeStamp();
    }

    /**
     * Set the time stamp for this message.
     */
    protected void setTimeStamp () {
        Calendar cal = Calendar.getInstance();

        // Get the components of the time
        // int hour12 = cal.get(Calendar.HOUR); // 0..11
        int hour24 = cal.get(Calendar.HOUR_OF_DAY); // 0..23
        int min = cal.get(Calendar.MINUTE); // 0..59
        int sec = cal.get(Calendar.SECOND); // 0..59
        // int ms = cal.get(Calendar.MILLISECOND); // 0..999
        // int ampm = cal.get(Calendar.AM_PM); // 0=AM, 1=PM

        this.timeStamp = hour24 + ":" + min + ":" + sec;
    }

    public void setReceiver (InetAddress receiver) {
        this.receiverAddress = receiver;
    }

    /**
     * Get the unicast urn.
     *
     * @return Urn
     */
    public String getUnicastUrn () {
        return this.unicastUrn;
    }

    /**
     * Set the unicast urn.
     *
     * @param urn
     */
    public void setUnicastUrn (String urn) {
        this.unicastUrn = urn;
    }

    /**
     * Get receiver URN.
     * @return Urn
     */
    public String getReceiverUrn () {
        return this.receiverUrn;
    }

    /**
     * Set receiver URN.
     * @param urn
     */
    public void setReceiverUrn (String urn) {
        this.receiverUrn = urn;
    }

    /**
     * Set the sender UID. This method is applied on every message
     * object created to associate it with a particular nomad.
     */
    protected final void setSenderId () {
        this.senderId = Status.getInstance().getNomadStatus()
                .getNomadId();
        this.senderAddress = Status.getInstance().getNetworkStatus()
                .getNomadIp();
    }

    /**
     * Get the senders IP address
     *
     * @return IP address
     */
    public InetAddress getSenderIp () {
        return this.senderAddress;
    }

    /**
     * Set the senders IP address
     *
     * @param ip
     *            IP address
     */
    public void setSenderIp (InetAddress ip) {
        this.senderAddress = ip;
    }

    /**
     * Get the receiver's IP address
     */
    public InetAddress getReceiver () {
        return this.receiverAddress;
    }

    /**
     * Set the UUID of the receiver.
     *
     * @param id
     *            ID of receiver
     */
    public void setReceivedId (UUID id) {
        this.receiverId = id;
    }

    /**
     * Get the UUID of the receiver.
     */
    public UUID getReceiverId () {
        return this.receiverId;
    }

    /**
     * Get the sender's UID
     *
     * @return UID
     */
    public UUID getSenderId () {
        return this.senderId;
    }

    /**
     * Set the payload
     *
     * @param payload
     */
    public abstract void setPayload (T load);

    /**
     * Get the payload
     *
     * @return Payload
     */
    public abstract T getPayload ();

    /**
     * Convert this object to the byte representation.
     *
     * @return array of bytes
     */
    public byte[] getBytes () throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(this);
        oos.flush();
        oos.close();

        bos.flush();
        bos.close();
        byte[] data = bos.toByteArray();
        return data;
    }

    /**
     * Converts an array of bytes back to its constituent object. The
     * input array is assumed to have been created from the original
     * object.
     * @param bytes Byte array to convert.
     * @return the associated object.
     */
    public static Object toObject (byte[] bytes) {
        Logger logger = LoggerFactory.getLogger(Message.class);
        Object object = new Object();
        try {
            ByteArrayInputStream byteArrayInStrm
                    = new ByteArrayInputStream(bytes);
            ObjectInputStream objInStrm
                    = new ObjectInputStream(byteArrayInStrm);
            object = objInStrm.readObject();
        } catch (StreamCorruptedException sce) {
            // This error occurs at every shutdown, so can be turned
            // down to avoid polluting logs.
            logger.info(
                    "Corrupted stream detected while trying to " +
                    "convert byte array to message", sce);
        } catch (IOException ioe) {
            logger.error(
                    "Error occurred converting byte array to object",
                    ioe);
        } catch (ClassNotFoundException cnfe) {
            logger.error(
                    "Error occurred converting byte array to object",
                    cnfe);
        }
        return object;
    }

    /**
     * String representation of this object
     */
    @Override
    public abstract String toString ();

    /***
     * Get the time stamp indicating when this message was created.
     *
     * @return String
     */
    public String getTimeStamp () {
        return this.timeStamp;
    }

}
