package com.code83.ui.gui.commands;

import com.code83.modules.communication.Responder;
import com.code83.utils.messages.MessageFactory;


/**
 * This class encapsulates the behaviors needed to set invisible mode
 * 
 *   Invisible.java 731 2009-12-16 00:54:44Z mngazimb $
 * 
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version $Id: Invisible.java 865 2011-12-15 03:35:16Z mngazimb $
 * @since 0.1
 */
public class Invisible implements Command {
	
	public void execute () {
		/* stop heart beats */
		Responder responder = Responder.instance();
		responder.setInvisibleMode();
		
		/* send Network goodbye */
		responder.sendToAll(MessageFactory.createGoodBye());
	}
	
}