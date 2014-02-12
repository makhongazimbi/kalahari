package com.code83.examples;

import com.code83.utils.messages.HeartBeat;
import com.code83.utils.messages.Message;
import com.code83.utils.messages.MessageFactory;
import com.code83.utils.messages.Packet;

/**
 * An example class showing how to create Message object via the
 * MessageFactory class.   MessageExample.java 650 2009-07-10 06:03:16Z
 * mngazimb $
 * @author makho
 * @version $Id: MessageExample.java 865 2011-12-15 03:35:16Z mngazimb $
 */
public class MessageExample {
    public static void main (String[] args) {
        Message<?> heartbeat = MessageFactory.createHeartBeat();
        Message<?> packet = MessageFactory.createPacket();

        if (heartbeat instanceof HeartBeat)
            System.out.println("heartbeat is a HeartBeat.");

        if (packet instanceof Packet)
            System.out.println("packet is a Packet.");

        if (heartbeat instanceof Message<?>)
            System.out.println("heartbeat is a Message.");

        if (packet instanceof Message<?>)
            System.out.println("packet is a Message.");

        if (heartbeat instanceof Packet)
            System.out.println("heartbeat is a Packet.");

        if (packet instanceof HeartBeat)
            System.out.println("packet is a HeartBeat.");
    }
}
