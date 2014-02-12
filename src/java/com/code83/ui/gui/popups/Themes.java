package com.code83.ui.gui.popups;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

import com.code83.data.accessLayer.DataAccessObject;
import com.code83.ui.gui.GuiFrame;
import com.code83.ui.gui.commands.Command;
import com.code83.ui.gui.commands.CommandRegister;
import com.code83.ui.gui.commands.SetCoolNuvola;
import com.code83.ui.gui.commands.SetCoolTango;
import com.code83.ui.gui.commands.SetWarmNuvola;
import com.code83.ui.gui.commands.SetWarmTango;
import com.code83.ui.gui.themes.Colors;
import com.code83.ui.gui.themes.Icons;
import com.code83.ui.gui.themes.Theme;
import com.code83.ui.gui.themes.ThemeFactory;
import com.code83.utils.Images;



/**
 * 
 *   Themes.java 787 2010-04-18 16:25:54Z mngazimb $
 * 
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version $Id: Themes.java 865 2011-12-15 03:35:16Z mngazimb $
 * @since 0.1
 */
public class Themes extends JFrame {

	private static final long serialVersionUID = -2967285621863433894L;
	private Command command = null;
	private Icons icons;
	private Colors colors;
	private static final int HEIGHT = 500;
	private static final int WIDTH = 450;
	
	public Themes () {
		super("Select Theme");
	
		ThemeFactory theme = Theme.instance().getThemeFactory();
		icons = theme.getIcons();
		colors = theme.getColors();
		
		JPanel panel = createPane();
		panel.setBackground(colors.HOME_PANEL_BACKGROUND());
		add(panel);
		
		setSize(new Dimension(WIDTH, HEIGHT));
		setLocation(300, 300);
		setAlwaysOnTop(true);
	
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		addWindowListener(new PopupListener());
		
		setIconImage(new ImageIcon(icons.KALAHARI_ICON_16x16()).getImage());
		
		CommandRegister commands = CommandRegister.instance();
		commands.addCommand("cool_tango", new SetCoolTango());
		commands.addCommand("warm_tango", new SetWarmTango());
		commands.addCommand("cool_nuvola", new SetCoolNuvola());
		commands.addCommand("warm_nuvola", new SetWarmNuvola());
		
		GuiFrame.instance().setEnabled(false);
	}
	
	/**
     * Used by createSimpleDialogBox and createFeatureDialogBox
     * to create a pane containing a description, a single column
     * of radio buttons, and the Show it! button.
     */
    private JPanel createPane () {
        JPanel box = new JPanel();
        box.setBackground(colors.HOME_PANEL_BACKGROUND());
        box.setLayout(new GridLayout(2,2));
        
        ButtonGroup group = new ButtonGroup();
        JPanel hardy = themePanel(Images.HARDY, "Hardy", group);
        JPanel intrepid = themePanel(Images.INTREPID, "Intrepid", group);
        JPanel jaunty = themePanel(Images.JAUNTY, "Jaunty", group);
        JPanel karmic = themePanel(Images.KARMIC, "Karmic", group);
        
        box.add(hardy);
        box.add(intrepid);
        box.add(jaunty);
        box.add(karmic);        

        JButton apply = new JButton("Apply");
        apply.addActionListener(new ApplyListener());
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(new CloseWindowListener());
        
        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttons.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, 
        		colors.DOWNLOAD_PANEL_BORDER()));
        
        buttons.add(apply);
        buttons.add(cancel);
        buttons.setBackground(colors.STATUS_PANEL_VISIBLE());
        
        JPanel top = new JPanel();
        top.setBackground(colors.HOME_PANEL_BACKGROUND());
        top.setLayout(new BoxLayout(top, BoxLayout.PAGE_AXIS));
        JPanel title = new JPanel(new FlowLayout(FlowLayout.LEFT));
        title.add(new JLabel("Select a theme"));
        title.setBackground(colors.HOME_PANEL_BACKGROUND());
        top.add(title);
        top.add(Box.createRigidArea(new Dimension(0, 10)));
        top.add(box);
        JPanel pane = new JPanel(new BorderLayout());
        pane.add(top, BorderLayout.PAGE_START);
        pane.add(buttons, BorderLayout.PAGE_END);
        return pane;
    }
    
    private JPanel themePanel (String imagePath, String name, ButtonGroup buttonGroup) {
    	JPanel panel = new JPanel();
    	panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
    	ImageIcon icon = new ImageIcon(imagePath);
    	JLabel image = new JLabel(icon);
    	Border border = BorderFactory.createMatteBorder(1, 1, 1, 1, 
    			colors.FOOTER_PANEL_BORDER());
    	image.setBorder(border);
    	panel.add(image);
    	panel.setBackground(colors.HOME_PANEL_BACKGROUND());
    	
    	DataAccessObject dao = new DataAccessObject();
    	String theme = dao.getSettingsDao().getTheme();
    	JRadioButton radioButton = new JRadioButton(name);
    	radioButton.setActionCommand(name.toLowerCase());
    	radioButton.addActionListener(new ApplyListener());
    	if (theme.equals("COOL_TANGO")) {
    		radioButton.setSelected(true);
    	}
        buttonGroup.add(radioButton);
    	panel.add(radioButton);
        panel.addMouseListener(new ImageListener());
    	return panel;
    }

	private class CloseWindowListener implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			dispose();
			GuiFrame.instance().setEnabled(true);
		}
	}
	
	private class ImageListener implements MouseListener {

		public void mouseClicked (MouseEvent e) {
			
		}

		public void mouseEntered (MouseEvent e) {}
		public void mouseExited (MouseEvent e) {}
		public void mousePressed (MouseEvent e) {}
		public void mouseReleased (MouseEvent e) {}
		
	}
	
	private class ApplyListener implements ActionListener {
		
		public void actionPerformed (ActionEvent e) {
			String action = e.getActionCommand();
			CommandRegister commands = CommandRegister.instance();
			
			if (action.equals("hardy")) {
				command = commands.getCommand("cool_tango");
			} else if (action.equals("intrepid")) {
				command = commands.getCommand("warm_tango");
			} else if (action.equals("jaunty")) {
				command = commands.getCommand("cool_nuvola");
			} else if (action.equals("karmic")) {
				command = commands.getCommand("warm_nuvola");
			} else if (action.equals("Apply")) {
				dispose();
				ThemeFactory theme = Theme.instance().getThemeFactory();
				Icons icons = theme.getIcons(); 
				ImageIcon icon = new ImageIcon(icons.INFO_ICON_32x32());
				JOptionPane.showMessageDialog(
						GuiFrame.instance().getContentPane(),
					    "Kalahari must be restarted before the new theme " +
					    "takes effect", "Choose theme", 
					    JOptionPane.INFORMATION_MESSAGE, icon);
				command.execute();
				GuiFrame.instance().setEnabled(true);
			}
		}
	}
}
