package snake;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;
import main.Config;
import simulation.SnakeGame;

public class Board {
	private int rows;
	private int columns;
	private Cell[][] cells;
	private Random random;
	private SnakeGame snakeGame;
	private int foodRow;
	private int foodColumn;
	
	public Board(SnakeGame snakeGame, int rows, int columns) {
		this.snakeGame = snakeGame;
		this.rows = rows;
		this.columns = columns;
		this.cells = new Cell[rows][columns];
		random = new Random(Config.simulationSeed);
		
		initializeCells();
	}

	//allocates each cell in memory and initializes its default value 
	private void initializeCells() {
		for(int i=0; i<rows; ++i) {
			for(int j=0; j<columns; ++j) {
				this.cells[i][j] = new Cell(i, j, CellType.EMPTY);
			}
		}		
	}

	//creates a new food cell wherever there exists room on the board
	public void generateFood() {
		while(true) {
			int position = random.nextInt(rows*columns);
			int x = position % rows;
			int y = position / columns;
			if(cells[x][y].getType() != CellType.SNAKE) {
				cells[x][y].setType(CellType.FOOD);
				foodRow = x;
				foodColumn = y;
				return;
			}
		}
	}
	
	public void draw(Graphics2D g2) {
		g2.setColor(Color.RED);
		int tileSize = snakeGame.getGamePanel().getTileSize();
		g2.fillRect((foodColumn * tileSize), (rows * tileSize) - (foodRow * tileSize) - tileSize, tileSize, tileSize);
		
		/*
		g2.setColor(Color.BLUE);
		for(int i=0; i<rows; ++i) {
			for(int j=0; j<columns; ++j) {
				if(cells[i][j].getType() == CellType.SNAKE) g2.fillRect((j * tileSize), (rows * tileSize) - (i * tileSize) - tileSize, tileSize, tileSize);
			}
		} */
	}

	//Getters & Setters
	public CellType getCellType(int row, int column) {
		return cells[row][column].getType();
	}
	public void setCellType(int row, int column, CellType type) {
		cells[row][column].setType(type);
	}
	
	public int getColumns() { return columns; }
	public int getRows() { return rows; }

}
