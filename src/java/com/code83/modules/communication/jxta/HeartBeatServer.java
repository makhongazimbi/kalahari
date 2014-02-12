package com.code83.modules.communication.jxta;

import java.io.IOException;
import java.util.Enumeration;

import net.jxta.discovery.DiscoveryEvent;
import net.jxta.discovery.DiscoveryListener;
import net.jxta.discovery.DiscoveryService;
import net.jxta.document.Advertisement;
import net.jxta.document.AdvertisementFactory;
import net.jxta.id.IDFactory;
import net.jxta.peergroup.PeerGroupID;
import net.jxta.protocol.DiscoveryResponseMsg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class sends out intermittent advertisements so that this
 * Nomad can be discovered on the network.
 *
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version $Id: HeartBeatServer.java 903 2012-09-14 22:10:04Z mngazimb $
 * @since 0.1
 */
public class HeartBeatServer extends Thread implements
        DiscoveryListener {

    /**
     * Logger.
     */
    private final Logger logger = LoggerFactory
            .getLogger(HeartBeatServer.class);

    /**
     * Discovery service.
     */
    private transient final DiscoveryService discovery;

    /**
     * FIXME All constants should be declared static final and screaming
     * CAPS unless decide to read them from DB.
     * The lifetime in milliseconds of an advertisement.
     */
    private final long lifetime = 60 * 2 * 1000L;

    /**
     * The expiration time of the advertisement in milliseconds.
     */
    private final long expiration = 60 * 2 * 1000L;

    /**
     * The wait time in milliseconds between sending advertisements.
     */
    private final long waittime = 60 * 1 * 1000L;
    /**
     * FIXME read from config
     */
    private int maxPublishErrors = 5;

    /**
     * Publishing messages.
     */
    private boolean publishing;

    /**
     * Constructor for the DiscoveryServer
     */
    public HeartBeatServer () throws Exception {
        super("Kalahari Heartbeat Server");
        Setup setup = Setup.getInstance();
        this.discovery = setup.getPeerGroup().getDiscoveryService();
        if (null == this.discovery) {
            this.logger.error("Discovery service is null");
        }
    }

    /**
     * create a new pipe adv, publish it for 2 minute network time,
     * sleep for 3 minutes, then repeat
     */
    @Override
    public final void run () {
        this.logger.info("Starting heartbeat server");
        this.publishing = true;
        int errors = 0;
        while (this.publishing) {
            try {
                Advertisement heartbeat
                        = HeartBeatServer.getHeartbeat();
                this.discovery.publish(heartbeat, this.lifetime,
                        this.expiration);
                this.discovery.remotePublish(heartbeat,
                        this.expiration);
                Thread.sleep(this.waittime);
            } catch (InterruptedException ie) {
                this.logger.error("Error during thread sleep", ie);
            } catch (IOException ioe) {
                errors += 1;
                this.logger.error("Discovery publish error", ioe);
                if (errors > this.maxPublishErrors) {
                    this.logger.error("Maximum publish errors occurred, " +
                            "stopping heartbeat server.");
                    // FIXME notify user that something very bad happened
                    this.stopServer();
                }
            }
        }
    }

    /**
     * Stop publishing heart beats.
     */
    public void stopServer () {
        this.logger.info("Stopping heartbeat server");
        this.publishing = false;
    }

    /**
     * This method is called whenever a discovery response is
     * received, which are either in response to a query we sent, or
     * a remote publish by another node.
     *
     * @param ev The discovery event
     */
    public void discoveryEvent (DiscoveryEvent ev) {
        DiscoveryResponseMsg res = ev.getResponse();
        Advertisement adv;
        Enumeration<Advertisement> en = res.getAdvertisements();

        if (en != null) {
            while (en.hasMoreElements()) {
                adv = en.nextElement();
                this.logger.debug(
                        "Heartbeat Server discovery event advertisement: [" +
                        adv + "]");
            }
        }
    }

    /**
     * Creates a pipe advertisement
     * @return a Pipe Advertisement
     */
    public static Advertisement getHeartbeat () {
        PeerGroupID groupId = PeerGroupID.defaultNetPeerGroupID;
        AdvertisementFactory.registerAdvertisementInstance(
                JxtaHeartbeat.getAdvertisementType(),
                new JxtaHeartbeat.Instantiator());
        JxtaHeartbeat heartbeat = new JxtaHeartbeat();
        heartbeat.setID(IDFactory.newPipeID(groupId));
        return heartbeat;
    }

}
