package com.code83.ui.gui.panels;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import com.code83.ui.gui.commands.Command;
import com.code83.ui.gui.commands.CommandRegister;
import com.code83.ui.gui.commands.HideDownloadTray;
import com.code83.ui.gui.commands.ShowDownloadTray;
import com.code83.ui.gui.themes.Colors;
import com.code83.ui.gui.themes.Theme;
import com.code83.ui.gui.themes.ThemeFactory;


/**
 * The panel at the SOUTH end of the GUI which contains the status panel
 * and the download tray.
 * 
 *   FooterPanel.java 737 2010-03-18 17:16:30Z mngazimb $
 * 
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version $Id: FooterPanel.java 865 2011-12-15 03:35:16Z mngazimb $
 * @since 0.1
 *
 */
public class FooterPanel extends JPanel {

	private static final long serialVersionUID = 1064156532408030965L;
	private DownloadsPanel downloads;
	private StatusPanel status;
	
	public FooterPanel () {
		ThemeFactory theme = Theme.instance().getThemeFactory();
		Colors colors = theme.getColors();
		
		Color color = colors.FOOTER_PANEL_BORDER();
		Border border = BorderFactory.createMatteBorder(1, 0, 0, 0, color);
		setBorder(border);
		
		setLayout(new BorderLayout());
		
		status = new StatusPanel(getWidth());
		downloads = new DownloadsPanel(getWidth());
		StatusUpdateThread statusUpdateThread = new StatusUpdateThread(status);
		statusUpdateThread.start();
		
		add(downloads, BorderLayout.NORTH);
		add(status, BorderLayout.SOUTH);
		
		Command show = new ShowDownloadTray(this);
		Command hide = new HideDownloadTray(this);
		CommandRegister register = CommandRegister.instance();
		register.addCommand("hide_download_tray", hide);
		register.addCommand("show_download_tray", show);
	}
	
	public void hideDowloadPanel () {
		remove(downloads);
	}
	
	public void showDownloadPanel () {
		add(downloads, 0);
	}
}
