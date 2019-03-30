package project;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import game.Direction;
import game.Drawable;
import naturesimulator.Action;

import naturesimulator.LocalInformation;
import ui.GridPanel;
/**
 * @author ÝBABE
 */
/*
 *  Comment that has green color is written on purpose in the case of the javadoc does not work
 * @author ÝBABE
 */
public class Herbivore extends Creature implements Drawable {
	private static final double MAX_HEALTH1 = 20.0;
/**
 * @param x location of the herbivore
 * @param y location of the herbivore
 * Herbivore constructor to set Herbivore's locations and health for the beginning of the game
 */
	/*
	 * @param x location of the herbivore
	 * @param y location of the herbivore
	 * Herbivore constructor to set Herbivore's locations and health for the beginning of the game
	 */
	public Herbivore(int x, int y) {
		super(x, y, MAX_HEALTH1 / (double) 2);
	}
/**
 * The second constructor to set Herbivore's  locations and health for the Herbivore that is reproduced
 * @param x location of the herbivore
 * @param y location of the herbivore
 * @param health of the herbivore
 */
	/*
	 * The second constructor to set Herbivore's  locations and health for the Herbivore that is reproduced
	 * @param x location of the herbivore
	 * @param y location of the herbivore
	 * @param health of the herbivore
	 */
	public Herbivore(int x, int y, double health) {
		super(x, y, health);

	}
/**
 * Takes the direction as a parameter and return creature that is produced
 * @param direction that is chosen to produce 
 */
	/*
	 * Takes the direction as a parameter and return creature that is produced
	 * @param direction direction that is chosen to produce 
	 */
	public Creature reproduce(Direction direction) {

		if (direction == Direction.DOWN) {

			Herbivore creature = new Herbivore(super.getX(), (super.getY() + 1), MAX_HEALTH1 * 2 / 10);
			super.setHealth(MAX_HEALTH1 * 4 / 10);

			return creature;
		} else if (direction == Direction.UP) {
			Herbivore creature = new Herbivore(super.getX(), (super.getY() - 1), MAX_HEALTH1 * 2 / 10);
			super.setHealth(MAX_HEALTH1 * 4 / 10);
			return creature;
		} else if (direction == Direction.LEFT) {
			Herbivore creature = new Herbivore((super.getX() - 1), super.getY(), MAX_HEALTH1 * 2 / 10);
			super.setHealth(MAX_HEALTH1 * 4 / 10);
			return creature;

		} else {

			Herbivore creature = new Herbivore((super.getX() + 1), super.getY(), MAX_HEALTH1 * 2 / 10);
			super.setHealth(MAX_HEALTH1 * 4 / 10);
			return creature;

		}

	}

	/**
	 * Draws the creature on the map according to their health level
	 * @param panel 
	 * 
	 */
	/*
	 * Draws the creature on the map according to their health level
	 * @param panel
	 * 
	 */
	
	public void draw(GridPanel panel) {

		if (super.getHealth() > 15.0) {
			panel.drawSquare(getX(), getY(), new Color(153, 0, 0));// Very dark red
		} else if (super.getHealth() > 10.0) {
			panel.drawSquare(getX(), getY(), new Color(204, 0, 0));// Dark red
		} else if (super.getHealth() > 5.0) {
			panel.drawSquare(getX(), getY(), new Color(255, 0, 0));// red
		} else {
			panel.drawSquare(getX(), getY(), Color.ORANGE);
		}
	}

	@Override
/**
 * Decreases the health of the herbivore;
 */
	/*
	 * Decreases the health of the herbivore;
	 */
	public void stay() {
		super.setHealth((double) (super.getHealth() - 0.1));

	}

	@Override
	/**
	 * To determine what the Herbivore would do according to conditions over its health , locations,and the free directions around the herbivore 
	 * @param localinformation that this herbivore has 
	 */
	/*
	 * To determine what the Herbivore would do according to conditions over its health , locations,and the free directions around the herbivore 
	 * @param localinformation that this herbivore has 
	 */
	public Action chooseAction(LocalInformation information) {

		List<Direction> freeDirections = information.getFreeDirections();
		Direction direct = LocalInformation.getRandomDirection(freeDirections);
	

		ArrayList<Direction> PossibleDirections = new ArrayList<>();

		if (super.getHealth() == MAX_HEALTH1 && freeDirections.size() != 0)

		{

			return new Action(Action.Type.REPRODUCE, direct);

		}

		else if ((information.getCreatureDown() instanceof Plant) || (information.getCreatureUp() instanceof Plant)
				|| (information.getCreatureRight() instanceof Plant)
				|| (information.getCreatureLeft() instanceof Plant)) {


			if (information.getCreatureDown() instanceof Plant) {
				PossibleDirections.add(Direction.DOWN);

			}
			if (information.getCreatureUp() instanceof Plant) {
				PossibleDirections.add(Direction.UP);

			}
			if (information.getCreatureLeft() instanceof Plant) {
				PossibleDirections.add(Direction.LEFT);

			}
			if (information.getCreatureRight() instanceof Plant) {
				PossibleDirections.add(Direction.RIGHT);

			}

			int randomIndex = (int) (Math.random() * PossibleDirections.size());

			return new Action(Action.Type.ATTACK, PossibleDirections.get(randomIndex));

		}

		else if ((super.getHealth() > 1.0) && (freeDirections.size() != 0)) {



			return new Action(Action.Type.MOVE, direct);
		}

		else {

			return super.chooseAction();
		}

	}
	/**
	 * Attacks a plant around it and takes its health for itsself
	 * @param attackedCreature to attack
	 */
	/*
	 * Attacks a plant around it and takes its health for itsself
	 * @param attackedCreature to attack
	 */

	public void attack(Creature attackedCreature) {

		if (attackedCreature instanceof Plant) {

			super.setHealth(attackedCreature.getHealth() + super.getHealth());
			attackedCreature.setHealth(0.0);
			if (super.getHealth() > MAX_HEALTH1) {
				super.setHealth(MAX_HEALTH1);

			}
			super.setX(attackedCreature.getX());
			super.setY(attackedCreature.getY());

		} else if (attackedCreature instanceof Herbivore) {


		}

	}
/**
 * Moves to free direction that this herbivore has
 * @param direction to move
 */
	/*
	 * Moves to free direction that this herbivore has
	 * @param direction to move
	 */
	public void move(Direction direction) {
		if (direction == Direction.DOWN) {
			this.setY(super.getY() + 1);
			super.setHealth(super.getHealth() - 1.0);
		}
		if (direction == Direction.UP) {
			this.setY(super.getY() - 1);
			super.setHealth(super.getHealth() - 1.0);
		}
		if (direction == Direction.LEFT) {
			this.setX(super.getX() - 1);
			super.setHealth(super.getHealth() - 1.0);
		}
		if (direction == Direction.RIGHT) {
			this.setX(super.getX() + 1);
			super.setHealth(super.getHealth() - 1.0);
		}

	}

}
