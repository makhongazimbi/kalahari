/*
 * Copyright (c) 1995 - 2008 Sun Microsystems, Inc.  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Sun Microsystems nor the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.code83.utils;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;

import com.code83.modules.filemods.FileSearch;
import com.code83.ui.gui.commands.CommandRegister;

import java.awt.*;
import java.awt.event.*;

/**
 * Modified by Makho Ngazimbi for Kalahari Project.
 *
 * Component to be used as tabComponent;
 * Contains a JLabel to show the text and 
 * a JButton to close the tab it belongs to 
 */
public class ButtonTabComponent extends JPanel {

    /**
     * Serial version.
     */
    private static final long serialVersionUID = -581787921752375100L;

    /**
     * Tabbed pane.
     */
    private final JTabbedPane pane;

    /**
     * Maximum tab title length.
     */
    private static final int MAX_TAB_TITLE_LENGTH = 15;

    /**
     * Search ID.
     */
    private int searchId;

    public ButtonTabComponent (final JTabbedPane pane, final int search) {
        //unset default FlowLayout' gaps
        super(new FlowLayout(FlowLayout.LEFT, 0, 0));
        this.searchId = search;
        if (pane == null) {
            throw new NullPointerException("TabbedPane is null");
        }
        this.pane = pane;
        this.setOpaque(false);

        //make JLabel read titles from JTabbedPane
        JLabel label = new JLabel() {
            private static final long serialVersionUID = -704323704215703762L;

            public String getText () {
                int i = pane.indexOfTabComponent(ButtonTabComponent.this);
                if (i != -1) {
                    String title = pane.getTitleAt(i);
                    this.setToolTipText(title);
                    if (title.length() > MAX_TAB_TITLE_LENGTH) {
                        title = title.substring(0, MAX_TAB_TITLE_LENGTH - 3) +
                                "...";
                    }
                    return title;
                }
                return null;
            }
        };
        label.addMouseListener(new LabelListener(pane));
        this.add(label);
        //add more space between the label and the button
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
        //tab button
        JButton button = new TabButton();
        this.add(button);
        //add more space to the top of the component
        this.setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
    }

    /*
     * This Listener is necessary so that if the label on the tab is 
     * clicked on directly that tab will be selected.
     */
    private class LabelListener implements MouseListener {

        private JTabbedPane pane;

        public LabelListener (final JTabbedPane p) {
            this.pane = p;
        }

        public void mouseClicked (final MouseEvent arg0) {
            int index = this.pane.indexOfTabComponent(ButtonTabComponent.this);
            if (index != -1) {
                this.pane.setSelectedIndex(index);
            }
        }

        public void mouseEntered (final MouseEvent arg0) {
        }

        public void mouseExited (final MouseEvent arg0) {
        }

        public void mousePressed (final MouseEvent arg0) {
        }

        public void mouseReleased (final MouseEvent arg0) {
        }

    }

    private class TabButton extends JButton implements ActionListener {
        private static final long serialVersionUID = -625084717723674024L;

        public TabButton () {
            int size = 17;
            this.setPreferredSize(new Dimension(size, size));
            this.setToolTipText("Close this search");
            //Make the button looks the same for all Laf's
            this.setUI(new BasicButtonUI());
            //Make it transparent
            this.setContentAreaFilled(false);
            //No need to be focusable
            this.setFocusable(false);
            //setBorder(BorderFactory.createEtchedBorder());
            this.setBorderPainted(false);
            //Making nice rollover effect
            //we use the same listener for all buttons
            //addMouseListener(buttonMouseListener);
            this.setRolloverEnabled(true);
            //Close the proper tab by clicking the button
            this.addActionListener(this);
        }

        public void actionPerformed (final ActionEvent e) {
            FileSearch searches = FileSearch.instance();
            searches.removeSearch(ButtonTabComponent.this.searchId);
            int i = ButtonTabComponent.this.pane
                    .indexOfTabComponent(ButtonTabComponent.this);
            if (i != -1) {
                ButtonTabComponent.this.pane.remove(i);
            }
            if (ButtonTabComponent.this.pane.getTabCount() < 1) {
                CommandRegister commands = CommandRegister.instance();
                commands.getCommand("go_home").execute();
            }
        }

        //we don't want to update UI for this button
        public void updateUI () {
        }

        //paint the cross
        protected void paintComponent (final Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            //shift the image for pressed buttons
            if (this.getModel().isPressed()) {
                g2.translate(1, 1);
            }
            g2.setStroke(new BasicStroke(1.0f));
            g2.setColor(new Color(138, 138, 138));
            if (this.getModel().isRollover()) {
                g2.setColor(new Color(230, 0, 23));
            }
            int delta = 11;
            g2.drawLine(delta, delta, this.getWidth() - delta - 1, this.getHeight()
                    - delta - 1);
            g2.drawLine(delta + 1, delta, this.getWidth() - delta, this.getHeight()
                    - delta - 1);

            g2.drawLine(this.getWidth() - delta - 1, delta, delta, this.getHeight()
                    - delta - 1);
            g2.drawLine(this.getWidth() - delta, delta, delta + 1, this.getHeight()
                    - delta - 1);
            g2.dispose();
        }
    }
}
