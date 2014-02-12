package com.code83.ui.gui.panels.forms;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import com.code83.ui.gui.themes.Colors;
import com.code83.ui.gui.themes.Theme;
import com.code83.ui.gui.themes.ThemeFactory;


/**
 * Document file search form
 * 
 *   DocumentSearchForm.java 773 2010-04-12 22:54:00Z mngazimb $
 * 
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version $Id: DocumentSearchForm.java 865 2011-12-15 03:35:16Z mngazimb $
 * @since 0.1
 *
 */
public class DocumentSearchForm extends SearchForm {

	private static final long serialVersionUID = 6633703962243435121L;
	
	public DocumentSearchForm () {
		setLayout(new BorderLayout());
		ThemeFactory theme = Theme.instance().getThemeFactory();
		Colors colors = theme.getColors();
		setBackground(colors.HOME_PANEL_BACKGROUND());
		addHeader();
		addBody();
	}
	
	private void addHeader () {
		JPanel header = getHeader("Document Search");
		add(header, BorderLayout.PAGE_START);
	}
	
	private void addBody () {
		addLabelAndTextfield("Name");
		addLabelAndTextfield("File Extension");
		addSearchButton();
		
		JPanel bodyPanel = getBody();
		add(bodyPanel, BorderLayout.LINE_START);
	}
	
} 
