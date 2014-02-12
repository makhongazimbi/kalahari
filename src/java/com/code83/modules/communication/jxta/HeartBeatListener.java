package com.code83.modules.communication.jxta;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.UUID;

import net.jxta.discovery.DiscoveryEvent;
import net.jxta.discovery.DiscoveryListener;
import net.jxta.discovery.DiscoveryService;
import net.jxta.document.Advertisement;
import net.jxta.protocol.DiscoveryResponseMsg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.code83.modules.status.Status;
import com.code83.utils.messages.HeartBeat;
import com.code83.utils.messages.MessageFactory;


/**
 * This module listens for discovery messages sent by other Nomads.
 *
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version $Id: HeartBeatListener.java 904 2012-09-14 22:31:20Z mngazimb $
 * @since 0.1
 */
public class HeartBeatListener implements DiscoveryListener {

    /**
     * Logger.
     */
    private Logger logger = LoggerFactory
            .getLogger(HeartBeatListener.class);
    private transient final DiscoveryService discovery;
    private static final long waittime = 20 * 1000L;

    /**
     * Constructor for the DiscoveryClient
     * @throws IOException
     */
    public HeartBeatListener () throws Exception {
        Setup setup = Setup.getInstance();
        this.discovery = setup.getPeerGroup().getDiscoveryService();
    }

    /**
     * loop forever attempting to discover advertisements every minute
     */
    public void start () {
        // Add ourselves as a DiscoveryListener for DiscoveryResponse
        // events
        this.discovery.addDiscoveryListener(this);
        while (true) {
            // Listen for heart beat messages
            this.discovery.getRemoteAdvertisements(null,
                    DiscoveryService.ADV, "name", "Heartbeat", 1,
                    null);
            // Wait before sending the next heart beat request message
            try {
                Thread.sleep(HeartBeatListener.waittime);
            } catch (InterruptedException ie) {
                this.logger.error("Thread interrupted", ie);
            }
        }
    }

    /**
     * This method is called whenever a discovery response is received,
     * which are either in response to a query we sent, or a remote
     * publish by another node
     * @param ev The discovery event. The discovery event
     */
    public void discoveryEvent (DiscoveryEvent ev) {
        DiscoveryResponseMsg res = ev.getResponse();
        // Get the responding peer's advertisement
        this.logger.debug("Got a Discovery Response: ["
                + res.getResponseCount() + " elements] from peer: ["
                + ev.getSource() + "]");

        Advertisement adv;
        Enumeration<Advertisement> en = res.getAdvertisements();

        if (en != null) {
            int count = 0;
            while (en.hasMoreElements()) {
                adv = en.nextElement();
                HeartBeat heartBeat = this.toHeartBeat(adv);
                if (null == heartBeat) {
                    continue;
                }
                this.logger.debug(
                        "Adding discovered nomad heartbeat to status");
                Status.getInstance().getNomadStatus().addNomad(heartBeat);
                count += 1;
            }
        }
    }

    /**
     * Convert an advertisement into a heart beat.
     */
    public HeartBeat toHeartBeat (Advertisement adv) {
        if (adv instanceof JxtaHeartbeat) {
            JxtaHeartbeat h = (JxtaHeartbeat) adv;
            HeartBeat heartbeat = h.getHeartBeat();
            UUID id = UUID.fromString(h.getSenderId());
            heartbeat.setSenderId(id);
            return heartbeat;
        }
        this.logger.warn("Failed to create a heart beat message");
        return null;
    }

}
