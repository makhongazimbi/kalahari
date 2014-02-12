package com.code83.modules.status;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * Nomad network status. 
 *
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version 0.1 SVN: $Id: NetworkStatus.java 865 2011-12-15 03:35:16Z mngazimb $
 * @since 0.1
 */
public class NetworkStatus {
    /**
     * Nomad IP address. 
     */
    private static InetAddress nomad_ip;
    /**
     * Network available.
     */
    private boolean networkAvailable = true;
    /**
     * Network visibility.
     */
    private NetworkVisibilityStatus nomadStatus =
            NetworkVisibilityStatus.VISIBLE;

    /**
     * Default constructor.
     */
    protected NetworkStatus () {
        resetIpAddress();
    }

    /**
     * Set class variables.
     */
    static {
        try {
            nomad_ip = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Reset IP address.
     */
    protected void resetIpAddress () {
        try {
            Enumeration<?> e = NetworkInterface.getNetworkInterfaces();

            while (e.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) e.nextElement();

                Enumeration<?> e2 = ni.getInetAddresses();

                while (e2.hasMoreElements()) {
                    InetAddress ip = (InetAddress) e2.nextElement();
                    if (!ip.isLoopbackAddress() && ip instanceof Inet4Address) {
                        nomad_ip = ip;
                    }
                }
            }
        } catch (Exception e) {
            //TODO log error
            e.printStackTrace();
        }
    }

    public InetAddress getNomadIp () {
        return nomad_ip;
    }

    public boolean isNetworkAvailable () {
        return this.networkAvailable;
    }

    public void networkAvailable (boolean avail) {
        this.networkAvailable = avail;
    }

    public NetworkVisibilityStatus getNomadStatus () {
        return this.nomadStatus;
    }

    public void setNomadStatus (NetworkVisibilityStatus status) {
        this.nomadStatus = status;
    }

    /*
     * VISIBLE: regular listening mode
     * INVISIBLE: when user selects invisible mode
     * OFFLINE: Unable to connect to network for some reason
     */
    public enum NetworkVisibilityStatus {
        VISIBLE, INVISIBLE, OFFLINE
    }
}
