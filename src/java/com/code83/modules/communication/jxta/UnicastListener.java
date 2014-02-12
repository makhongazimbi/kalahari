package com.code83.modules.communication.jxta;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;

import net.jxta.document.AdvertisementFactory;
import net.jxta.id.IDFactory;
import net.jxta.pipe.PipeID;
import net.jxta.pipe.PipeService;
import net.jxta.protocol.PipeAdvertisement;
import net.jxta.socket.JxtaServerSocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.code83.modules.communication.Responder;
import com.code83.modules.status.Status;
import com.code83.utils.messages.HeartBeat;
import com.code83.utils.messages.MessageFactory;
import com.code83.utils.messages.Ping;
import com.code83.utils.messages.Reply;


/**
 * A class that listens for Unicast messages to this nomad.
 *
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version 0.1 SVN: $Id: UnicastListener.java 816 2010-08-12 05:24:53Z mngazimb
 * @since 0.1
 */
public class UnicastListener extends Thread {

    /**
     * Whether this Nomad is listening for unicasts or not.
     */
    private boolean listening;

    /**
     * Logger.
     */
    private Logger logger = LoggerFactory
            .getLogger(UnicastListener.class);

    /**
     * Explicit default constructor.
     */
    public UnicastListener () {
        super("Kalahari Unicast Listener");
    }

    /**
     * Create pipe advertisement for this Nomad.
     * @return Pipe advertisement
     */
    public static PipeAdvertisement createSocketAdvertisement (String urn) {
        PipeID socketID = null;
        try {
            socketID = (PipeID) IDFactory.fromURI(new URI(urn));
        } catch (URISyntaxException use) {
            use.printStackTrace();
        }
        PipeAdvertisement advertisement = (PipeAdvertisement) AdvertisementFactory
                .newAdvertisement(PipeAdvertisement.getAdvertisementType());

        advertisement.setPipeID(socketID);
        advertisement.setType(PipeService.UnicastType);
        advertisement.setName("Kalahari Nomad Unicast Pipe");
        return advertisement;
    }

    /**
     * Run this thread.
     */
    @Override
    public void run () {
        this.listening = true;
        this.logger.info("Starting unicastListener server socket");
        JxtaServerSocket serverSocket = null;
        try {
            String urn = Status.getInstance().getNomadStatus()
                    .getUnicastUrn();
            serverSocket = new JxtaServerSocket(Setup.getInstance()
                    .getPeerGroup(), UnicastListener
                    .createSocketAdvertisement(urn), 50);
            serverSocket.setSoTimeout(0);
        } catch (IOException e) {
            this.logger.error("Failed to create a server socket", e);
            return;
        }

        while (this.listening) {
            try {
                Socket socket = serverSocket.accept();
                if (socket != null) {
                    UnicastListenerWorker worker = new UnicastListenerWorker(
                            socket);
                    worker.start();
                } else {
                    this.logger.error(
                            "Unicast listener server socket is null");
                }
            } catch (Exception e) {
                this.logger.error(
                        "Error occurred while accepting a connection", e);
            }
        }
    }

    /**
     * Stop the unicast listener.
     */
    public void stopListener () {
        this.listening = false;
    }

}

/**
 * The thread that handle a socket connection on behalf of the
 * UnicastListener.
 */
class UnicastListenerWorker extends Thread {

    /**
     * Socket to handle
     */
    private Socket socket;
    /**
     * Logger.
     */
    private Logger logger = LoggerFactory
            .getLogger(UnicastListenerWorker.class);

    /**
     * Constructor.
     * @param skt The socket to handle
     */
    public UnicastListenerWorker (Socket skt) {
        super();
        this.setName("Unicast Listener Worker ID [" + this.getId() + "]");
        this.socket = skt;
    }

    /**
     * Run the thread.
     */
    @Override
    public void run () {
        try {
            InputStream in = this.socket.getInputStream();
            ObjectInputStream input = new ObjectInputStream(in);
            Object object = input.readObject();

            this.logger.debug("Received unicast message: ["
                    + object + "]");

            if (object.toString().equals("SEND")) {
                // Send sendMsg = (Send) object;
            } else if (object.toString().equals("PING")) {
                Ping pingMsg = (Ping) object;
                HeartBeat heartBeat = MessageFactory.createHeartBeat();
                heartBeat.setUnicastUrn(pingMsg.getUnicastUrn());
                Responder responder = Responder.instance();
                responder.send(heartBeat);
            } else if (object.toString().equals("REPLY")) {
                Reply reply = (Reply) object;
                Status.getInstance().getReplyStatus().addReply(reply);
                this.logger.debug("Reply message unicast URN: [" +
                        reply.getUnicastUrn() + "]");
            } else {
                this.logger.warn("Received unknown unicast message: [" +
                        object + "]");
            }
        } catch (IOException ioe) {
            this.logger.error("Error reading input stream", ioe);
        } catch (ClassNotFoundException cnfe) {
            this.logger.error("Error reading object", cnfe);
        }
    }

}
