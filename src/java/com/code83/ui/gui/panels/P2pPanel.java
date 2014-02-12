package com.code83.ui.gui.panels;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import com.code83.ui.gui.themes.Colors;
import com.code83.ui.gui.themes.Theme;
import com.code83.ui.gui.themes.ThemeFactory;


/**
 * 
 * 
 *   P2pPanel.java 779 2010-04-14 22:11:01Z mngazimb $
 * 
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version $Id: P2pPanel.java 865 2011-12-15 03:35:16Z mngazimb $
 * @since 0.1
 *
 */
public class P2pPanel extends JPanel {

	private static final long serialVersionUID = 2451346794853073858L;
	
	public P2pPanel () {
		JPanel header = new JPanel();
		setLayout(new BorderLayout());
		JLabel title = new JLabel("Peer to Peer Network");
		ThemeFactory theme = Theme.instance().getThemeFactory();
		Colors colors = theme.getColors();
		header.setBackground(colors.STATUS_PANEL_INVISIBLE());
		header.add(title);
		Border border = BorderFactory.createMatteBorder(
				0, 0, 1, 0, colors.FOOTER_PANEL_BORDER());
		header.setBorder(border);
		add(header, BorderLayout.PAGE_START);
	}

}
