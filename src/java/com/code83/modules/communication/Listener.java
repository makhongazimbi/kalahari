package com.code83.modules.communication;

import java.io.IOException;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.code83.modules.auxilliary.Shutdown;
import com.code83.modules.communication.jxta.HeartBeatListener;
import com.code83.modules.communication.jxta.MulticastListener;
import com.code83.modules.communication.jxta.UnicastListener;

/**
 * FIXME does not need to be a singleton?
 *
 * This class continuously monitors the network for messages from other nomads.
 * This class implements the Singleton design pattern.
 *
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version 0.1 SVN: $Id: Listener.java 896 2012-09-13 23:59:49Z mngazimb $
 * @since 0.1
 * @see Responder
 */
public class Listener implements Shutdown {

    /**
     * Listener.
     */
    private static Listener listener = null;

    /**
     * Multicast listener.
     */
    private final MulticastListener multiCastListener
            = new MulticastListener();

    /**
     * Unicast listener.
     */
    private final UnicastListener unicastListener
            = new UnicastListener();

    /**
     * Logger.
     */
    private Logger logger = LoggerFactory.getLogger(Listener.class);

    /**
     * @throws IOException
     *             if an error occurs while opening a socket.
     */
    private Listener () {
        Runnable hblThread = new Runnable() {
            public void run () {
                try {
                    HeartBeatListener hbl = new HeartBeatListener();
                    hbl.start();
                } catch (Exception e) {
                    Listener.this.logger.error(
                            "Error starting heartbeat listener", e);
                }
            }
        };
        new Thread(hblThread, "Kalahari Heartbeat Listener").start();
    }

    /**
     * Start the listener threads.
     */
    public void start () {
        this.logger.info("Starting listeners.");
        this.unicastListener.start();
        this.multiCastListener.start();
    }

    /**
     * This method is the only way to create an instance of this class.
     *
     * @return a Listener object.
     * @throws IOException
     *             if an error occurs while opening a socket.
     */
    public static Listener instance () {
        if (Listener.listener == null) {
            Listener.listener = new Listener();
        }
        return Listener.listener;
    }

    /**
     * Shutdown listener.
     */
    public void shutdown () {
        this.logger.info("Shutting down listeners");
        this.unicastListener.stopListener();
        this.multiCastListener.stopListener();
    }

}
