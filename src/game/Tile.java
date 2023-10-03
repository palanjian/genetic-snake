package game;

public class Tile {
	private int row;
	private int column;
	private TileType type;
	public Tile(int row, int column, TileType type) {
		this.setRow(row);
		this.setColumn(column);
		this.type = type;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	public TileType getType() {
		return type;
	}
	public void setType(TileType type) {
		this.type = type;
	}
}
