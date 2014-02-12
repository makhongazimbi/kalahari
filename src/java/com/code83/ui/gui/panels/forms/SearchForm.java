package com.code83.ui.gui.panels.forms;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.code83.ui.gui.themes.Colors;
import com.code83.ui.gui.themes.Theme;
import com.code83.ui.gui.themes.ThemeFactory;


/**
 * An abstract class for a search form
 * 
 *   SearchForm.java 773 2010-04-12 22:54:00Z mngazimb $
 * 
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version $Id: SearchForm.java 865 2011-12-15 03:35:16Z mngazimb $
 * @since 0.1
 *
 */
public abstract class SearchForm extends JPanel {

	private static final long serialVersionUID = 50917229728670025L;
	private static final int TEXT_FIELD_WIDTH = 15;
	private int gridy = 0;
	private GridBagConstraints constraints = new GridBagConstraints();
	private JPanel body = new JPanel(new GridBagLayout());
	
	protected SearchForm () {
		ThemeFactory theme = Theme.instance().getThemeFactory();
		Colors colors = theme.getColors();
		body.setBackground(colors.HOME_PANEL_BACKGROUND());
	}
	
	protected static JPanel getHeader (String searchName) {
		JPanel header = new JPanel();
		header.setLayout(new FlowLayout(FlowLayout.LEFT));
		ThemeFactory theme = Theme.instance().getThemeFactory();
		Colors colors = theme.getColors();
		header.setBackground(colors.HOME_PANEL_BACKGROUND());
		JLabel title = new JLabel(searchName);
		Font f = title.getFont();
		title.setFont(f.deriveFont(f.getStyle() ^ Font.BOLD));
		header.add(title);
		return header;
	}
	
	protected void addLabelAndTextfield (String name) {
		int gridx = 0;
		JLabel label = new JLabel(name);
		JTextField field = new JTextField(TEXT_FIELD_WIDTH);
		constraints.gridx = gridx; constraints.gridy = gridy;
		constraints.anchor = GridBagConstraints.LINE_START;
		body.add(label, constraints);
		constraints.gridx = gridx + 1; constraints.gridy = gridy;
		body.add(field, constraints);
		gridy += 1;
	}
	
	protected void addSearchButton () {
		JButton button = new JButton("Search");
		constraints.gridx = 1; constraints.gridy = gridy;
		constraints.anchor = GridBagConstraints.LINE_END;
		body.add(button, constraints);
		gridy += 1;
	}
	
	protected JPanel getBody () {
		JPanel bodyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		ThemeFactory theme = Theme.instance().getThemeFactory();
		Colors colors = theme.getColors();
		bodyPanel.setBackground(colors.HOME_PANEL_BACKGROUND());
		bodyPanel.add(body);
		return bodyPanel;
	}
	
}
