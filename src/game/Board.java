package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

public class Board {
	private int rows;
	private int columns;
	Tile[][] tiles;
	Random rand;

	public Board(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		this.tiles = new Tile[rows][columns];
		for(int i=0; i<rows; ++i) {
			for(int j=0; j<columns; ++j) {
				this.tiles[i][j] = new Tile(i, j, TileType.EMPTY);
			}
		}
		rand = new Random(1);
	}
	
	public TileType getCellType(int row, int column) {
		return tiles[row][column].getType();
	}
	public void setCellType(int row, int column, TileType type) {
		tiles[row][column].setType(type);
	}
	public int getColumns() {
		return columns;
	}
	
	public int getRows() {
		return rows;
	}

	public void generateFood() {
		while(true) {
			int position = rand.nextInt(rows*columns);
			int xPos = position % rows;
			int yPos = position / columns;
			if(tiles[xPos][yPos].getType() != TileType.SNAKE) {
				tiles[xPos][yPos].setType(TileType.FOOD);
				break;
			}
		}
	}
}
