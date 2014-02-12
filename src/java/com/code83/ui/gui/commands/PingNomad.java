package com.code83.ui.gui.commands;

import com.code83.modules.communication.Responder;
import com.code83.utils.messages.MessageFactory;
import com.code83.utils.messages.Ping;


/**
 * Ping a nomad.
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version 0.1 SVN: $Id: PingNomad.java 865 2011-12-15 03:35:16Z mngazimb $
 * @since 0.1
 */
public class PingNomad implements Command {

    private Ping ping;

    /**
     * Explicit default constrcutor. 
     * TODO add the ID of the nomad to ping as a constructor.
     */
    public PingNomad () {
        System.out.println("Pinging myself!");
        this.ping = MessageFactory.createPing();
    }

    /**
     * Constructor.
     * @param urn The urn identifying the nomad to ping.
     */
    public PingNomad (String urn) {
        System.out.println("Nomad about to be pinged: [" + urn + "]");
        this.ping = MessageFactory.createPing();
        this.ping.setUnicastUrn(urn);
    }

    /**
     * Execute this command.
     */
    public void execute () {
        //Ping ping = MessageFactory.createPing();

        Responder responder = Responder.instance();
        // TODO set UUID correctly. Just myself for now...
        //UUID id = Status.getInstance().getNomadStatus().getNomadId();
        //ping.setReceivedId(id);
        responder.send(this.ping);
    }
}
