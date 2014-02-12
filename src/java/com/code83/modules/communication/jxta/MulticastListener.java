package com.code83.modules.communication.jxta;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

import net.jxta.document.AdvertisementFactory;
import net.jxta.id.IDFactory;
import net.jxta.peergroup.PeerGroup;
import net.jxta.pipe.PipeID;
import net.jxta.pipe.PipeService;
import net.jxta.protocol.PipeAdvertisement;
import net.jxta.socket.JxtaMulticastSocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.code83.modules.communication.RequestHandler;
import com.code83.modules.communication.Responder;
import com.code83.modules.status.Configure;
import com.code83.modules.status.Status;
import com.code83.utils.messages.GoodBye;
import com.code83.utils.messages.Message;
import com.code83.utils.messages.Request;


/**
 * Multicast listener.
 *
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version 0.1 SVN: $Id: MulticastListener.java 817 2010-08-13 21:26:53Z
 *          mngazimb $
 * @since 0.1
 */
public class MulticastListener extends Thread {

    /**
     * TODO: generate new unique ID. Unique ID for our multicast pipe
     * advertisement.
     */
    public final static String SOCKET_ID = "urn:jxta:uuid-59616261646162614E5047205032503393B5C2F6CA7A41FDB0F890173088E79404";

    /**
     * Logger.
     */
    private final Logger logger = LoggerFactory
            .getLogger(MulticastListener.class);

    /**
     * Listening.
     */
    private boolean listening = true;

    /**
     * Multicast socket.
     */
    private JxtaMulticastSocket mcastSocket;

    /**
     * The request message handler.
     */
    private final RequestHandler requestHandler = new RequestHandler();

    /**
     * Constructor.
     */
    public MulticastListener () {
        super("Kalahari Multicast Listener");

        Setup setup = Setup.getInstance();
        PeerGroup peerGroup = setup.getPeerGroup();

        this.logger.info("Starting Multicast Listener");
        this.logger.info("Creating JxtaMulticastSocket");
        this.mcastSocket = null;
        try {
            this.mcastSocket = new JxtaMulticastSocket(peerGroup,
                    MulticastListener.getSocketAdvertisement());
            // This socket should never time out!
            this.mcastSocket.setSoTimeout(0);
            this.logger.info("LocalAddress :"
                    + this.mcastSocket.getLocalAddress());
            this.logger.info("LocalSocketAddress :"
                    + this.mcastSocket.getLocalSocketAddress());
        } catch (IOException ioe) {
            this.logger.error("Error creating multicast socket", ioe);
            // FIXME alert user
        }

        this.requestHandler.start();
    }

    /**
     * Creates a Socket Pipe Advertisement with the pre-defined pipe ID
     *
     * @return a socket PipeAdvertisement
     */
    public static PipeAdvertisement getSocketAdvertisement () {
        PipeID socketID = null;

        try {
            socketID = (PipeID) IDFactory.fromURI(new URI(
                    MulticastListener.SOCKET_ID));
        } catch (URISyntaxException use) {
            Logger logger = LoggerFactory.getLogger(Responder.class);
            logger.error("Error creating Socket ID", use);
        }
        PipeAdvertisement advertisement = (PipeAdvertisement) AdvertisementFactory
                .newAdvertisement(PipeAdvertisement.getAdvertisementType());

        advertisement.setPipeID(socketID);
        // set to type to propagate
        advertisement.setType(PipeService.PropagateType);
        advertisement.setName("Kalahari Multicast Pipe Advertisement");
        return advertisement;
    }

    /**
     * Run the thread.
     */
    @Override
    public void run () {
        this.listening = true;
        while (this.listening) {
            // FIXME This is obviously a magic number. Place somewhere better
            // and find a way to better guess the value.
            byte[] buffer = new byte[16384];
            DatagramPacket packet = new DatagramPacket(buffer,
                    buffer.length);
            try {
                // Block for a datagram. The following can be put into a loop
                this.mcastSocket.receive(packet);
                byte[] trimmed = Arrays.copyOfRange(packet.getData(),
                        0, packet.getLength());

                Message<?> messageObject;
                try {
                    messageObject = (Message)
                            Message.toObject(trimmed);
                } catch (ClassCastException cce) {
                    // If the object isn't a message, just ignore it
                    continue;
                }
                Configure conf = Configure.instance();
                String mode = conf.get("network.mode");

                if (mode.equals("loopback")) {
                  this.logger.debug("Loopback network mode enabled");
                } else if (mode.equals("normal")) {
                  this.logger.debug("Normal network mode enabled");
                  // Don't respond to own messages
                  String senderUnicastUrn = messageObject
                          .getUnicastUrn();
                  String myUnicastUrn = Status.getInstance()
                          .getNomadStatus().getUnicastUrn();
                  if (senderUnicastUrn.equals(myUnicastUrn)) {
                    this.logger.debug("Ignoring own multicast message");
                    continue;
                  }
                } else {
                  this.logger.warn("Unknown network mode: [" + mode
                          + "]");
                }

                this.logger.info("Received multicast packet: [" +
                        messageObject + "], From ["
                        + packet.getAddress() + "]");
                if (messageObject.toString().equals("GOOD_BYE")) {
                    GoodBye message = (GoodBye) messageObject;
                    Status.getInstance().getNomadStatus()
                            .removeNomad(message);
                } else if (messageObject.toString().equals("REQUEST"))
                        {
                    // Search internal database and send reply, do this
                    // in a separate thread.
                    // TODO This is just place holder code.

                    // Handle request asynchronously
                    Request request = (Request) messageObject;
                    this.requestHandler.addRequest(request);
                } else {
                    if (this.listening) {
                        this.logger.warn(
                                "Received an unknown multicast " +
                                "message: " + messageObject);
                    }
                }
            } catch (IOException ioe) {
                if (!this.listening) {
                    this.logger.error(
                            "An error occurred while trying to "
                            + "receive a multicast", ioe);
                }
            }
        }
    }

    /**
     * Stop multicast listener.
     */
    public void stopListener () {
        this.listening = false;
        this.mcastSocket.close();
        this.requestHandler.shutdown();
    }
}
