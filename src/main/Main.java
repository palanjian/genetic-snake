package main;

import javax.swing.JFrame;

import simulation.Simulation;

public class Main {
	public static JFrame window;
	public GamePanel gp;
	
	public static void main(String[] args) {
		new Main();
	}
	
	public Main() {
		setupVisuals();
		setupSimulation();
	}
	
	public void setupVisuals(){
		window = new JFrame();
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("Java2DMMO");
	}
	
	public void setupSimulation(){
		Simulation simulation = new Simulation();
		simulation.simulate();
	}

}
