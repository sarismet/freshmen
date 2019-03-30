package project;

import java.awt.Color;
import java.util.List;

import game.Direction;
import game.Drawable;
import naturesimulator.Action;

import naturesimulator.LocalInformation;
import ui.GridPanel;
/** 
 * @author �BABE
 */
/* 
 * Comment that has green color is written on purpose in the case of the javadoc does not work
 * @author �BABE
 */
public class Plant extends Creature implements Drawable {
	
	private static final double MAX_HEALTH2 = 1.0;
/**
 * The first constructor of this plant for the beginning of the game 
 * @param x location of plant
 * @param y location of plant
 */
	/*
	 * The first constructor of this plant for the beginning of the game 
	 * @param x location of plant
	 * @param y location of plant
	 */
	public Plant(int x, int y) {
		super(x, y, MAX_HEALTH2 / 2);
	}
/** 
 * The second constructor of the plant that is produced later on
 * @param x location of plant
 * @param y location of plant
 * @param health of plant
 */
	/* 
	 * The second constructor of the plant that is produced later on
	 * @param x location of plant
	 * @param y location of plant
	 * @param healthof plant
	 */
	public Plant(int x, int y, double health) {
		super(x, y, health);
	}
	/**
	 * Shows the plant on the map
	 *@param panel
	 */
	/*
	 * Shows the plant on the map
	 *@param panel
	 */

	public void draw(GridPanel panel) {

		if (super.getHealth() > 0.750) {
			panel.drawSquare(getX(), getY(), new Color(0, 120, 0));

		} else if (super.getHealth() > 0.500) {
			panel.drawSquare(getX(), getY(), new Color(0, 204, 0));
		} else {
			panel.drawSquare(getX(), getY(), new Color(102, 255, 102));
		}
	}
	/**
	 * Produces one more plant to direction which is selected
	 * @param direction to move
	 */
	/*
	 * Produces one more plant to direction which is selected
	 * @param direction to move
	 */


	public Creature reproduce(Direction direction) {

		if (direction == Direction.DOWN) {

			Plant creature = new Plant(super.getX(), (super.getY() + 1), super.getHealth() / 10);
			super.setHealth((double) (super.getHealth() - super.getHealth() * 30 / 100));
			return creature;
		}
		if (direction == Direction.UP) {
			Plant creature = new Plant(super.getX(), (super.getY() - 1), super.getHealth() / 10);
			super.setHealth((double) (super.getHealth() - super.getHealth() * 30 / 100));
			return creature;
		}
		if (direction == Direction.LEFT) {
			Plant creature = new Plant((super.getX() - 1), super.getY(), super.getHealth() / 10);
			super.setHealth((double) (super.getHealth() - super.getHealth() * 30 / 100));
			return creature;

		}
		if (direction == Direction.RIGHT) {
			
				Plant creature = new Plant((super.getX() + 1), super.getY(), super.getHealth() / 10);
				super.setHealth((double) (super.getHealth() - super.getHealth() * 30 / 100));
				return creature;
			
		}
		return null;

	}

	public Action chooseAction(LocalInformation information) {

		List<Direction> freeDirections = information.getFreeDirections();
		Direction direct = LocalInformation.getRandomDirection(freeDirections);

		if (super.getHealth() >= 0.750 && freeDirections.size() != 0) {

			return new Action(Action.Type.REPRODUCE, direct);

		} else {
			return new Action(Action.Type.STAY);
		}
	}

	public void stay() {
		super.setHealth((double) (super.getHealth() + 0.05));
		if (super.getHealth() > MAX_HEALTH2) {
			super.setHealth(MAX_HEALTH2);

		}

	}
}
