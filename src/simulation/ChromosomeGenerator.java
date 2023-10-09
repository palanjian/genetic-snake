package simulation;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import main.Config;

public class ChromosomeGenerator {
	
	public static ArrayList<SnakeGame> initialize() {
		ArrayList<SnakeGame> chromosomes = new ArrayList<>();
		for(int i = 0; i< Config.generationSize; ++i) {
			chromosomes.add(new SnakeGame(generateRandomString()));
		}
		return chromosomes;
	}
	
	public static String generateRandomString() {
		char[] options = {'L', 'R', 'S'};
		StringBuilder string = new StringBuilder();
		
		for(int i=0; i<Config.chromosomeLength; ++i) {
			string.append(options[ThreadLocalRandom.current().nextInt(3)]);
		}
		return string.toString();
	}
}	
