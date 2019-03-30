package game;

import ui.GridPanel;

import javax.swing.*;
import java.util.HashSet;
import java.util.Set;


public abstract class GridGame {

	private  GridPanel gamePanel;
	private Timer gameTimer;

	private static Set<Drawable> drawables;


	public GridGame(int gridWidth, int gridHeight, int gridSquareSize, int frameRate) {
		gamePanel = new GridPanel(gridWidth, gridHeight, gridSquareSize);
		gameTimer = new Timer(1000 / frameRate, event -> {
			timerTick();
			redraw();
		});
		drawables = new HashSet<>();
	}

	private void redraw() {
		gamePanel.clearCanvas();
		gamePanel.drawGrid();

		for (Drawable drawable : drawables) {
			drawable.draw(gamePanel);
		}

		gamePanel.repaint();
	}


	public static void addDrawable(Drawable drawable) {
		drawables.add(drawable);
	}

	public static void removeDrawable(Drawable drawable) {
		drawables.remove(drawable);
	}


	public GridPanel getGamePanel() {
		return gamePanel;
	}


	public void start() {
		gameTimer.setInitialDelay(0);
		gameTimer.start();
	}

	public void stop() {
		gameTimer.stop();
	}


	public  int getGridWidth() {
		return gamePanel.getGridWidth();
	}

	
	public int getGridHeight() {
		return gamePanel.getGridHeight();
	}

	protected abstract void timerTick();

}
