package com.code83.modules.communication.jxta;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.code83.utils.messages.HeartBeat;
import com.code83.utils.messages.Message;
import com.code83.utils.messages.MessageFactory;

import net.jxta.document.Advertisement;
import net.jxta.document.AdvertisementFactory;
import net.jxta.document.Attributable;
import net.jxta.document.Document;
import net.jxta.document.Element;
import net.jxta.document.MimeMediaType;
import net.jxta.document.StructuredDocument;
import net.jxta.document.StructuredDocumentFactory;
import net.jxta.document.StructuredTextDocument;
import net.jxta.document.TextElement;
import net.jxta.id.ID;
import net.jxta.id.IDFactory;

/**
 * Simple Advertisement Tutorial creates an advertisment describing a system
 * <p/>
 * <pre>
 * &lt;?xml version="1.0"?>
 * &lt;!DOCTYPE jxta:System>
 * &lt;jxta:System xmlns:jxta="http://jxta.org">
 *   &lt;id>id&lt;/id>
 *   &lt;name>Device Name&lt;/name>
 *   &lt;ip>ip address&lt;/ip>
 *   &lt;hwarch>x86&lt;/hwarch>
 *   &lt;hwvendor>Sun MicroSystems&lt;/hwvendor>
 *   &lt;OSName>&lt;/OSName>
 *   &lt;OSVer>&lt;/OSVer>
 *   &lt;osarch>&lt;/osarch>
 *   &lt;sw>&lt;/sw>
 * &lt;/jxta:System>
 * </pre>
 *
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version $Id: JxtaHeartbeat.java 903 2012-09-14 22:10:04Z mngazimb $
 * @since 0.1
 */
