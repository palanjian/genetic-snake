package simulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Simulation {
	private boolean continueSimulation; 
	private int generation;
	private ArrayList<SnakeGame> genes;
	
	public Simulation() {
		continueSimulation = true;
		generation = 0;
		genes = GeneticString.initialize();
	}
	
	public void simulate(){
		//for every gene, run the game and calculate its value using value function
		//get the top x% of values (using roulette wheel) and some elitisim, add to list
		//cross breed the two values to get new list, add possibility of mutation
		//rinse and repeat
		while(continueSimulation) {
			getEvaluations();
			selectFittest();
			//recombination();
			//mutation();
			//elitism();
			++generation;
			promptUser();
		}
	}

	public void getEvaluations() {
		//plays the game for all strings and calculates their evaluations 
		for(SnakeGame sg : genes) {
			sg.play();
		}
		Collections.sort(genes);
		System.out.println("Greatest evaluation for gen " + generation + ": " + genes.get(0).getEvaluation());
	}
	
	public void selectFittest() {
		//roulette wheel algorithm
		
	}
	
	public void promptUser() {
		System.out.println("Would you like to simulate a further generation? Y/N");
		Scanner scan = new Scanner(System.in);
		if(scan.next().equals("Y")) continueSimulation = true;
		else if(scan.next().equals("N")) continueSimulation = false;
	}
}
