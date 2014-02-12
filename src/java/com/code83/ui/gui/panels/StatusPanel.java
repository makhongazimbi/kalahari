package com.code83.ui.gui.panels;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.code83.modules.status.Status;
import com.code83.modules.status.NetworkStatus.NetworkVisibilityStatus;
import com.code83.ui.gui.commands.CommandRegister;
import com.code83.ui.gui.commands.HideInvisibleBanner;
import com.code83.ui.gui.commands.Invisible;
import com.code83.ui.gui.commands.ShowInvisibleBanner;
import com.code83.ui.gui.commands.ShowNoNetworkStatus;
import com.code83.ui.gui.commands.Visible;
import com.code83.ui.gui.themes.Colors;
import com.code83.ui.gui.themes.Icons;
import com.code83.ui.gui.themes.Theme;
import com.code83.ui.gui.themes.ThemeFactory;


/**
 * The status bar at the bottom of the Kalahari window that shows system
 * information including number of connections, uploads, downloads, and
 * files shared.
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version $Id: StatusPanel.java 905 2012-09-14 22:49:26Z mngazimb $
 * @since 0.1
 * @see FooterPanel
 */
public class StatusPanel extends JPanel {

    private static final long serialVersionUID = -3684180854661680826L;
    //private boolean visibleFlag = true;
    private JPanel currentPanel = new InvisiblePanel();

    /**
     * Default constructor.
     */
    public StatusPanel (int width) {
        CommandRegister commands = CommandRegister.instance();
        commands.addCommand("visible_mode", new Visible());
        commands.addCommand("invisible_mode", new Invisible());
        commands.addCommand("no_network", new ShowNoNetworkStatus(this));
        commands.addCommand("show_invisible_banner",
                new ShowInvisibleBanner(this));
        commands.addCommand("hide_invisible_banner",
                new HideInvisibleBanner(this));

        ThemeFactory theme = Theme.instance().getThemeFactory();
        Colors colors = theme.getColors();
        Color visible = colors.STATUS_PANEL_VISIBLE();
        this.setBackground(visible);
        FlowLayout layout = new FlowLayout(FlowLayout.LEFT);
        layout.setVgap(0);
        this.setLayout(layout);
        this.add(this.currentPanel);
    }

    /**
     * Set panel to invisible status.
     */
    public void invisible () {
        /* show panel indicating invisible mode */
        GridLayout layout = new GridLayout();
        layout.setVgap(0);
        this.setLayout(layout);
        this.setVisibility(false);
        this.remove(this.currentPanel);
        this.removeAll();
        this.currentPanel = new InvisiblePanel();
        this.add(this.currentPanel);
    }

    public void visible () {
        /* show regular stuff */
        FlowLayout layout = new FlowLayout(FlowLayout.LEFT);
        layout.setVgap(0);
        this.setLayout(layout);
        this.setVisibility(true);
        this.remove(this.currentPanel);
        this.currentPanel = new VisiblePanel();
        this.add(this.currentPanel);
    }

    /**
     * Update the status panel
     */
    public void update () {
        Status status = Status.getInstance();
        NetworkVisibilityStatus nvs = status.getNetworkStatus()
                .getNomadStatus();
        if (nvs.equals(NetworkVisibilityStatus.OFFLINE)) {
            this.noNetworkStatus();
        } else if (nvs.equals(NetworkVisibilityStatus.INVISIBLE)) {
            this.invisible();
        } else {
            this.visible();
        }
    }

    public void noNetworkStatus () {
        GridLayout layout = new GridLayout();
        layout.setVgap(0);
        this.setLayout(layout);
        this.remove(this.currentPanel);
        this.removeAll();
        this.currentPanel = new NoNetworkPanel();
        this.add(this.currentPanel);
    }

    /*
     * Accessed by main thread and StatusUpdateThread
     */
    private void setVisibility (boolean visibilty) {
        //visibleFlag = visibilty;
    }

