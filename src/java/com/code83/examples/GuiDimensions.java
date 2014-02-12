package com.code83.examples;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GuiDimensions extends JFrame {

	private static final long serialVersionUID = 7903576719920445577L;
	private static final int WIDTH = 200;
	private static final int HEIGHT = 200;
	private static GuiDimensions gui = null;
	
	private GuiDimensions () {
		super("Kalahari Nomad version 0.1 (alpha release)");
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(new Dimension(WIDTH, HEIGHT));
		setMinimumSize(new Dimension(WIDTH, HEIGHT));
		
		JPanel panel = new JPanel();
		JButton button = new JButton("Push Me");
		ActionListener actionListener = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				System.out.println("Width: "+getWidth()+", Height: "
						+getHeight());
			}
		};
		button.addActionListener(actionListener);
		panel.add(button);
		add(panel);
	}
	
	public static GuiDimensions instance () {
		if (gui==null) {
			gui = new GuiDimensions();
		}
		return gui;
	}
	
	public static void main (String[] args) {
		ShutdownHook hook = new ShutdownHook();
		Runtime.getRuntime().addShutdownHook(hook);
		
		GuiDimensions g = GuiDimensions.instance();
		g.setVisible(true);
	}
}


class ShutdownHook extends Thread {
	public void run () {
		System.out.println("SHUTDOWN>>\n width: "+
				GuiDimensions.instance().getWidth()+", height: "+
				GuiDimensions.instance().getHeight());
	}
}