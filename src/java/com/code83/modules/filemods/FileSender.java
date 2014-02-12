package com.code83.modules.filemods;

import com.code83.modules.communication.Responder;
import com.code83.utils.messages.MessageFactory;
import com.code83.utils.messages.Packet;


/**
 * This class is responsible for sending files to other nomads on the
 * network.
 * 
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version 0.1 SVN: $Id: FileSender.java 865 2011-12-15 03:35:16Z mngazimb $
 * @since 0.1
 * @see PacketReceiver
 */
public class FileSender extends Thread {

    /**
     * The file ID. This will probably be some kind of hash value eg, MD5.
     */
    //private String fileId;
    //private String receipient;

    /**
     * Constructor.
     * @param fId The file ID
     * @param receipient The nomad the file needs to be sent to
     */
    public FileSender (String fId, String receipient) {
        super();
        this.setName("File Sender Thread ID [" + this.getId() + "]");
    }

    /**
     * Run thread.
     */
    public void run () {
        // 1. Look up the file ID in the db.
        // 2. Get file path on system and use to construct packets that will
        // be sent on the network.

        // The following may need to be done from within a loop for all the
        // packet.
        Packet packet = MessageFactory.createPacket();
        // Set the receiver. This may end up being a Nomad ID once my jxta 
        // oriented refactor is complete. 
        packet.setReceiver(null);
        Responder.instance().send(packet);
        // FIXME complete this class
    }

}
