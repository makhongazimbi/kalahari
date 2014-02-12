package com.code83.ui.gui.popups;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.code83.ui.gui.GuiFrame;
import com.code83.ui.gui.themes.Icons;
import com.code83.ui.gui.themes.Theme;
import com.code83.ui.gui.themes.ThemeFactory;

/**
 * The "About Kalahari" pop up window
 *
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version $Id: About.java 874 2012-05-10 16:56:46Z mngazimb $
 * @since 0.1
 */
public class About extends JFrame {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 6212820204631525038L;

    /**
     * Logger.
     */
    private Logger logger = LoggerFactory.getLogger(About.class);

    /**
     * Panel width.
     */
    private static final int WIDTH = 300;

    /**
     * Panel height.
     */
    private static final int HEIGHT = 300;

    /**
     * Theme factory.
     */
    private ThemeFactory themeFactory;

    /**
     * Default constructor for About panel.
     */
    public About () {
        super("About Kalahari");

        this.themeFactory = Theme.instance().getThemeFactory();

        JPanel panel = this.createPane();
        this.setPanelBackground(panel);
        this.add(panel);

        this.setSize(new Dimension(WIDTH, HEIGHT));
        this.setLocation(300, 300);
        this.setAlwaysOnTop(true);
        GuiFrame.instance().setEnabled(false);
        this.addWindowListener(new PopupListener());

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        Icons icons = this.themeFactory.getIcons();
        this.setIconImage(new ImageIcon(icons.KALAHARI_ICON_16x16())
                .getImage());
    }

    /**
     * Set panel background.
     */
    public void setPanelBackground (JPanel panel) {
        panel.setBackground(
                this.themeFactory.getColors().HOME_PANEL_BACKGROUND());
    }

    /**
     * Used by createSimpleDialogBox and createFeatureDialogBox
     * to create a pane containing a description, a single column
     * of radio buttons, and the Show it! button.
     */
    private JPanel createPane () {
        JPanel box = new JPanel();
        this.setPanelBackground(box);

        Icons icons = this.themeFactory.getIcons();

        JPanel icon = new JPanel(new GridLayout(1, 1));
        this.setPanelBackground(icon);
        JLabel iconLabel = new JLabel(
                new ImageIcon(icons.KALAHARI_ICON_48X48()), JLabel.CENTER);
        icon.add(iconLabel);

        JPanel labelA = new JPanel(new GridLayout(1, 1));
        this.setPanelBackground(labelA);
        JLabel textA = new JLabel("Kalahari Nomad 0.1", JLabel.CENTER);
        textA.setFont(new Font("Arial", Font.BOLD, 28));
        labelA.add(textA);

        JPanel labelB = new JPanel(new GridLayout(1, 1));
        this.setPanelBackground(labelB);
        JLabel textB =
                new JLabel(
                "Copyright \u00a9 2009-2012 code83, Inc and Others",
                JLabel.CENTER);
        textB.setFont(new Font("Arial", Font.PLAIN, 12));
        labelB.add(textB);

        JPanel labelC = new JPanel(new GridLayout(1, 1));
        this.setPanelBackground(labelC);
        JLabel textC = new JLabel("http://www.code83.com/Kalahari",
                JLabel.CENTER);
        textC.setFont(new Font("Arial", Font.PLAIN, 14));
        labelC.add(textC);

        box.setLayout(new BoxLayout(box, BoxLayout.PAGE_AXIS));
        box.setAlignmentX(CENTER_ALIGNMENT);
        box.add(Box.createRigidArea(new Dimension(0, 10)));
        box.add(icon);
        box.add(Box.createRigidArea(new Dimension(0, 15)));
        box.add(labelA);
        box.add(Box.createRigidArea(new Dimension(0, 15)));
        box.add(labelB);
        box.add(Box.createRigidArea(new Dimension(0, 15)));
        box.add(labelC);

        JButton close = new JButton("Close",
                new ImageIcon(icons.CLOSE_ICON_16x16()));
        close.addActionListener(new CloseWindowListener());

        JPanel buttons = new JPanel();
        this.setPanelBackground(buttons);
        buttons.add(close);

        JPanel pane = new JPanel(new BorderLayout());
        this.setPanelBackground(pane);
        pane.add(box, BorderLayout.PAGE_START);
        pane.add(buttons, BorderLayout.PAGE_END);
        return pane;
    }

    /**
     * Close window listener.
     */
    private class CloseWindowListener implements ActionListener {

        /**
         * Close the popup window when close button is clicked.
         */
        public void actionPerformed (ActionEvent e) {
            About.this.logger.debug("");
            About.this.dispose();
        }
    }
}
