package snake;

public class Cell {
	private int row;
	private int column;
	private CellType type;
	
	public Cell(int row, int column, CellType type) {
		this.setRow(row);
		this.setColumn(column);
		this.type = type;
	}
	
	//Getters & Setters
	public int getRow() { return row; }
	public void setRow(int row) { this.row = row; }
	
	public int getColumn() { return column; }
	public void setColumn(int column) { this.column = column; }
	
	public CellType getType() { return type; }	
	public void setType(CellType type) { this.type = type; }
}
