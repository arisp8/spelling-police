package com.spelling_police;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MainIconListener implements MouseListener {
	
	private JLabel label;
	private static String imagesPath = System.getProperty("user.dir") + "\\resources\\images\\";
	private ImageIcon defaultIcon;
	private ImageIcon hoverIcon;
	
	private JFrame parentFrame;
	private ApplicationWindow window;
	
	public MainIconListener(JLabel label, ApplicationWindow window) {
		this.label = label;
		this.parentFrame = parentFrame;
		defaultIcon = (ImageIcon) this.label.getIcon();
		this.window = window;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		switch(e.getComponent().getName()) {
			case "text-editor":
				this.window.createTextEditorPage();
				break;
			case "from-file":
				this.window.loadFileFromSystem();
				break;
			case "from-url":
				this.window.loadFromRemoteURL();
				break;
			case "from-image":
				// 
				break;
		}
	}

	private void prepareHoverIcon() {
		Icon image = this.label.getIcon();
		Image iconImage = iconToImage(image);
		BufferedImage original = toBufferedImage(iconImage);
		BufferedImage overlay;

		try {
			overlay = ImageIO.read(new File(imagesPath, "Overlay.png"));
		} catch (IOException error) {
			error.printStackTrace();
			return;
		}

		int w = Math.max(original.getWidth(), overlay.getWidth());
		int h = Math.max(original.getHeight(), overlay.getHeight());
		BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		
		Graphics g = combined.getGraphics();
		g.drawImage(original, 0, 0, null);
		g.drawImage(overlay, 0, 0, null);
		
		hoverIcon = new ImageIcon(combined);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
		if (hoverIcon == null) {
			this.prepareHoverIcon();
		}
		this.label.setIcon(hoverIcon);
	}

	private static BufferedImage toBufferedImage(Image src) {
        int w = src.getWidth(null);
        int h = src.getHeight(null);
        int type = BufferedImage.TYPE_INT_ARGB;  // other options
        BufferedImage dest = new BufferedImage(w, h, type);
        Graphics2D g2 = dest.createGraphics();
        g2.drawImage(src, 0, 0, null);
        g2.dispose();
        return dest;
    }

	private static Image iconToImage(Icon icon) {
	   if (icon instanceof ImageIcon) {
		   return ((ImageIcon)icon).getImage();
	   } else {
		   	int w = icon.getIconWidth();
	      	int h = icon.getIconHeight();
	      	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	      	GraphicsDevice gd = ge.getDefaultScreenDevice();
	      	GraphicsConfiguration gc = gd.getDefaultConfiguration();
	      	BufferedImage image = gc.createCompatibleImage(w, h);
	      	Graphics2D g = image.createGraphics();
      		icon.paintIcon(null, g, 0, 0);
      		g.dispose();
			return image;
	   }
	}

	@Override
	public void mouseExited(MouseEvent e) {
		this.label.setIcon(defaultIcon);
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
