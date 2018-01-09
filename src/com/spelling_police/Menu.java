package com.spelling_police;

import javax.swing.*;
import java.awt.event.*;

public class Menu {
	
	public static void main(String args[]){
		JFrame frame = new JFrame("Menu");
		frame.setVisible(true);
		frame.setSize(400,200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar jmenubar = new JMenuBar();
		frame.setJMenuBar(jmenubar);
		
		JMenu file = new JMenu("File");
		jmenubar.add(file);
		JMenuItem new1 = new JMenuItem("New");
		JMenuItem open = new JMenuItem("Open");
		JMenuItem save = new JMenuItem("Save");
		JMenuItem exit = new JMenuItem("Exit");
		file.add(new1);
		file.add(open);
		file.add(save);
		file.add(exit);
		
		JMenu options = new JMenu("Options");
		jmenubar.add(options);
		JToggleButton toggle1 =  new JToggleButton("Greek");
		JToggleButton toggle2 =  new JToggleButton("English");
		options.add(toggle1);
		options.add(toggle2);
		
		class exitaction implements ActionListener {
			public void actionPerformed (ActionEvent e){
				System.exit(0);
			}
		}
		exit.addActionListener(new exitaction());
	}
}
