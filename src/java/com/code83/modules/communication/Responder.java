package com.code83.modules.communication;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;

import net.jxta.peergroup.PeerGroup;
import net.jxta.protocol.PipeAdvertisement;
import net.jxta.socket.JxtaMulticastSocket;
import net.jxta.socket.JxtaSocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.code83.modules.auxilliary.Shutdown;
import com.code83.modules.communication.jxta.HeartBeatServer;
import com.code83.modules.communication.jxta.MulticastListener;
import com.code83.modules.communication.jxta.Setup;
import com.code83.modules.communication.jxta.UnicastListener;
import com.code83.modules.status.Status;
import com.code83.ui.gui.commands.Command;
import com.code83.ui.gui.commands.CommandRegister;
import com.code83.utils.messages.Message;
import com.code83.utils.messages.MessageFactory;


/**
 * This class responds to messages from other nomods on the network.
 *
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version 0.1 SVN: $Id: Responder.java 886 2012-09-12 16:54:37Z mngazimb $
 * @since 0.1
 * @see Listener
 */
public class Responder implements Shutdown {

    /**
     * Responder.
     */
    private static Responder responder = null;

    /**
     * Heart beat server.
     */
    private HeartBeatServer hbs;

    /**
     * Logger.
     */
    private final Logger logger = LoggerFactory.getLogger(Responder.class);

    /**
     * Explicit default constructor.
     */
    private Responder () {
        try {
            this.hbs = new HeartBeatServer();
        } catch (Exception e) {
            // FIXME alert user of error
            Responder.this.logger.error(
                    "Error starting Heart Beat Server thread", e);
        }
    }

    /**
     * @return An instance of this class.
     */
    public static Responder instance () {
        if (Responder.responder == null) {
            Responder.responder = new Responder();
        }
        return Responder.responder;
    }

    /**
     * Start sending heart beats.
     */
    public void start () {
        this.hbs.start();
    }

    /**
     * This method stops heart beats from being placed on the network by this
     * nomad thereby rendering it invisible.
     */
    public void setInvisibleMode () {
        this.hbs.stopServer();
    }

    /**
     * This method restarts heart beat message being sent from this nomad
     * thereby making it visible on the network.
     */
    public void setVisibleMode () {
        this.hbs.start();
    }

    /**
     * Basically a multicast to all nomads on the network.
     * @param message The message to send
     */
    public void sendToAll (Message<?> message) {
        this.logger.debug("Multicasting (sendToAll) message: [" +
                message.toString() + "], Payload [" + message.getPayload() +
                "]");
        Setup setup = Setup.getInstance();
        PeerGroup peerGroup = setup.getPeerGroup();

        try {
            byte[] messageBytes = message.getBytes();
            DatagramPacket packet = new DatagramPacket(messageBytes,
                    messageBytes.length);
            JxtaMulticastSocket mcastSocket = new JxtaMulticastSocket(
                    peerGroup, MulticastListener.getSocketAdvertisement());
            this.logger.debug("Multicast Local Address [" +
                    mcastSocket.getLocalAddress() +
                    "], Multicast Local Socket Address [" +
                    mcastSocket.getLocalSocketAddress() + "]");
            mcastSocket.send(packet);
            mcastSocket.close();
        } catch (IOException ioe) {
            this.logger.error("Error multicasting message", ioe);
            CommandRegister commands = CommandRegister.instance();
            Command showNoNetwork = commands.getCommand("no_network");
            if (showNoNetwork != null) {
                showNoNetwork.execute();
            }
        }
    }

    /**
     * A unicast to a particular nomad.
     * @param message The message to be sent.
     */
    public void send (Message<?> message) {
        this.logger.debug("Sending message [" + message + "], Payload [" +
                message.getPayload() + "]");
        //String myUrn = Status.getInstance().getNomadStatus().getUnicastUrn();
        String destinationUrn = message.getReceiverUrn();

        PipeAdvertisement pipeAdv = UnicastListener
                .createSocketAdvertisement(destinationUrn);
        // Must now set my urn so the receiver knows who the ping came from.
        try {
            JxtaSocket socket = new JxtaSocket(Setup.getInstance()
                    .getPeerGroup(),
                    // no specific peerid
                    null, pipeAdv,
                    // FIXME add to configuration
                    // connection timeout: 5 seconds
                    5000,
                    // reliable connection
                    true);

            // get the socket output stream
            OutputStream out = socket.getOutputStream();

            ObjectOutputStream output = new ObjectOutputStream(out);
            output.writeObject(message);
            out.flush();
        } catch (IOException io) {
            this.logger.error("Error sending message", io);
            return;
        }
    }

    /**
     * Shutdown the responder.
     */
    public void shutdown () {
        this.logger.info("Shutting down responder");
        this.setInvisibleMode();
        this.sendToAll(MessageFactory.createGoodBye());
    }

}
