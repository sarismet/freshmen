package main;

import java.awt.Color;
import java.awt.EventQueue;

import naturesimulator.NatureSimulator;
import project.*;

import ui.ApplicationWindow;

/**
 * "GULRU" is proud to present this project
 * The main class of the project.
 * 
 */
public class Main {

    /**
     * Main entry point for the application.
     *
     * @param args application arguments
     */
    public static void main(String[] args) {

		EventQueue.invokeLater(() -> {
			try {
				// Create game snake
				// You can change the world width and height, size of each grid square in pixels 
				// the game speed or max number of snake
				NatureSimulator game = new NatureSimulator(70 ,38, 25,3,150);
				int x = (int) (Math.random() * (game.getGridWidth()-3)+3);
				int y = (int) (Math.random() * game.getGridHeight());
			
			// Creating the first Nodes for the first snake for the beginning of the game
			
				Node head= new Node(x,y,Color.RED,null,null);
				Node node2= new Node(x-1,y,Color.BLUE,head,null);
				Node node3= new Node(x-2,y,Color.BLUE,node2,null);
				Node node4= new Node(x-3,y,Color.BLUE,node3,null);
				head.setPrevious(node2);
				node2.setPrevious(node3);
				node3.setPrevious(node4);
				
			
				// Adds them to first snake arraylist
					game.addCreature(head,game.creatures);
					game.addCreature(node2,game.creatures);
					game.addCreature(node3,game.creatures);
					game.addCreature(node4,game.creatures);
			
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
