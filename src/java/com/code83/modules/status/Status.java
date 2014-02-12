package com.code83.modules.status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class stores status information about Kalahari while it
 * is running. This class implements the Singleton design pattern.
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version $Id: Status.java 903 2012-09-14 22:10:04Z mngazimb $
 * @since 0.1
 */
public class Status {
    private NetworkStatus networkStatus = new NetworkStatus();
    private NomadStatus nomadStatus = new NomadStatus();
    private ReplyStatus replyStatus = new ReplyStatus();
    private RequestStatus requestStatus = new RequestStatus();
    /**
     * Logger.
     */
    private Logger logger = LoggerFactory.getLogger(Status.class);

    /**
     * Initialize status objects.
     */
    private Status () {
        this.networkStatus.resetIpAddress();
    }

    /**
     *  Get an instance of this class.
     * @return The single instance of the Status class.
     */
    public static Status getInstance () {
        return StatusHolder.INSTANCE;
    }

    /**
     * Status holder.
     */
    private static class StatusHolder {
        private static final Status INSTANCE = new Status();
    }

    /**
     * Get nomad status.
     * @return nomad status
     */
    public NomadStatus getNomadStatus () {
        return this.nomadStatus;
    }

    /**
     * Get the reply status.
     * @return Reply status
     */
    public ReplyStatus getReplyStatus () {
        return this.replyStatus;
    }

    /**
     * Get the request status.
     * @return request status
     */
    public RequestStatus getRequestStatus () {
        return this.requestStatus;
    }

    /**
     * Get network status.
     * @return Network status
     */
    public NetworkStatus getNetworkStatus () {
        return this.networkStatus;
    }
}
