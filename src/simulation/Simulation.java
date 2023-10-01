package simulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

import main.Config;

public class Simulation {
	private boolean continueSimulation; 
	private int generation;
	private ArrayList<SnakeGame> genes;
	Random rand;

	public Simulation() {
		continueSimulation = true;
		generation = 0;
		genes = GeneticString.initialize();
		rand  = new Random(System.currentTimeMillis());
	}
	
	public void simulate(){
		//for every gene, run the game and calculate its value using value function
		//get the top x% of values (using roulette wheel) and some elitisim, add to list
		//cross breed the two values to get new list, add possibility of mutation
		//rinse and repeat
		while(continueSimulation) {
			playGames();
			++generation;
			promptUser();
			
			setupNextGeneration();
		}
	}
	
	public void playGames() {
		//plays the game for all strings and calculates their evaluations 
		for(SnakeGame sg : genes) {
			sg.play();
		}
		Collections.sort(genes);
		System.out.println("Greatest evaluation for gen " + generation + ": " + genes.get(0).getEvaluation());
	}
	
	public void setupNextGeneration() {
		ArrayList<SnakeGame> fittest = selectFittest(genes);
		ArrayList<SnakeGame> newGeneration = recombination(fittest); //recombination w elitism
		//mutation();
		
		genes = newGeneration;
	}

	
	public ArrayList<SnakeGame> selectFittest(ArrayList<SnakeGame> sg){
		//gets the value of the last value in the sorted list, which would be the lowest
		int lowestValue = sg.get(sg.size()-1).getEvaluation();
		System.out.println("Lowest value= " + lowestValue);

		ArrayList<SnakeGame> normalized;
		if(lowestValue < 0) {
			normalized = new ArrayList<SnakeGame>();
			int discrepency = Math.abs(lowestValue);
			for(SnakeGame s : sg) {
				s.addToEvaluation(discrepency);
				normalized.add(s);
			}
		}
		else normalized = sg;
		
		int sumOfEvaluations = sum(normalized);
		
		ArrayList<SnakeGame> finalList = new ArrayList<>();
		for(int i=0; i<Config.numGenes/2; ++i) {
			SnakeGame selected = selectUsingRoulette(normalized, sumOfEvaluations);
			finalList.add(selected);
//			/System.out.println("Selected " + selected.getGene() + " which has eval: " + selected.getEvaluation());
		}
		return finalList;
	}
	
	public SnakeGame selectUsingRoulette(ArrayList<SnakeGame> sg, int sumOfEvaluations) {
		int random = rand.nextInt(sumOfEvaluations);
		int sum = 0;
		for(SnakeGame s : sg) {
			sum += s.getEvaluation();
			if(sum >= random) {
				return s;
			}
		}
		return null;
	}
	
	private ArrayList<SnakeGame> recombination(ArrayList<SnakeGame> sg){
		ArrayList<SnakeGame> finalList = new ArrayList<SnakeGame>();
		for(int i=0; i<sg.size()/2; ++i) {
			ArrayList<SnakeGame> recombinations = singlePointCrossover(sg.get(i*2), sg.get(i*2 + 1));
			finalList.addAll(recombinations);
		}
		//elitism, adds the best from the previous generation so it never gets worse
		Collections.sort(sg);
		finalList.set(finalList.size()-1, sg.get(0));
		
		return finalList;
	}
	
	public ArrayList<SnakeGame> singlePointCrossover(SnakeGame sg1, SnakeGame sg2){
		int splitPoint = rand.nextInt(Config.geneLength);
		String newGene1 = "";
		String newGene2 = "";
		for(int i=0; i<Config.geneLength; ++i) {
			if(i < splitPoint) {
				newGene1 += sg1.getGene().charAt(i);
				newGene2 += sg2.getGene().charAt(i);
			}
			else {
				newGene1 += sg2.getGene().charAt(i);
				newGene2 += sg1.getGene().charAt(i);
			}
		}
		ArrayList<SnakeGame> finalList = new ArrayList<SnakeGame>();
		finalList.add(new SnakeGame(newGene1));
		finalList.add(new SnakeGame(newGene2));
		return finalList;
	}
	
	
	private int sum(ArrayList<SnakeGame> sg) {
		int sum = 0;
		for(SnakeGame s : sg) {
			sum += s.getEvaluation();
		}
		return sum;
	}

	public void promptUser() {
		System.out.println("Would you like to simulate a further generation? Y/N");
		Scanner scan = new Scanner(System.in);
		if(scan.next().equals("Y")) continueSimulation = true;
		else if(scan.next().equals("N")) continueSimulation = false;
	}
}
