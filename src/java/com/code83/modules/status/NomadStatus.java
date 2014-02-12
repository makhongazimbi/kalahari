package com.code83.modules.status;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.code83.modules.communication.jxta.Setup;
import com.code83.utils.messages.HeartBeat;
import com.code83.utils.messages.Message;

import net.jxta.id.IDFactory;
import net.jxta.peergroup.PeerGroupID;


/**
 * Nomad status. Contains Nomad UUID and other nomads discovered on
 * network.
 *
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version 0.1 SVN: $Id: NomadStatus.java 896 2012-09-13 23:59:49Z mngazimb $
 * @since 0.1
 */
public class NomadStatus {

    /**
     * Map of nomads discovered on the network.
     */
    private Map<UUID, HeartBeat> nomads =
            new Hashtable<UUID, HeartBeat>();
    /**
     * This nomad's ID. TODO: create once and store in database. Retrieve
     * that value and use that every time.
     */
    private UUID nomadId = UUID.randomUUID();
    /**
     * The unicast urn for this nomad.
     */
    private String unicastUrn;
    /**
     * Logger.
     */
    private Logger logger = LoggerFactory.getLogger(NomadStatus.class);

    /**
     * Explicit default constructor.
     */
    protected NomadStatus () {
        PeerGroupID peerGroupId = Setup.getInstance().getPeerGroup()
                .getPeerGroupID();
        Calendar calendar = Calendar.getInstance();
        String seed = "UnicastPipeNomadId" + this.nomadId +
                calendar.getTimeInMillis();
        this.unicastUrn = IDFactory.newPipeID(peerGroupId,
                hash(seed.toLowerCase()))
                .toString();
    }

    /**
     * Returns a SHA1 hash of string.
     * @param expression to hash
     * @return a SHA1 hash of string or {@code null} if the expression
     * could not be hashed.
     */
    private static byte[] hash (final String expression) {
        byte[] result;
        MessageDigest digest;

        if (expression == null) {
            throw new IllegalArgumentException(
                    "Invalid null expression");
        }

        try {
            digest = MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException failed) {
            failed.printStackTrace(System.err);
            RuntimeException failure = new IllegalStateException(
                    "Could not get SHA-1 Message");
            failure.initCause(failed);
            throw failure;
        }

        try {
            byte[] expressionBytes = expression.getBytes("UTF-8");
            result = digest.digest(expressionBytes);
        } catch (UnsupportedEncodingException impossible) {
            RuntimeException failure = new IllegalStateException(
                    "Could not encode expression as UTF8");

            failure.initCause(impossible);
            throw failure;
        }
        return result;
    }

    /**
     * Add a nomad.
     * @param nomad
     */
    public void addNomad (HeartBeat nomad) {
        this.logger.debug("Adding nomad heart beat from sender: [" +
                nomad.getSenderId() + "]");
        this.nomads.put(nomad.getSenderId(), nomad);
    }

    /**
     * Remove a nomad.
     * @param nomad
     */
    public void removeNomad (Message<?> nomad) {
        this.logger.debug("Removing nomad heart beat from sender: [" +
                nomad.getSenderId() + "]");
        UUID sender = nomad.getSenderId();
        if (this.nomads.containsKey(sender)) {
            this.nomads.remove(sender);
        }
    }

    /**
     * Get the number of nomads currently connected to
     * @return int number of nomad connections
     */
    public int getNomadConnections () {
        return this.nomads.size();
    }

    /**
     * Get this nomad's ID.
     * @return
     */
    public UUID getNomadId () {
        return this.nomadId;
    }

    /**
     * Get the unicast urn for this nomad.
     * @return Urn
     */
    public String getUnicastUrn () {
        return this.unicastUrn;
    }

    protected void setUnicastUrn (String u) {
        this.unicastUrn = u;
    }

    /**
     * Get a map of the nomads that have been discovered.
     * @return Nomads
     */
    public synchronized Map<UUID, HeartBeat> getNomads () {
        return new Hashtable<UUID, HeartBeat>(this.nomads);
    }

}
