package com.code83.ui.gui.panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import com.code83.ui.gui.themes.Colors;
import com.code83.ui.gui.themes.Icons;
import com.code83.ui.gui.themes.Theme;
import com.code83.ui.gui.themes.ThemeFactory;

/**
 * 
 *   DownloadsPanel.java 770 2010-04-10 00:00:26Z mngazimb $
 * 
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version $Id: DownloadsPanel.java 865 2011-12-15 03:35:16Z mngazimb $
 * @since 0.1
 */
public class DownloadsPanel extends JPanel {

    private static final long serialVersionUID = 4834875465959625635L;
    private static final int HEIGHT = 60;
    private static final int MAX_TITLE_LENGTH = 15;

    public DownloadsPanel (final int width) {
        ThemeFactory theme = Theme.instance().getThemeFactory();
        Colors colors = theme.getColors();

        this.setPreferredSize(new Dimension(width, HEIGHT));
        this.setBackground(colors.DOWNLOAD_PANEL_BACKGROUND());
        FlowLayout leftAlign = new FlowLayout();
        leftAlign.setAlignment(FlowLayout.LEFT);
        this.setLayout(leftAlign);
        Color color = colors.DOWNLOAD_PANEL_BORDER();
        Border border = BorderFactory.createMatteBorder(0, 0, 1, 0, color);
        this.setBorder(border);
        this.addTransferObjects();
    }

    private void addTransferObjects () {
        Collection<JPanel> transfers = this.getTransferObjects();
        for (JPanel transfer : transfers ) {
            this.add(transfer);
        }
    }

    //TODO
    private Collection<JPanel> getTransferObjects () {
        // TODO fetch all the tranfer objects in progress and add them to the 
        // downloads panel
        JPanel download = this.createTransfersIconPanel(true, "macy_gray.mp3");
        JPanel upload =
                this
                .createTransfersIconPanel(false, "inglorious_basterds.wav");

        ArrayList<JPanel> transfers = new ArrayList<JPanel>();
        transfers.add(download);
        transfers.add(upload);
        return transfers;
    }

    private JPanel createTransfersIconPanel (final boolean download,
            final String title) {
        ThemeFactory theme = Theme.instance().getThemeFactory();
        Colors colors = theme.getColors();
        Icons icons = theme.getIcons();

        FlowLayout zeroGapCenterAlign = new FlowLayout();
        zeroGapCenterAlign.setAlignment(FlowLayout.CENTER);
        zeroGapCenterAlign.setHgap(0);
        zeroGapCenterAlign.setVgap(0);

        JPanel container = new JPanel();
        container.setLayout(zeroGapCenterAlign);
        JPanel panel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(boxLayout);

        JLabel icon = new JLabel(new ImageIcon(icons.UPLOADS_ICON_32X32()));
        if (download) {
            icon.setIcon(new ImageIcon(icons.DOWNLOADS_ICON_32X32()));
        }

        JPanel iconPanel = new JPanel();
        iconPanel.setLayout(zeroGapCenterAlign);
        iconPanel.add(icon);
        iconPanel.setBackground(colors.DOWNLOAD_PANEL_BACKGROUND());

        String croppedTitle = title;
        if (title.length() > MAX_TITLE_LENGTH) {
            croppedTitle = title.substring(0, MAX_TITLE_LENGTH - 3) + "...";
        }

        JLabel text = new JLabel(croppedTitle);
        JPanel textPanel = new JPanel();
        textPanel.setLayout(zeroGapCenterAlign);
        textPanel.add(text);
        textPanel.setBackground(colors.DOWNLOAD_PANEL_BACKGROUND());

        panel.add(iconPanel);
        panel.add(textPanel);
        Border border = BorderFactory.createMatteBorder(0, 0, 0, 1,
                colors.TRANSFER_OBJECT_BORDER());

        container.add(panel);
        Component rigidArea = Box.createRigidArea(new Dimension(5, 0));
        container.add(rigidArea);
        container.setBorder(border);
        container.setBackground(colors.DOWNLOAD_PANEL_BACKGROUND());

        container.setToolTipText(this.createToolTipText(title));
        return container;
    }

    private String createToolTipText (final String title) {
        ThemeFactory theme = Theme.instance().getThemeFactory();
        Icons icons = theme.getIcons();
        String image = "file:" + icons.AUDIO_ICON_16X16();
        String text =
                "<html>" +
                "<img src=" + image + ">&nbsp; Audio file<br>" +
                "File: " + title + "<br>" +
                "Transfer speed: 123.5 kbps<br>" +
                "</html>";
        return text;
    }

    /**
     * Can be called by StatusUpdateThread to update the downloads panel
     */
    public void update () {
        this.removeAll();
        this.addTransferObjects();
    }
}
