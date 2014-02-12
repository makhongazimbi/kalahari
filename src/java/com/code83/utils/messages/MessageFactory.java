package com.code83.utils.messages;

import com.code83.modules.status.Status;

/**
 * A factory class that creates instances of the appropriate
 * Message subtype.
 *
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version 0.1 SVN: $Id: MessageFactory.java 895 2012-09-13 23:07:35Z mngazimb $
 * @since 0.1
 */
public final class MessageFactory {

    /**
     * Explicit default constructor.
     */
    private MessageFactory () {
    }

    /**
     *
     * @return a Packet object.
     */
    public static Packet createPacket () {
        Packet packet = new Packet();
        MessageFactory.setMessageUnicastUrn(packet);
        return packet;
    }

    /**
     *
     * @return a HeartBeat object.
     */
    public static HeartBeat createHeartBeat () {
        HeartBeat heartBeat = new HeartBeat();
        MessageFactory.setMessageUnicastUrn(heartBeat);
        return heartBeat;
    }

    /**
     *
     * @return a GoodBye object.
     */
    public static GoodBye createGoodBye () {
        GoodBye goodBye = new GoodBye();
        MessageFactory.setMessageUnicastUrn(goodBye);
        return goodBye;
    }

    /**
     *
     * @return a Request message
     */
    public static Request createRequest () {
        Request request = new Request();
        int requestId = Status.getInstance().getRequestStatus()
                .getRequestId();
        request.setRequestId(requestId);
        MessageFactory.setMessageUnicastUrn(request);
        return request;
    }

    /**
     * @param receiverUrn The receiver URN
     * @return a Reply message
     */
    public static Reply createReply (String receiverUrn) {
        Reply reply = new Reply();
        reply.setReceiverUrn(receiverUrn);
        MessageFactory.setMessageUnicastUrn(reply);
        return reply;
    }

    /**
     * Create a send message.
     * @return a Send message
     */
    public static Send createSend () {
        Send send = new Send();
        MessageFactory.setMessageUnicastUrn(send);
        return send;
    }

    /**
     * Create a ping message.
     * @return a Ping message
     */
    public static Ping createPing () {
        Ping ping = new Ping();
        MessageFactory.setMessageUnicastUrn(ping);
        return ping;
    }

    /**
     * Set the unicast URN on a message.
     * @param message Message to set URN on
     */
    protected static void setMessageUnicastUrn (Message message) {
        String urn = Status.getInstance().getNomadStatus()
                .getUnicastUrn();
        message.setUnicastUrn(urn);
    }

}
