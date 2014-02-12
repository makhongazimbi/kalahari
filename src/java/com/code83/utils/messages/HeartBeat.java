package com.code83.utils.messages;

import java.util.UUID;

/**
 * A heart beat message that nomads on the network send to signal
 * they are still active.
 *
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version $Id: HeartBeat.java 904 2012-09-14 22:31:20Z mngazimb $
 * @since 0.1
 * @see Message, {@link DefaultMessage}
 */
public class HeartBeat extends Message<String> {

    private static final long serialVersionUID = -4326744903569404370L;
    private static final String payload = "HEART_BEAT";
    private String osName = System.getProperty("os.name");
    private String osVersion = System.getProperty("os.version");
    private String osArch = System.getProperty("os.arch");
    private String hwArch = System.getProperty("HOSTTYPE", System
            .getProperty("os.arch"));
    private String hwVendor = System
            .getProperty("java.vm.vendor");
    private int filesShared;

    public HeartBeat () {
        super();
        // TODO calculate number of files shared.
        this.filesShared = -1;
    }

    @Override
    public String getPayload () {
        return "HEART_BEAT";
    }

    @Override
    public void setPayload (String load) {
    }

    public String toString () {
        return payload;
    }

    public String getOsName () {
        return this.osName;
    }

    public void setOsName (String name) {
        this.osName = name;
    }

    public String getOsVersion () {
        return this.osVersion;
    }

    public void setOsVersion (String version) {
        this.osVersion = version;
    }

    public String getOsArch () {
        return this.osArch;
    }

    public void setOsArch (String arch) {
        this.osArch = arch;
    }

    public String getHwArch () {
        return this.hwArch;
    }

    public void setHwArch (String arch) {
        this.hwArch = arch;
    }

    public String getHwVendor () {
        return this.hwVendor;
    }

    public void setHwVendor (String vendor) {
        this.hwVendor = vendor;
    }

    public int getFilesSharedCount () {
        return this.filesShared;
    }

    public void setSenderId (UUID id) {
        this.senderId = id;
    }

}
