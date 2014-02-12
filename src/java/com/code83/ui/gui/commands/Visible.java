package com.code83.ui.gui.commands;

import com.code83.modules.communication.Responder;

public class Visible implements Command {
	
	public void execute() {
		/* start sending heart beats again */
		Responder responder = Responder.instance();
		responder.setVisibleMode();
	}

}
