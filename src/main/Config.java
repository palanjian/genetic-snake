package main;

import snake.Direction;

public class Config {
	//number of rows for the snake game cell grid
	public static int rows = 10;
	
	//number of columns for the snake game cell grid
	public static int columns = 10;
	
	//seed for random number generation of food cells
	public static int simulationSeed = 1;
	
	//amount of chromosomes per generation
	public static int generationSize = 5000;
	
	//length of individual chromosome strings (how many genes)
	public static int chromosomeLength = 500;
	
	//the top x chromosomes of the previous generation should be placed in the new
	//generation (so each generation never gets "worse".)
	public static int elitists = 100;

	//direction snake should initially face (left, right, up, down)
	public static Direction initialDirection = Direction.RIGHT;

	//row snake should be initialized in
	public static int initialRow = 5;

	//column snake should be initialized in
	public static int initialColumn = 5;

	//every gene in the chromosome has an X% chance to mutate on each recombination
	public static int mutationRate = 2;

	//current options: "ROULETTE"
	public static String selectionAlgorithm = "ROULETTE";

	//current options: "SINGLE_POINT"
	public static String recombinationAlgorithm = "SINGLE_POINT";

	//how many points should evaluation function reward/deduct for every piece of food eaten
	public static int foodEatenScore = 500;

	//how many points should evaluation function reward/deduct for every step taken
	public static int stepsTakenScore = -10;

}
