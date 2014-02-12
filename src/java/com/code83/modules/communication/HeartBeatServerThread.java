package com.code83.modules.communication;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.code83.modules.communication.jxta.HeartBeatServer;

/**
 * Controller class for JXTA Heart beat server.
 *
 * @author Makho Ngazimbi
 * @version $Id: HeartBeatServerThread.java 882 2012-05-30 18:15:44Z mngazimb $
 * @since 0.1
 */
public class HeartBeatServerThread extends Thread {

    /**
     * Logger
     */
    private final Logger logger = LoggerFactory
            .getLogger(HeartBeatServerThread.class);

    /**
     * Heart beat server thread.
     */
    public HeartBeatServerThread () {
        super("Kalahari Heartbeat Server");
    }

    /**
     * Run thread
     */
    @Override
    public void run () {
        try {
            HeartBeatServer server = new HeartBeatServer();
            server.start();
        } catch (Exception e) {
            this.logger.error("Error creating network manaager", e);
        }
    }

}