    /*
     * Accessed by main thread and StatusUpdateThread
     */
    /*private boolean isPanelVisible () {
return visibleFlag;
    }*/

    private class VisiblePanel extends JPanel {
        private static final long serialVersionUID = -3620062671605102515L;

        public VisiblePanel () {
            ThemeFactory theme = Theme.instance().getThemeFactory();
            Colors colors = theme.getColors();
            Icons icons = theme.getIcons();
            Color visible = colors.STATUS_PANEL_VISIBLE();

            this.setBackground(visible);
            FlowLayout layout = new FlowLayout(FlowLayout.LEFT);
            layout.setVgap(0);
            this.setLayout(layout);

            StatusItem connections = new StatusItem(
                    icons.FRIENDS_ICON_16x16(),
                    "connections.");
            connections.refresh(Status.getInstance().getNomadStatus()
                    .getNomadConnections());
            this.add(connections);

            StatusItem uploads = new StatusItem(icons.UPLOADS_ICON_16x16(),
                    "uploads.");
            uploads.refresh(0);
            this.add(uploads);

            StatusItem downloads = new StatusItem(
                    icons.DOWNLOADS_ICON_16x16(),
                    "downloads.");
            downloads.refresh(0);
            this.add(downloads);

            StatusItem sharing = new StatusItem(icons.SHARING_ICON_16x16(),
                    "files shared.");
            sharing.refresh(0);
            this.add(sharing);
        }
    }

    private class InvisiblePanel extends JPanel {
        private static final long serialVersionUID = -68168384090520601L;

        public InvisiblePanel () {
            ThemeFactory theme = Theme.instance().getThemeFactory();
            Colors colors = theme.getColors();
            Icons icons = theme.getIcons();
            Color invisible = colors.STATUS_PANEL_INVISIBLE();

            this.setBackground(invisible);
            FlowLayout layout = new FlowLayout(FlowLayout.LEFT);
            layout.setVgap(0);
            this.setLayout(layout);
            StatusItem notice = new StatusItem(icons.ALERT_ICON_16x16(),
                    "Invisible Mode");
            notice.background(invisible);
            notice.setToolTipText("You appear offline to other Nomads when "
                    + "Invisible Mode is set.");

            this.add(notice);
        }
    }

    private class NoNetworkPanel extends JPanel {
        private static final long serialVersionUID = 4607857307235902861L;

        public NoNetworkPanel () {
            ThemeFactory theme = Theme.instance().getThemeFactory();
            Colors colors = theme.getColors();
            Icons icons = theme.getIcons();
            Color invisible = colors.STATUS_PANEL_INVISIBLE();

            this.setBackground(invisible);
            FlowLayout layout = new FlowLayout(FlowLayout.LEFT);
            layout.setVgap(0);
            this.setLayout(layout);
            StatusItem notice = new StatusItem(icons.NO_NETWORK_ICON_16X16(),
                    "Unable to connect to network");
            notice.setToolTipText("Please check your network connection");
            notice.background(invisible);

            this.add(notice);
        }
    }

    private class StatusItem extends JPanel {
        private static final long serialVersionUID = 1427688317526609630L;
        private String image;
        private String description;

        public StatusItem (String img, String desc) {
            this.image = img;
            this.description = desc;
            ThemeFactory theme = Theme.instance().getThemeFactory();
            Colors colors = theme.getColors();
            this.setBackground(colors.STATUS_ITEM_BACKGROUND());
        }

        public void background (Color c) {
            this.setBackground(c);
            JLabel img = new JLabel(new ImageIcon(this.image));
            JLabel text = new JLabel(this.description);
            this.add(img);
            this.add(text);
        }

        public void refresh (int value) {
            this.removeAll();
            JLabel img = new JLabel(new ImageIcon(this.image));
            JLabel text = new JLabel(value + " " + this.description);
            this.add(img);
            this.add(text);
        }
    }

}
