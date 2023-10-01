package simulation;

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
		
		int numS = 0;
		int numL = 0;
		
		for(int i=0; i<gene.length(); ++i) {
			if(gene.charAt(i) == 'S') { numS++; }
			if(gene.charAt(i) == 'L') { numL++; }
		}
		
		evaluation = evaluate(numS, numL);
	}
	
	public int evaluate(int numS, int numL) {
		return (10*numS) - (5*numL);
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
