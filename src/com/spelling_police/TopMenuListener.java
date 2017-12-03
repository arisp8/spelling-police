package com.spelling_police;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class TopMenuListener implements MouseListener {
	
	private CompoundBorder hoverBorder;
	private JLabel label;
	private EmptyBorder originalBorder;
	public TopMenuListener(JLabel label) {
		this.label = label;
		originalBorder = (EmptyBorder) this.label.getBorder();
		System.out.println(originalBorder.getBorderInsets());
		if (hoverBorder == null) {
			Border beveledBorder = BorderFactory.createLoweredSoftBevelBorder();
			System.out.println(originalBorder.getBorderInsets());
			Insets insets = originalBorder.getBorderInsets();
			
			// Decrease padding size to avoid choppy effects.
			insets.top = insets.top == 0 ? 0 : insets.top - 3;
			insets.left = insets.left == 0 ? 0 : insets.left - 3;
			insets.bottom = insets.bottom == 0 ? 0 : insets.bottom - 3;
			insets.right = insets.right == 0 ? 0 : insets.right - 3;
					
			EmptyBorder innerPadding = new EmptyBorder(insets);
			hoverBorder = BorderFactory.createCompoundBorder(beveledBorder, innerPadding);
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		this.label.setBorder(hoverBorder);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		this.label.setBorder(originalBorder);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
