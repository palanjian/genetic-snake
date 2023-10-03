package game;

public class Game {
	
	boolean gameOver = false;
	String chromosome;
	int rows = 10;
	int columns = 10;
	Board board = new Board(rows, columns);
	
	private int stepsTaken;
	
	public Snake snake = new Snake(board, 5, 5);
	public Game(String chromosome) {
		this.chromosome = chromosome;
	}
	
	public void playSnake() {
		for(stepsTaken=0; stepsTaken<chromosome.length(); ++stepsTaken){
			char nextMove = chromosome.charAt(stepsTaken);
			snake.update(nextMove);
			if(checkLoseCondition() == true) {
				return;
			}
			else if(checkWinCondition() == true) {
				System.out.println("Snake has won the game");
				return;
			}
		}
	}
	public boolean checkLoseCondition() {
		if(snake.isCollided()) return true;
		return false;
	}
	public boolean checkWinCondition() {
		if(snake.snakePieces.size() == rows*columns - 1) return true;
		return false;
	}
	
	public int getStepsTaken() {
		return stepsTaken;
	}
}
