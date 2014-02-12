package com.code83.ui.gui;

import java.awt.Container;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.code83.data.accessLayer.DataAccessObject;
import com.code83.data.provider.sqlite.SettingsProvider;
import com.code83.ui.gui.commands.CommandRegister;
import com.code83.ui.gui.commands.SaveWindowDimensions;
import com.code83.ui.gui.menu.MenuBar;
import com.code83.ui.gui.panels.ContentPanel;
import com.code83.ui.gui.themes.Icons;
import com.code83.ui.gui.themes.Theme;
import com.code83.ui.gui.themes.ThemeFactory;



/**
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version $Id: GuiFrame.java 865 2011-12-15 03:35:16Z mngazimb $
 * @since 0.1
 */
public class GuiFrame extends JFrame {

    private static final long serialVersionUID = -8133021777838280507L;
    private static final int MINIMUM_WIDTH = 650;
    private static final int MINIMUM_HEIGHT = 550;
    private static GuiFrame frame = null;

    private GuiFrame () {
        super("Kalahari Nomad");

        MenuBar menubar = new MenuBar();
        setJMenuBar(menubar);

        DataAccessObject dao = new DataAccessObject();
        SettingsProvider settingsDao = dao.getSettingsDao();
        int[] dimensions = settingsDao.getWindowDimensions();
        int x = dimensions[0];
        int y = dimensions[1];
        int width = dimensions[2];
        int height = dimensions[3];
        setSize(width, height);
        setLocation(x, y);
        setMinimumSize(new Dimension(MINIMUM_WIDTH, MINIMUM_HEIGHT));

        Container framepane = getContentPane();
        ContentPanel contentpanel = new ContentPanel(framepane.getWidth(),
                framepane.getHeight());
        add(contentpanel);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        ThemeFactory theme = Theme.instance().getThemeFactory();
        Icons icons = theme.getIcons();
        setIconImage(new ImageIcon(icons.KALAHARI_ICON_16x16()).getImage());
        CommandRegister commands = CommandRegister.instance();
        commands.addCommand("save_window_dimensions",
                new SaveWindowDimensions(this));
    }

    public static GuiFrame instance () {
        if (frame == null) {
            frame = new GuiFrame();
        }
        return frame;
    }

    public void reloadGui () {
        /*
         * frame.setVisible(false); frame.dispose(); try { Thread.sleep(500);
         * } catch (InterruptedException e) { // TODO log this error
         * e.printStackTrace(); } frame.setVisible(true);
         */
    }

    public static void setLookAndFeel (LookAndFeels laf) {
        String lookAndFeel = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";

        if (laf.equals(LookAndFeels.NIMBUS)) {
            lookAndFeel = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
        } else if (laf.equals(LookAndFeels.NATIVE)) {
            lookAndFeel = UIManager.getSystemLookAndFeelClassName();
        }
        try {
            UIManager.setLookAndFeel(lookAndFeel);
        } catch (ClassNotFoundException e) {
            // TODO
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    public enum LookAndFeels {
        NIMBUS, NATIVE
    }

}
