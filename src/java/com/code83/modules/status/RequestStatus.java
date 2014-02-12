package com.code83.modules.status;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.code83.utils.messages.Request;


/**
 * Nomad Request status.
 * 
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @since 0.1
 * @version $Id: RequestStatus.java 865 2011-12-15 03:35:16Z mngazimb $
 */
public class RequestStatus {

    /**
     * Request ID counter.
     */
    private int requestId = 0;

    /**
     * Map of requests made by this nomad. Key: Request ID Value: Request
     */
    private final Map<Integer, Request> requests = new HashMap<Integer, Request>();

    /**
     * Default constructor.
     */
    protected RequestStatus () {
    }

    /**
     * Get the next available request ID.
     * 
     * @return int Request ID
     */
    public int getRequestId () {
        this.requestId += 1;
        return this.requestId;
    }

    /**
     * Add a request object to the list of request made by this nomad.
     * 
     * @param r
     *            Request by this nomad
     * @return void
     */
    public void addRequest (Request r) {
        this.requests.put(r.getRequestId(), r);
    }

    /**
     * Get a specified request ID
     * 
     * @param id
     * @return
     */
    public Request getRequest (int id) {
        return this.requests.get(id);
    }

    /**
     * Get the set of requests already made by this nomad.
     * 
     * @return Set of request IDs
     */
    public Set<Integer> getRequestIds () {
        return this.requests.keySet();
    }

}
