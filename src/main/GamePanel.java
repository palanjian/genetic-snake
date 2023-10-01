package main;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;

public class GamePanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	final int tileSize = 32;
	final int columns = 20;
	final int rows = 20;
	final int screenWidth = tileSize * rows;
	final int screenHeight = tileSize * columns;
		
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.BLACK);
		this.setFocusable(true);
	}
}
