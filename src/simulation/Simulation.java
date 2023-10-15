package simulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

import main.Config;

public class Simulation {
	private boolean continueSimulation; 
	private int generation;
	private ArrayList<SnakeGame> chromosomes;
	Random rand;

	private int generationsToSimulate = 1;
	
	public Simulation() {
		continueSimulation = true;
		generation = 0;
		chromosomes = ChromosomeGenerator.initialize();
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
			promptVisualization();
			promptUser();
			
			setupNextGeneration();
		}
	}
	
	public void playGames() {
		//plays the game for all strings and calculates their evaluations 
		for(SnakeGame sg : chromosomes) {
			sg.play();
		}
		Collections.sort(chromosomes);
		System.out.println("Greatest evaluation for gen " + generation + ": " + chromosomes.get(0).getEvaluation());
		System.out.println("Chromosome is " + chromosomes.get(0).getChromosome());		
	}
	
	public void promptVisualization() { 
		if(generationsToSimulate > 1) return;
		while(true) {
			System.out.println("Would you like to visualize this generation? Y/N");
			Scanner scan = new Scanner(System.in);
			String input = scan.next().strip();
			if(input.toLowerCase().equals("y")) visualize(chromosomes.get(0).getChromosome());
			else if(input.toLowerCase().equals("n")) break;
		}
	}
	
	public void visualize(String chromosome) {
		SnakeGame sg = new SnakeGame(chromosome);
		sg.visualize();
	}
	
	public void setupNextGeneration() {
		ArrayList<SnakeGame> fittest = selectFittest(chromosomes);
		ArrayList<SnakeGame> newGeneration = recombination(fittest); //recombination w elitism
		newGeneration = mutation(newGeneration);
		newGeneration.addAll(getElitists(Config.elitists));
		chromosomes = newGeneration;
	}

	
	private ArrayList<SnakeGame> getElitists(int elitists) {
		ArrayList<SnakeGame> finalList = new ArrayList<SnakeGame>();
		for(int i=0; i<elitists; ++i) {
			finalList.add(chromosomes.get(i));
		}
		return finalList;
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
		for(int i=0; i<Config.generationSize/2; ++i) {
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
		//implement elitism
		for(int i=0; i<Config.elitists; ++i) {
			finalList.add(chromosomes.get(i));
		}
		
		return finalList;
	}
	
	public ArrayList<SnakeGame> singlePointCrossover(SnakeGame sg1, SnakeGame sg2){
		int splitPoint = rand.nextInt(Config.chromosomeLength);
		String newGene1 = "";
		String newGene2 = "";
		for(int i=0; i<Config.chromosomeLength; ++i) {
			if(i < splitPoint) {
				newGene1 += sg1.getChromosome().charAt(i);
				newGene2 += sg2.getChromosome().charAt(i);
			}
			else {
				newGene1 += sg2.getChromosome().charAt(i);
				newGene2 += sg1.getChromosome().charAt(i);
			}
		}
		ArrayList<SnakeGame> finalList = new ArrayList<SnakeGame>();
		finalList.add(new SnakeGame(newGene1));
		finalList.add(new SnakeGame(newGene2));
		return finalList;
	}
	
	public ArrayList<SnakeGame> mutation(ArrayList<SnakeGame> sg){
		ArrayList<SnakeGame> finalList = new ArrayList<SnakeGame>();

		for(SnakeGame s : sg) {
			SnakeGame newGame = new SnakeGame(mutate(s.getChromosome()));
			finalList.add(newGame);
		}
		return finalList;
	}
	
	public String mutate(String chromosome) {
		String finalString = "";
		char[] options = {'L', 'R', 'S'};
		for(int i=0; i<chromosome.length(); ++i) {
			if(rand.nextInt(100) < Config.mutationRate) {
				while(true) {
					int index = rand.nextInt(3);
					if(options[index] != chromosome.charAt(i)) {
						finalString += options[index];
						break;
					}					
				}
			}
			else finalString += chromosome.charAt(i);
		}
		return finalString;
	}
	
	private int sum(ArrayList<SnakeGame> sg) {
		int sum = 0;
		for(SnakeGame s : sg) {
			sum += s.getEvaluation();
		}
		return sum;
	}

	public void promptUser() {
		--generationsToSimulate;
		if(generationsToSimulate > 0) return;
		System.out.println("How many generations would you like to simulate?");
		Scanner scan = new Scanner(System.in);
		generationsToSimulate = scan.nextInt();
	}
}
