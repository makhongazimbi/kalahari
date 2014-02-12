package com.code83.utils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;

/**
 * This class displays the splash image.
 * TODO No need for this to be a singleton. This class can be refactored. 
 * 
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version $Id: Splash.java 866 2011-12-17 06:01:37Z mngazimb $
 * @since 0.1
 */
public class Splash extends JWindow {
    private static final long serialVersionUID = -9190584653265032905L;
    private static Splash instance = null;
    private String filename;
    private WaitRunner wait_runner = null;
    private static final int WAIT_TIME = 100;

    public static void main (String[] args) {
        Splash splash = Splash.instance();
        splash.display();
    }

    /**
     * 
     * @param frame A lightweight frame that will contain the image.
     */
    private Splash (Frame frame) {
        filename = Images.SPLASH;
    }

    /**
     * A method used to create an instance of this class.
     * @return Splash An instance of this class.
     */
    public static Splash instance () {
        if (instance == null) {
            Frame f = new Frame();
            instance = new Splash(f);
        }
        return instance;
    }

    /**
     * This method is useful in multi-screen environments. It returns the
     * dimensions of the primary screen so that the splash image can be 
     * centered on one screen. Instead of the using the center of the 
     * virtual screen which may be inappropriate. 
     * @return Dimension object of the primary screen. 
     */
    public static Dimension getPrimaryScreenDimension () {
        Dimension dimension = new Dimension();

        GraphicsEnvironment ge = GraphicsEnvironment
                .getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = ge.getScreenDevices();
        for (GraphicsDevice device : gs) {
            Rectangle bounds = device.getDefaultConfiguration().getBounds();

            if (bounds.getX() == 0.0 && bounds.getY() == 0.0) {
                dimension.setSize(bounds.getWidth(), bounds.getHeight());
            }
        }

        return dimension;
    }

    /**
     * This method actually displays the splash.
     */
    public void display () {
        /* allows BMP file formats to be handled */
        File file = new File(filename);
        BufferedImage image = null;

        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            System.out.println(filename);
            e.printStackTrace();
            return;
        }

        Font font = new Font("Arial", Font.BOLD, 12);
        Graphics graphics = image.getGraphics();
        graphics.setFont(font);
        graphics.setColor(new Color(0, 0, 0));
        graphics.drawString("Loading Kalahari...", 10, 10);

        JLabel l = new JLabel(new ImageIcon(image));
        Container pane = getContentPane();
        pane.add(l, BorderLayout.CENTER);
        pack();
        //Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        Dimension screenSize = getPrimaryScreenDimension();
        Dimension labelSize = l.getPreferredSize();

        setLocation(screenSize.width / 2 - (labelSize.width / 2),
                screenSize.height / 2 - (labelSize.height / 2));

        // allow splash to close by clicking on it
        /*addMouseListener(new MouseAdapter()
            {
                public void mousePressed(MouseEvent e)
                {
                    setVisible(false);
                    dispose();
                }
            });*/
        //final int pause = WAIT_TIME;
        final Runnable closerRunner = new Runnable() {
            public void run () {
                setVisible(false);
                dispose();
            }
        };
        /*Runnable waitRunner = new Runnable()
            {
                public void run()
                {
                    try
                        {
                            Thread.sleep(pause);
                            SwingUtilities.invokeAndWait(closerRunner);
                        }
                    catch(Exception e)
                        {
                            e.printStackTrace();
                            // can catch InvocationTargetException
                            // can catch InterruptedException
                        }
                }
            };*/
        setVisible(true);
        //Thread splashThread = new Thread(waitRunner, "SplashThread");
        wait_runner = new WaitRunner(closerRunner);
        Thread splashThread = new Thread(wait_runner, "SplashThread");
        splashThread.start();
    }

    public void close () {
        if (wait_runner == null) {
            return;
        }
        wait_runner.close();
    }

    private class WaitRunner implements Runnable {
        private Runnable closerRunner;
        private boolean run = true;

        public WaitRunner (Runnable cr) {
            closerRunner = cr;
        }

        public void close () {
            run = false;
        }

        public void run () {
            try {
                while (run) {
                    Thread.sleep(WAIT_TIME);
                }
                SwingUtilities.invokeAndWait(closerRunner);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setText (String text) {

    }
}
