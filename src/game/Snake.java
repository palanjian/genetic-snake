package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.LinkedList;

public class Snake {
	Board board;
	int row;
	int column;
	String trueDirection;
	private boolean collided;
	private int dotsEaten = 0;
	
	LinkedList<Tile> snakePieces;
	public Snake(Board board, int initRow, int initColumn) {
		this.board = board;
		this.row = initRow;
		this.column = initColumn;
		this.trueDirection = "RIGHT";
		snakePieces = new LinkedList<Tile>();
		snakePieces.addFirst(new Tile(initRow, initColumn, TileType.SNAKE));
		collided = false;
		board.generateFood();
	}

	public void update(char nextMove) {
		//change direction
		updateDirection(nextMove);
		updatePosition();
		//remove from end
		snakePieces.removeLast();
		//add to beginning
		snakePieces.addFirst(new Tile(row, column, TileType.SNAKE));
		checkFood();
	}
	
	

	private void checkFood() {
		if(board.getCellType(row, column) == TileType.FOOD) {
			board.setCellType(row, column, TileType.SNAKE);
			++dotsEaten;
			growTail();
			
			board.generateFood();
		}
	}

	public void growTail() {
		snakePieces.add(new Tile(row, column, TileType.SNAKE));
	}
	
	public boolean checkCollision(int r, int c) {
		if(r >= board.getRows() || c >= board.getColumns() || r < 0 || c < 0) {
			collided = true;
			return true;
		}
		if(board.getCellType(r, c) == TileType.SNAKE) {
			collided = true;
			return true;
		}
		return false;
	}

	private void updatePosition() {
		int newRow = row;
		int newCol = column;
		if(trueDirection.equals("UP")) newRow +=1;
		if(trueDirection.equals("DOWN")) newRow -=1;
		if(trueDirection.equals("LEFT")) newCol -=1 ;
		if(trueDirection.equals("RIGHT")) newCol +=1;
		if(checkCollision(newRow, newCol)) return;
		
		board.setCellType(row, column, TileType.SNAKE);
		row = newRow;
		column = newCol;
	}
	

	public void updateDirection(char nextMove) {
		if(nextMove == 'S') return;
		else if(nextMove == 'L') {
			if(trueDirection.equals("UP")) trueDirection = "LEFT";
			else if(trueDirection.equals("DOWN")) trueDirection = "RIGHT";
			else if(trueDirection.equals("LEFT")) trueDirection = "DOWN";
			else if(trueDirection.equals("RIGHT")) trueDirection = "UP";
		}
		else if(nextMove == 'R'){
			if(trueDirection.equals("UP")) trueDirection = "RIGHT";
			else if(trueDirection.equals("DOWN")) trueDirection = "LEFT";
			else if(trueDirection.equals("LEFT")) trueDirection = "UP";
			else if(trueDirection.equals("RIGHT")) trueDirection = "DOWN";
		}
	}

	public boolean isCollided() {
		return collided;
	}

	public int getDotsEaten() {
		return dotsEaten;
	}


}
