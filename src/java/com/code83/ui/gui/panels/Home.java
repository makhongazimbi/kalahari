package com.code83.ui.gui.panels;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.code83.ui.gui.themes.Colors;
import com.code83.ui.gui.themes.Theme;
import com.code83.ui.gui.themes.ThemeFactory;
import com.code83.utils.Images;


/**
 * 
 *   Home.java 694 2009-09-18 22:18:58Z mngazimb $
 * 
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version $Id: Home.java 865 2011-12-15 03:35:16Z mngazimb $
 * @since 0.1
 */
public class Home extends JPanel {

	private static final long serialVersionUID = -7714201438157414529L;

	public Home () {
		ThemeFactory theme = Theme.instance().getThemeFactory();
		Colors colors = theme.getColors();
		
		setLayout(new BorderLayout());
		setBackground( colors.HOME_PANEL_BACKGROUND() );
		ImageIcon icon = new ImageIcon( Images.KALAHARI_ICON_128x128 );
		JLabel label = new JLabel(icon);
		
		add(label, BorderLayout.CENTER);
	}
}
