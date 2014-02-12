package com.code83.modules.communication.jxta;

import java.io.File;
import java.io.IOException;

import net.jxta.exception.PeerGroupException;
import net.jxta.peergroup.PeerGroup;
import net.jxta.platform.NetworkManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for initializing JXTA. Uses a thread safe singleton class: solution of
 * Bill Pugh approach.
 * 
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version 0.1 SVN: $Id: Setup.java 865 2011-12-15 03:35:16Z mngazimb $
 * @since 0.1
 */
public class Setup {

    /**
     * Network manager.
     */
    private transient NetworkManager manager;

    /**
     * Logger.
     */
    private final Logger logger = LoggerFactory.getLogger(Setup.class);

    /**
     * Explicit default constructor.
     */
    private Setup () {
        // The following properties basically turn off JXTA logging which can
        // be rather noisy.
        System.setProperty("net.jxta.logging.Logging", "SEVERE");
        System.setProperty("net.jxta.level", "SEVERE");

        try {
            this.manager = new NetworkManager(NetworkManager.ConfigMode.ADHOC,
                    "Kalahari", new File(new File(".cache"), "Kalahari")
                            .toURI());
            this.manager.startNetwork();
        } catch (IOException ioe) {
            this.logger.error(
                    "An error occurred starting JXTA network manager", ioe);
        } catch (PeerGroupException pge) {
            this.logger.error("An error occurred joining JXTA peer group", pge);
        }
    }

    /**
     * SetupHolder is loaded on the first execution of Setup.instance() or the
     * first access to SingletonHolder.INSTANCE, not before.
     */
    private static class SetupHolder {
        private static final Setup INSTANCE = new Setup();
    }

    /**
     * This method needs to be synchronized to prevent multiple instances being
     * called by multiple threads. Threads known to access this objects are
     * HeartBeatListener and HeartBeatServer.
     * 
     * @return Setup
     */
    public static Setup getInstance () {
        return SetupHolder.INSTANCE;
    }

    /**
     * Stops the platform
     */
    public void stop () {
        // Stop JXTA
        this.manager.stopNetwork();
    }

    /**
     * Get the peer group.
     * 
     * @return
     */
    public PeerGroup getPeerGroup () {
        return this.manager.getNetPeerGroup();
    }

}