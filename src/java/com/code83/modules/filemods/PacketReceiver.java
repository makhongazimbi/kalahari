package com.code83.modules.filemods;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.code83.utils.messages.Packet;


/**
 * This class handles file packets that are received from other nomads.
 *
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version 0.1 SVN: $Id: PacketReceiver.java 865 2011-12-15 03:35:16Z mngazimb $
 * @since 0.1
 * @see FileSender
 */
public class PacketReceiver extends Thread {

    /**
     * File packets. Use a list for better memory management especially in
     * case the list of packets should grow grotesquely large. Better to use
     * in that particular case than an array based implementation such as 
     * vector.
     */
    private List<Packet> packets = Collections.synchronizedList(
            new LinkedList<Packet>());

    /**
     * Default constructor.
     */
    public PacketReceiver () {
    }

    /**
     * Add a new packet just recieved.
     */
    public void addPacket (Packet p) {
        this.packets.add(p);
        if (!this.isAlive()) {
            this.start();
        }
    }

    /**
     * Run thread.
     */
    public void run () {
        while (!this.packets.isEmpty()) {
            // Do whatever processing is required to the packets received.
            // Thread will naturally exit once the job is done.
            // FIXME complete
        }
    }
}
