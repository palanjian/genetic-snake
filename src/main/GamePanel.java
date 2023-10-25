package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.Serial;

import javax.swing.JPanel;

import simulation.SnakeGame;
import snake.Board;
import snake.Snake;

public class GamePanel extends JPanel{
	
	@Serial
	private static final long serialVersionUID = 1L;
	private final int tileSize = 64;
	private final int columns = Config.rows;
	private final int rows = Config.columns;
	private final int screenWidth = tileSize * rows;
	private final int screenHeight = tileSize * columns;
	
	private SnakeGame snakeGame;
	private Board board;
	private Snake snake;
	
	public GamePanel(SnakeGame snakeGame) {
		this.snakeGame = snakeGame;
		this.board = snakeGame.getBoard();
		this.snake = snakeGame.getSnake();
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.BLACK);
		this.setFocusable(true);
	}

	public void paint(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		snake.draw(g2);
		board.draw(g2);
		g2.dispose();
	}
	
	public int getTileSize() { return tileSize; }
}
