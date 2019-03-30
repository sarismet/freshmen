package main;

import naturesimulator.NatureSimulator;
import project.Herbivore;
import project.Plant;
import ui.ApplicationWindow;

import java.awt.*;


public class Main {


	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
			
				NatureSimulator game = new NatureSimulator(50, 50, 19, 10);

				// Create and add plants
				for (int i = 0; i < 200; i++) {
					int x = (int) (Math.random() * game.getGridWidth());
					int y = (int) (Math.random() * game.getGridHeight());
					game.addCreature(new Plant(x, y));
				}

				// Create and add herbivores
				for (int i = 0; i < 20; i++) {
					int x = (int) (Math.random() * game.getGridWidth());
					int y = (int) (Math.random() * game.getGridHeight());
					game.addCreature(new Herbivore(x, y));
				}

				// Create application window that contains the game panel
				ApplicationWindow window = new ApplicationWindow(game.getGamePanel());
				window.getFrame().setVisible(true);

				// Start game
				game.start();

			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

}
