package simulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

import main.Config;

public class Simulation {
	private boolean continueSimulation = true; 
	private int generation = 0;
	private ArrayList<SnakeGame> chromosomes = ChromosomeGenerator.initialize();
	Random rand = new Random();
	Scanner scan = new Scanner(System.in);
	private int generationsToSimulate = 1;
		
	public void simulate(){
		while(continueSimulation) {
			getEvaluations();
			promptUser();	
			setupNextGeneration();
			++generation;
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////
	/*                       	  		Simulation Functions                                 */
	///////////////////////////////////////////////////////////////////////////////////////////	
	public void getEvaluations() {
		//plays the game for all strings and calculates their evaluations 
		for(SnakeGame sg : chromosomes) {
			sg.play();
		}
		Collections.sort(chromosomes);
		printGenerationStatistics();
	}
	
	public void setupNextGeneration() {
		ArrayList<SnakeGame> fittest = selectFittest(chromosomes);
		chromosomes = recombination(fittest);
	}
	
	public ArrayList<SnakeGame> selectFittest(ArrayList<SnakeGame> sg){
		ArrayList<SnakeGame> fittest = new ArrayList<>();
		ArrayList<SnakeGame> normalized = normalize(sg);
		int sumOfEvaluations = sum(normalized);
		
		for(int i=0; i<Config.generationSize; ++i) {
			SnakeGame selected = null;
			switch(Config.selectionAlgorithm) {
				case "ROULLETE":
					selected = selectUsingRoulette(normalized, sumOfEvaluations);
					break;
			}
			fittest.add(selected);
		}
		return fittest;
	}
	
	private ArrayList<SnakeGame> normalize(ArrayList<SnakeGame> sg) {
		ArrayList<SnakeGame> normalized = new ArrayList<>();
		//gets the value of the last value in the sorted list, which would be the lowest
		int lowestValue = sg.get(sg.size()-1).getEvaluation();
		if(lowestValue > 0) {
			return sg;
		}
		
		int discrepency = Math.abs(lowestValue);
		for(SnakeGame s : sg) {
			s.addToEvaluation(discrepency);
			normalized.add(s);
		}
		
		return normalized;
	}

	public SnakeGame selectUsingRoulette(ArrayList<SnakeGame> sg, int sumOfEvaluations) {
		int randomNumber = rand.nextInt(sumOfEvaluations);
		int sum = 0;
		for(SnakeGame s : sg) {
			sum += s.getEvaluation();
			if(sum >= randomNumber) {
				return s;
			}
		}
		return null;
	}
	
	private ArrayList<SnakeGame> recombination(ArrayList<SnakeGame> sg){
		ArrayList<SnakeGame> recombinated = new ArrayList<SnakeGame>();
		recombinated.addAll(getElitists());
		
		for(int i=0; i<sg.size()/2; ++i) {
			ArrayList<SnakeGame> pairOfRecombinations = null;
			switch(Config.recombinationAlgorithm) {
				case "SINGLE_POINT":
					pairOfRecombinations = singlePointCrossover(sg.get(i*2), sg.get(i*2 + 1));
					recombinated.addAll(pairOfRecombinations);
					break;
			}
		}		
		return recombinated;
	}
	
	public ArrayList<SnakeGame> singlePointCrossover(SnakeGame sg1, SnakeGame sg2){
		ArrayList<SnakeGame> pairOfRecombinations = new ArrayList<SnakeGame>();
		int splitPoint = rand.nextInt(Config.chromosomeLength);
		String chromosome1 = "";
		String chromosome2 = "";
		
		for(int i=0; i<Config.chromosomeLength; ++i) {
			char gene1 = sg1.getChromosome().charAt(i);
			char gene2 = sg2.getChromosome().charAt(i);
			//checks for mutation
			gene1 = mutate(gene1);
			gene2 = mutate(gene2);
			
			if(i < splitPoint) {
				chromosome1 += gene1;
				chromosome2 += gene2;
			}
			else {
				chromosome1 += gene2;
				chromosome2 += gene1;
			}
		}
		
		pairOfRecombinations.add(new SnakeGame(chromosome1));
		pairOfRecombinations.add(new SnakeGame(chromosome2));
		
		return pairOfRecombinations;
	}
	
	public char mutate(char gene) {
		char[] options = {'L', 'R', 'S'};
		if(rand.nextInt(100) < Config.mutationRate) {
			while(true) {
				int index = rand.nextInt(3);
				if(options[index] != gene) return options[index];		
			}			
		}
		return gene;
	}
	
	
	private ArrayList<SnakeGame> getElitists(){
		ArrayList<SnakeGame> elitists = new ArrayList<SnakeGame>();
		for(int i=0; i<Config.elitists; ++i) {
			elitists.add(chromosomes.get(i));
		}
		return elitists;
	}
	
	private int sum(ArrayList<SnakeGame> sg) {
		int sum = 0;
		for(SnakeGame s : sg) sum += s.getEvaluation();
		return sum;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////
	/*                       	  Visualization-Related Functions                            */
	///////////////////////////////////////////////////////////////////////////////////////////
	public void promptUser() {
		--generationsToSimulate;
		if(generationsToSimulate > 0) return;
		
		while(true) {
			System.out.println("Would you like to visualize this generation? Y/N");
			String input = scan.next().strip();
			if(input.toLowerCase().equals("y")) visualize(chromosomes.get(0).getChromosome());
			else if(input.toLowerCase().equals("n")) break;
		}
		
		System.out.println("How many generations would you like to simulate?");
		generationsToSimulate = scan.nextInt();
	}
	
	public void visualize(String chromosome) {
		SnakeGame sg = new SnakeGame(chromosome);
		sg.visualize();
	}
	
	private void printGenerationStatistics() {
		System.out.println("Greatest evaluation for gen " + generation + ": " + chromosomes.get(0).getEvaluation());
		System.out.println("Worst evaluation is " + chromosomes.get(chromosomes.size()-1).getEvaluation());
		System.out.println("Chromosome is " + chromosomes.get(0).getChromosome());
	}
}
