package main;

import javax.swing.JFrame;

import simulation.Simulation;

public class Main {
	static JFrame window;
	static GamePanel gp;
	
	public static void main(String[] args) {
		new Main();
	}
	
	public Main() {
		setupVisuals();
		setupSimulation();
	}
	
	public void setupVisuals(){
		window = new JFrame();
		gp = new GamePanel();
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("Java2DMMO");
		window.add(gp);
		window.pack();
		window.setLocationRelativeTo(null);
		//window.setVisible(true);
	}
	
	public void setupSimulation(){
		Simulation sim = new Simulation();
		sim.simulate();
	}
}
