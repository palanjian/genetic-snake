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
	public static final int elitists = 30;
	
	public static final Direction initialDirection = Direction.RIGHT;

	public static int initialRow = 5;
	
	public static int initialColumn = 5;

	public static double mutationRate = 2;

	public static String selectionAlgorithm = "ROULETTE";

	public static String recombinationAlgorithm = "SINGLE_POINT";
}
