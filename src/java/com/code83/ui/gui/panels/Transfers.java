package com.code83.ui.gui.panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.Border;

import com.code83.ui.gui.themes.Colors;
import com.code83.ui.gui.themes.Icons;
import com.code83.ui.gui.themes.Theme;
import com.code83.ui.gui.themes.ThemeFactory;


/**
 * 
 *   Transfers.java 754 2010-04-06 18:30:52Z mngazimb $
 * 
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version $Id: Transfers.java 865 2011-12-15 03:35:16Z mngazimb $
 * @since 0.1
 *
 */
public class Transfers extends JPanel {

	private static final long serialVersionUID = 1887286388385875407L;
	private JTabbedPane transfersPanel = new JTabbedPane();
	
	public Transfers () {
		setLayout(new GridLayout(1,1));
		transfersPanel.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		addDownLoadsTab();
		addUpLoadsTab();
		add(transfersPanel);
	}
	
	private void addUpLoadsTab () {
		ThemeFactory theme = Theme.instance().getThemeFactory();
		Icons icons = theme.getIcons();
		ImageIcon icon = new ImageIcon(icons.UPLOADS_ICON_16x16());

		JPanel uploads = new JPanel();
		uploads.setLayout(new BorderLayout());
		
		JPanel titleBar = new JPanel();
		titleBar.setLayout(new FlowLayout());
		JLabel title = new JLabel("No uploads in progress");
		titleBar.add(title);
		
		Colors colors = theme.getColors();
		titleBar.setBackground(colors.STATUS_PANEL_INVISIBLE());
		uploads.add(titleBar, BorderLayout.PAGE_START);
		Border border = BorderFactory.createMatteBorder(
				0, 0, 1, 0, colors.FOOTER_PANEL_BORDER());
		titleBar.setBorder(border);
		add(uploads);
		
		transfersPanel.addTab("Uploads", icon, uploads, "Uploads in Progress");
	}
	
	private void addDownLoadsTab () {
		ThemeFactory theme = Theme.instance().getThemeFactory();
		Icons icons = theme.getIcons();
		ImageIcon icon = new ImageIcon(icons.DOWNLOADS_ICON_16x16());

		JPanel downloads = new JPanel();
		downloads.setLayout(new BorderLayout());
		
		JPanel titleBar = new JPanel();
		titleBar.setLayout(new FlowLayout());
		JLabel title = new JLabel("No downloads in progress");
		titleBar.add(title);
		
		Colors colors = theme.getColors();
		titleBar.setBackground(colors.STATUS_PANEL_INVISIBLE());
		downloads.add(titleBar, BorderLayout.PAGE_START);
		Border border = BorderFactory.createMatteBorder(
				0, 0, 1, 0, colors.FOOTER_PANEL_BORDER());
		titleBar.setBorder(border);
		add(downloads);
		
		transfersPanel.addTab("Downloads", icon, downloads, 
				"Downloads in Progress");
		transfersPanel.setSelectedComponent(downloads);
	}
}
