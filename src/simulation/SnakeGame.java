package simulation;

import game.Game;

public class SnakeGame implements Comparable<SnakeGame>{
	private String gene;
	private int evaluation;
	private double normalized;
	
	public SnakeGame(String gene) {
		this.gene = gene;
	}
	
	
	public void play() {
		//plays the game of snake and outputs the evaluation
		// PROOF OF CONCEPT: MOST FIT GENE HAS MOST 'S' chars
		
		Game game = new Game(gene);
		game.playSnake();
		
		int stepsTaken = game.getStepsTaken();
		int dotsEaten = game.snake.getDotsEaten();
		boolean collision = game.snake.isCollided();
		evaluation = evaluate(dotsEaten, stepsTaken, collision);
	}

	public int evaluate(int dots, int steps, boolean collision) {
		return (500*dots) - (5*steps);
	}


	@Override
	public int compareTo(SnakeGame o) {
		if(this.evaluation > o.getEvaluation()) return -1;
		else if(this.evaluation < o.getEvaluation()) return 1;
		else return 0;
	}
	
	public String getGene() { return gene; }
 	public int getEvaluation() { return evaluation; }
	public void addToEvaluation(int value) { evaluation += value; }
	public double getNormalized() { return normalized; }
	public void setNormalized(double value) { normalized = value; }
}
