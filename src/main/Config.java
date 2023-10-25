package main;

import snake.Direction;

public class Config {
	//number of rows for the snake game cell grid
	public static final int rows = 10;
	
	//number of columns for the snake game cell grid
	public static final int columns = 10;
	
	//seed for random number generation of food cells
	public static final int simulationSeed = 1;
	
	//amount of chromosomes per generation
	public static final int generationSize = 1000;
	
	//length of individual chromosome strings (how many genes)
	public static final int chromosomeLength = 250;
	
	//the top x chromosomes of the previous generation should be placed in the new
	//generation (so each generation never gets "worse". 
	public static final int elitists = 100;

	//direction snake should initially face (left, right, up, down)
	public static final Direction initialDirection = Direction.RIGHT;

	//row snake should be initialized in
	public static int initialRow = 5;

	//column snake should be initialized in
	public static int initialColumn = 5;

	//every gene in the chromosome has an X% chance to mutate on each recombination
	public static double mutationRate = 2;

	//current options: "ROULETTE"
	public static String selectionAlgorithm = "ROULETTE";

	//current options: "SINGLE_POINT"
	public static String recombinationAlgorithm = "SINGLE_POINT";

	//how many points should evaluation function reward/deduct for every piece of food eaten
	public static int foodEatenScore = 500;

	//how many points should evaluation function reward/deduct for every step taken
	public static int stepsTakenScore = -10;
}
