package simulation;

import snake.Board;
import snake.Snake;
import main.Config;
import main.GamePanel;
import main.Main;

public class SnakeGame implements Comparable<SnakeGame>{
	private String chromosome;
	private int evaluation;
	private double normalized;
	
	private int stepsTaken = 0;
	private int dotsEaten = 0;
	
	private Board board = new Board(this, Config.rows, Config.columns);
	private Snake snake = new Snake(this, board);
	
	private GamePanel gp;
	
	public SnakeGame(String chromosome) {
		this.chromosome = chromosome;
		
	}
	
	public void play() {
		for(int i=0; i<chromosome.length(); ++i){
			char nextMove = chromosome.charAt(i);
			snake.update(nextMove);
			if(checkLoseCondition()) {
				break;
			}
			else if(checkWinCondition()) {
				System.out.println("Snake has won the game");
				break;
			}
			++stepsTaken;
		}
		evaluation = evaluate(dotsEaten, stepsTaken);
	}
	
	public int evaluate(int dots, int steps) {
	    return (500 * dots) - (10 * steps);
	}

	@Override
	public int compareTo(SnakeGame o) {
		if(this.evaluation > o.getEvaluation()) return -1;
		else if(this.evaluation < o.getEvaluation()) return 1;
		else return 0;
	}
	
	public boolean checkLoseCondition() {
		if(snake.hasCollided()) return true;
		return false;
	}
	
	public boolean checkWinCondition() {
		if(snake.getSnakePieces().size() == Config.rows * Config.columns - 1) return true;
		return false;
	}
	
	//visualization-centered functions
	public void visualize() {
		gp = new GamePanel(this);
		Main.window.add(gp);
		Main.window.pack();
		Main.window.setVisible(true);
		Main.window.setLocationRelativeTo(null);

		int i = 0;
		double drawInterval = 1000000000/6;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
	
		while(i < chromosome.length()) {
			//game loop
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			lastTime = currentTime;
			if(delta >=1) {
				char nextMove = chromosome.charAt(i);
				snake.update(nextMove);
				++stepsTaken;
				--delta;
				++i;
				
				if(checkLoseCondition() || checkWinCondition()) break;
				gp.repaint(); //calls paintComponent		
			}
		}
	}
	
	public GamePanel getGamePanel() { return gp; }
	public String getChromosome() { return chromosome; }
	public int getEvaluation() { return evaluation; }
	public void addToEvaluation(int value) { evaluation += value; }
	public double getNormalized() { return normalized; }
	public void setNormalized(double value) { normalized = value; }
	public int getStepsTaken() { return stepsTaken; }
	public void incrementDotsEaten() { dotsEaten +=1; }
	public Snake getSnake() { return snake; }
	public Board getBoard() { return board; }

}
