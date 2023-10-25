package snake;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.LinkedList;

import main.Config;
import simulation.SnakeGame;

public class Snake {
	
	private Board board;
	private SnakeGame snakeGame;
	//row and column of snake's head
	private int row = Config.initialRow;
	private int column = Config.initialColumn;
	
	//up, down, left, or right
	private Direction trueDirection = Config.initialDirection;
	//each cell of the current snake
	private LinkedList<Cell> snakePieces = new LinkedList<>();
	
	private boolean hasCollided = false;
	private int dotsEaten = 0;
	
	public Snake(SnakeGame snakeGame, Board board) {
		this.snakeGame = snakeGame;
		this.board = board;
		
		snakePieces.addFirst(new Cell(row, column, CellType.SNAKE));
		board.generateFood();
	}

	public void update(char nextMove) {
		//change direction
		updateDirection(nextMove);
		updatePosition();
		//remove from end
		checkFood();
		board.setCellType(snakePieces.getLast().getRow(), snakePieces.getLast().getColumn(), CellType.EMPTY);
		snakePieces.removeLast();
		//add to beginning
		snakePieces.addFirst(new Cell(row, column, CellType.SNAKE));
		board.setCellType(row, column, CellType.SNAKE);

	}

	private void checkFood() {
		if(board.getCellType(row, column) == CellType.FOOD) {
			board.setCellType(row, column, CellType.SNAKE);
			snakeGame.incrementDotsEaten();
			growSnake();
			
			board.generateFood();
		}
	}

	private void growSnake() {
		snakePieces.add(new Cell(row, column, CellType.SNAKE));
	}
	
	public boolean checkCollision(int newRow, int newColumn) {
		if(newRow >= board.getRows() || newColumn >= board.getColumns()
		|| newRow < 0 || newColumn < 0) {
			hasCollided = true;
			return true;
		}
		if(board.getCellType(newRow, newColumn) == CellType.SNAKE) {
			hasCollided = true;
			return true;
		}
		return false;
	}

	private void updatePosition() {
		int newRow = row;
		int newColumn = column;
		
		switch(trueDirection) {
			case UP: newRow +=1; break;
			case DOWN: newRow -=1; break;
			case LEFT: newColumn -=1; break;
			case RIGHT: newColumn +=1; break;
		}

		if(checkCollision(newRow, newColumn)) return;

		board.setCellType(row, column, CellType.SNAKE);
		row = newRow;
		column = newColumn;
	}

	private void updateDirection(char nextMove) {
		if(nextMove == 'S') return;
		switch(trueDirection){
			case UP:
				if(nextMove == 'L') trueDirection = Direction.LEFT;
				else trueDirection = Direction.RIGHT;
				break;
			case DOWN:
				if(nextMove == 'L') trueDirection = Direction.RIGHT;
				else trueDirection = Direction.LEFT;
				break;
			case LEFT:
				if(nextMove == 'L') trueDirection = Direction.DOWN;
				else trueDirection = Direction.UP;
				break;
			case RIGHT:
				if(nextMove == 'L') trueDirection = Direction.UP;
				else trueDirection = Direction.DOWN;
				break;
		}
	}
	
	public void draw(Graphics2D g2) {
		g2.setColor(Color.GREEN);
		int tileSize = snakeGame.getGamePanel().getTileSize();
		for(Cell cell : snakePieces) {
			g2.fillRect((cell.getColumn() * tileSize), (board.getRows() * tileSize) - (cell.getRow() * tileSize) - tileSize, tileSize, tileSize);
		}
	}

	//Getters & Setters
	public boolean hasCollided() { return hasCollided; }
	public LinkedList<Cell> getSnakePieces() { return snakePieces; }

}