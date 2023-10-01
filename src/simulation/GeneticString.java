package simulation;

import java.util.ArrayList;
import java.util.Random;

import main.Config;

public class GeneticString {
	static Random rand = new Random(System.currentTimeMillis());

	public static ArrayList<SnakeGame> initialize() {
		ArrayList<SnakeGame> list = new ArrayList<>();
		for(int i = 0; i< Config.numGenes; ++i) {
			list.add(new SnakeGame(generateRandomString()));
		}
		return list;
	}
	
	public static String generateRandomString() {
		char[] options = {'L', 'R', 'S'};
		String s = ""; 
		
		for(int i=0; i<Config.geneLength; ++i) {
			s += options[rand.nextInt(3)];
		}
		return s;
	}
}	