public class JxtaHeartbeat extends Advertisement implements
        Comparable<String>, Cloneable, Serializable {
    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -7064410899106238917L;
    /**
     * Logger.
     */
    private Logger logger = LoggerFactory.getLogger(JxtaHeartbeat.class);
    private String hwarch;
    private String hwvendor;
    private ID id = ID.nullID;
    private String ip;
    private String name;
    private String osname;
    private String osversion;
    private String osarch;
    private String inventory;
    private String uuid;
    private String urn;
    private String senderId;

    private final static String OSNameTag = "OSName";
    private final static String OSVersionTag = "OSVer";
    private final static String OSarchTag = "osarch";
    private final static String hwarchTag = "hwarch";
    private final static String hwvendorTag = "hwvendor";
    private final static String idTag = "ID";
    private final static String ipTag = "ip";
    private final static String nameTag = "name";
    private final static String swTag = "sw";
    private final static String uuidTag = "kuuid";
    private final static String urnTag = "urn";
    private final static String SENDER_ID = "sender-id";

    /**
     * Nomad message type.
     */
    private HeartBeat heartbeat;

    /**
     * Indexable fields.  Advertisements must define the indexables, in order
     * to properly index and retrieve these advertisements locally and on the
     * network
     */
    private final static String[] fields = { idTag, nameTag, hwarchTag };

    /**
     * Default Constructor
     */
    public JxtaHeartbeat () {
        this.setID(ID.nullID);
        this.setName("Heartbeat");
        // TODO move this out of this class into heartbeat sender.
        this.heartbeat = MessageFactory.createHeartBeat();
        this.setOSName(this.heartbeat.getOsName());
        this.setOSArch(this.heartbeat.getOsArch());
        this.setHWArch(this.heartbeat.getHwArch());
        this.setHWVendor(this.heartbeat.getHwVendor());
        this.setIP(this.heartbeat.getSenderIp().getHostAddress());
        this.setOSVersion(this.heartbeat.getOsVersion());
        this.setUuid(this.heartbeat.getSenderId().toString());
        this.setUnicastUrn(this.heartbeat.getUnicastUrn().toString());
        this.setSenderId(this.heartbeat.getSenderId().toString());
    }

    /**
     * Construct from a StructuredDocument
     * @param root Root element
     */
    public JxtaHeartbeat (Element<?> root) {
        TextElement<?> doc = (TextElement<?>) root;

        if (!getAdvertisementType().equals(doc.getName())) {
            throw new IllegalArgumentException("Could not construct : "
                    + getClass().getName() + "from doc containing a "
                    + doc.getName());
        }
        initialize(doc);
        this.heartbeat = MessageFactory.createHeartBeat();
    }

    /**
     * Construct a doc from InputStream
     *
     * @param stream the underlying input stream.
     * @throws IOException if an I/O error occurs.
     */

    public JxtaHeartbeat (InputStream stream) throws IOException {
        StructuredTextDocument<?> doc = (StructuredTextDocument<?>)
                StructuredDocumentFactory
                .newStructuredDocument(MimeMediaType.XMLUTF8, stream);
        initialize(doc);
        this.heartbeat = MessageFactory.createHeartBeat();
    }

    /**
     * Set a message.
     */
    public void setHeartBeat (HeartBeat message) {
        this.heartbeat = message;
    }

    /**
     * Get the translation of this JxtaHeartbeat as a Kalahari
     * utils.messages.Message.
     * @return
     */
    public HeartBeat getHeartBeat () {
        return this.heartbeat;
    }

    /**
     * Set the uuid of the nomad.
     * @param id The Id
     */
    public void setUuid (String id) {
        this.uuid = id;
    }

    /**
     * Set the unicast urn.
     * @param u Urn
     */
    public void setUnicastUrn (String u) {
        this.urn = u;
    }

    /**
     * Get the unicast urn.
     * @return Urn
     */
    public String getUnicastUrn () {
        return this.urn;
    }

    /**
     * Sets the hWArch attribute of the AdvertisementTutorial object
     *
     * @param hwarch The new hWArch value
     */
    public void setHWArch (String hwarch) {
        this.hwarch = hwarch;
    }

    /**
     * Sets the OSArch attribute of the AdvertisementTutorial object
     *
     * @param osarch The new hWArch value
     */
    public void setOSArch (String osarch) {
        this.osarch = osarch;
    }

    /**
     * Sets the hWVendor attribute of the AdvertisementTutorial object
     *
     * @param hwvendor The new hWVendor value
     */
    public void setHWVendor (String hwvendor) {
        this.hwvendor = hwvendor;
    }

    /**
     * sets the unique id
     *
     * @param id The id
     */
    public void setID (ID id) {
        this.id = (id == null ? null : id);
    }

    /**
     * Sets the iP attribute of the AdvertisementTutorial object
     *
     * @param ip The new iP value
     */
    public void setIP (String ip) {
        this.ip = ip;
    }

    /**
     * Sets the name attribute of the AdvertisementTutorial object
     *
     * @param name The new name value
     */
    public void setName (String name) {
        this.name = name;
    }

    /**
     * Sets the oSName attribute of the AdvertisementTutorial object
     *
     * @param osname The new oSName value
     */
    public void setOSName (String osname) {
        this.osname = osname;
    }

    /**
     * Sets the oSVersion attribute of the AdvertisementTutorial object
     *
     * @param osversion The new oSVersion value
     */
    public void setOSVersion (String osversion) {
        this.osversion = osversion;
    }

    /**
     * Sets the SWInventory attribute of the AdvertisementTutorial object
     *
     * @param inventory the software inventory of the system
     */
    public void setSWInventory (String inventory) {
        this.inventory = inventory;
    }

    /**
     * {@inheritDoc}
     *
     * @param asMimeType Document encoding
     * @return The document value
     */
    @SuppressWarnings("unchecked")
    @Override
    public Document getDocument (MimeMediaType asMimeType) {
        StructuredDocument adv = StructuredDocumentFactory
                .newStructuredDocument(asMimeType, getAdvertisementType());

        if (adv instanceof Attributable) {
            ((Attributable) adv).addAttribute("xmlns:jxta", "http://jxta.org");
        }
        Element<?> e;

        e = adv.createElement(idTag, getID().toString());
        adv.appendChild(e);
        e = adv.createElement(nameTag, getName().trim());
        adv.appendChild(e);
        e = adv.createElement(OSNameTag, getOSName().trim());
        adv.appendChild(e);
        e = adv.createElement(OSVersionTag, getOSVersion().trim());
        adv.appendChild(e);
        e = adv.createElement(OSarchTag, getOSArch().trim());
        adv.appendChild(e);
        e = adv.createElement(ipTag, getIP().trim());
        adv.appendChild(e);
        e = adv.createElement(hwarchTag, getHWArch().trim());
        adv.appendChild(e);
        e = adv.createElement(hwvendorTag, getHWVendor().trim());
        adv.appendChild(e);
        e = adv.createElement(swTag, getSWInventory().trim());
        adv.appendChild(e);
        e = adv.createElement(uuidTag, this.getUuid().trim());
        adv.appendChild(e);
        e = adv.createElement(urnTag, this.getUnicastUrn().trim());
        adv.appendChild(e);
        e = adv.createElement(JxtaHeartbeat.SENDER_ID,
                this.getSenderId().trim());
        adv.appendChild(e);

        return adv;
    }

    /**
     * Get the uuid attribute.
     * @return The uuid value
     */
    public String getUuid () {
        return this.uuid;
    }

    /**
     * Gets the hWArch attribute of the AdvertisementTutorial object
     *
     * @return The hWArch value
     */
    public String getHWArch () {
        return this.hwarch;
    }

    /**
     * Gets the OSArch attribute of the AdvertisementTutorial object
     *
     * @return The OSArch value
     */
    public String getOSArch () {
        return this.osarch;
    }

    /**
     * Gets the hWVendor attribute of the AdvertisementTutorial object
     *
     * @return The hWVendor value
     */
    public String getHWVendor () {
        return this.hwvendor;
    }

    /**
     * returns the id of the device
     *
     * @return ID the device id
     */
    @Override
    public ID getID () {
        return (this.id == null ? null : this.id);
    }

    /**
     * Gets the IP attribute of the AdvertisementTutorial object
     *
     * @return The IP value
     */
    public String getIP () {
        return this.ip;
    }

    /**
     * Gets the name attribute of the AdvertisementTutorial object
     *
     * @return The name value
     */
    public String getName () {
        return this.name;
    }

    /**
     * Gets the OSName attribute of the AdvertisementTutorial object
     *
     * @return The OSName value
     */
    public String getOSName () {
        return this.osname;
    }

    /**
     * Gets the Software Inventory text element
     *
     * @return The Inventory value
     */
    public String getSWInventory () {
        if (this.inventory == null) {
            this.inventory = "";
        }
        return this.inventory;
    }

    /**
     * Gets the OSVersion attribute of the AdvertisementTutorial object
     *
     * @return The OSVersion value
     */
    public String getOSVersion () {
        return this.osversion;
    }

    public void setSenderId (String id) {
        this.senderId = id;
    }

    public String getSenderId () {
        return this.senderId;
    }

    /**
     * Process an individual element from the document.
     *
     * @param elem the element to be processed.
     * @return true if the element was recognized, otherwise false.
     */
    protected boolean handleElement (TextElement<?> elem) {
        if (elem.getName().equals(idTag)) {
            try {
                URI id = new URI(elem.getTextValue());
                setID(IDFactory.fromURI(id));
            } catch (URISyntaxException badID) {
                throw new IllegalArgumentException(
                        "unknown ID format in advertisement: "
                        + elem.getTextValue());
            } catch (ClassCastException badID) {
                throw new IllegalArgumentException(
                        "Id is not a known id type: " + elem.getTextValue());
            }
            return true;
        }
        if (elem.getName().equals(nameTag)) {
            setName(elem.getTextValue());
            return true;
        }
        if (elem.getName().equals(OSNameTag)) {
            setOSName(elem.getTextValue());
            return true;
        }
        if (elem.getName().equals(OSVersionTag)) {
            setOSVersion(elem.getTextValue());
            return true;
        }
        if (elem.getName().equals(OSarchTag)) {
            setOSArch(elem.getTextValue());
            return true;
        }
        if (elem.getName().equals(ipTag)) {
            setIP(elem.getTextValue());
            return true;
        }
        if (elem.getName().equals(hwarchTag)) {
            setHWArch(elem.getTextValue());
            return true;
        }
        if (elem.getName().equals(hwvendorTag)) {
            setHWVendor(elem.getTextValue());
            return true;
        }
        if (elem.getName().equals(swTag)) {
            setSWInventory(elem.getTextValue());
            return true;
        }
        if (elem.getName().equals(uuidTag)) {
            setUuid(elem.getTextValue());
            return true;
        }
        if (elem.getName().equals(urnTag)) {
            setUnicastUrn(elem.getTextValue());
            return true;
        }
        if (elem.getName().equals(SENDER_ID)) {
            setSenderId(elem.getTextValue());
            return true;
        }

        // element was not handled
        return false;
    }

    /**
     * Intialize a System advertisement from a portion of a structured
     * document.
     * @param root document root
     */
    protected void initialize (Element<?> root) {
        if (!TextElement.class.isInstance(root)) {
            throw new IllegalArgumentException(getClass().getName()
                    + " only supports TextElement");
        }
        TextElement<?> doc = (TextElement<?>) root;

        if (!doc.getName().equals(getAdvertisementType())) {
            throw new IllegalArgumentException("Could not construct : "
                    + getClass().getName() + "from doc containing a "
                    + doc.getName());
        }
        Enumeration<?> elements = doc.getChildren();

        while (elements.hasMoreElements()) {
            TextElement<?> elem = (TextElement<?>) elements.nextElement();

            if (!handleElement(elem)) {
                this.logger.warn("Unhandleded element \'" + elem.getName()
                        + "\' in " + doc.getName());
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String[] getIndexFields () {
        return fields;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals (Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof JxtaHeartbeat) {
            JxtaHeartbeat adv = (JxtaHeartbeat) obj;

            return getID().equals(adv.getID());
        }

        return false;
    }

    /**
     * {@inheritDoc}
     */
    public int compareTo (String other) {
        return getID().toString().compareTo(other);
    }

    /**
     * All messages have a type (in xml this is &#0033;doctype) which
     * identifies the message
     *
     * @return String "jxta:AdvertisementTutorial"
     */
    public static String getAdvertisementType () {
        return "jxta:KalahariHeartbeat";
    }

    /**
     * Instantiator
     */
    public static class Instantiator implements
            AdvertisementFactory.Instantiator {

        /**
         * Returns the identifying type of this Advertisement.
         *
         * @return String the type of advertisement
         */
        public String getAdvertisementType () {
            return JxtaHeartbeat.getAdvertisementType();
        }

        /**
         * Constructs an instance of <CODE>Advertisement</CODE> matching the
         * type specified by the <CODE>advertisementType</CODE> parameter.
         *
         * @return The instance of <CODE>Advertisement</CODE> or null if it
         *         could not be created.
         */
        public Advertisement newInstance () {
            return new JxtaHeartbeat();
        }

        /**
         * Constructs an instance of <CODE>Advertisement</CODE> matching the
         * type specified by the <CODE>advertisementType</CODE> parameter.
         *
         * @param root Specifies a portion of a StructuredDocument which will
         *             be converted into an Advertisement.
         * @return The instance of <CODE>Advertisement</CODE> or null if it
         *         could not be created.
         */
        @SuppressWarnings("unchecked")
        public Advertisement newInstance (Element root) {
            return new JxtaHeartbeat(root);
        }
    }

}
