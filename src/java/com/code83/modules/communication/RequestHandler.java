package com.code83.modules.communication;

import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.code83.modules.auxilliary.Shutdown;
import com.code83.modules.filemods.FileDescriptor;
import com.code83.modules.status.Status;
import com.code83.utils.messages.MessageFactory;
import com.code83.utils.messages.Reply;
import com.code83.utils.messages.Request;


/**
 * A class that handles REQUEST messages from from other nomads.
 *
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version 0.1 SVN: $Id: RequestHandler.java 806 2010-08-02 21:20:35Z mngazimb
 *          $
 * @since 0.1
 */
public class RequestHandler extends Thread implements Shutdown {

    /**
     * Logger.
     */
    private final Logger logger = LoggerFactory.getLogger(
            RequestHandler.class);

    /**
     * Queue of requests.
     */
    private final Queue<Request> queue = new LinkedList<Request>();

    /**
     * Thread running state
     */
    private boolean running = true;

    /**
     * Explicit default constructor.
     */
    public RequestHandler () {
        super("Kalahari Request Handler");
        this.running = true;
    }

    /**
     * Add a search request.
     * @param r The search request message.
     */
    public void addRequest (Request r) {
        this.queue.add(r);
    }

    /**
     * Run this thread.
     */
    @Override
    public void run () {
        this.logger.info("Starting request handler");

        while (this.running) {
            if (this.queue.isEmpty()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    this.logger.error(
                            "An error occurred in thread sleep state",
                            e);
                }
                continue;
            }

            Request request = this.queue.remove();
            FileDescriptor descriptor = request.getPayload();
            this.logger.debug("Request payload received: [" +
                    descriptor + "], Requestor URN: [" +
                    request.getUnicastUrn() + "]");

            // Search the data base, if the file is found return a
            // response.
            // TODO implement
            String receiverUrn = request.getUnicastUrn();
            Reply reply = MessageFactory.createReply(receiverUrn);
            reply.setSearchId(request.getRequestId());
            // Descriptor must be modified to include the data for
            // the matching file found on this nomad.
            reply.setPayload(descriptor);

            Status status = Status.getInstance();
            UUID id = status.getNomadStatus().getNomadId();
            if (id.equals(request.getSenderId())) {
                this.logger.debug(
                        "Received own search request message");
            }

            // FIXME needs to be unicast message.
            Responder.instance().send(reply);
        }
        this.logger.info("Stopping request handler");
    }

    /**
     * Shutdown this thread.
     */
    public void shutdown () {
        this.logger.info("Shutting down Request Handler.");
        this.running = false;
    }

}
