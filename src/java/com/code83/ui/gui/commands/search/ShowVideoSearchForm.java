package com.code83.ui.gui.commands.search;

import javax.swing.JPanel;

import com.code83.ui.gui.GuiFrame;
import com.code83.ui.gui.commands.Command;
import com.code83.ui.gui.panels.forms.VideoSearchForm;
import com.code83.utils.RealTimeSwing;


/**
 * Show the video search form
 * 
 *   ShowVideoSearchForm.java 772 2010-04-12 16:36:16Z mngazimb $
 * 
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @since 0.1
 * @version $Id: ShowVideoSearchForm.java 865 2011-12-15 03:35:16Z mngazimb $
 *
 */
public class ShowVideoSearchForm implements Command {

	private JPanel container;
	
	public ShowVideoSearchForm (JPanel c) {
		container = c;
	}
	
	public void execute () {
		final Runnable command = new Runnable () {
			public void run () {
				container.removeAll();
				container.add(new VideoSearchForm());
				GuiFrame.instance().validate();
			}
		};
		RealTimeSwing.invokeNow(command);
	}

}
