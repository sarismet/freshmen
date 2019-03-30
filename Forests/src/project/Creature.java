package project;

import game.*;
import naturesimulator.Action;
import naturesimulator.LocalInformation;
import ui.GridPanel;
/** 
 * Creature class 
 * @author ÝBABE
 */
/*
 *  Comment that has green color is written on purpose in the case of the javadoc does not work
 * Creature class 
 * @author ÝBABE
 */
public abstract class Creature implements Drawable {

	private int x;
	private int y;
	protected double health;
/**
 * Sets the x location of this creature
 * @param x location of this creature
 */
	/*
	 * Sets the x location of this creature
	 * @param x location of this creature
	 */
	public void setX(int x) {
		this.x = x;
	}
/** 
 * Sets the y location of this creature
 * @param y location of this creature
 */
	/* 
	 * Sets the y location of this creature
	 * @param y
	 */
	public void setY(int y) {
		this.y = y;
	}
/** 
 * Sets the health of this Creature
 * @param health of this Creature
 */
	/* 
	 * Sets the health of this Creature
	 * @param health of this Creature
	 */
	public void setHealth(double health) {
		this.health = health;
	}
/** 
 * The constructor of creature to set the x and y locations and health of it
 * @param x location of this creature
 * @param y location of this creature
 * @param health of this creature
 */
	/* 
	 * The constructor of creature to set the x and y locations and health of it
	 * @param x location of this creature
	 * @param y location of this creature
	 * @param health of this creature
	 */
	public Creature(int x, int y, double health) {
		this.x = x;
		this.y = y;
		this.health = health;

	}
/** 
 * Chooses an action according to information that creature has 
 * @param information
 * @return
 */
	/* 
	 * Chooses an action according to information that creature has 
	 * @param information
	 * @return
	 */
	public Action chooseAction(LocalInformation information) {
		return null;
	}
		
/** 
 * A specific action for creature to just stay
 * @return
 */
	/* 
	 * A specific action for creature to just stay
	 * @return stays action
	 */
	public Action chooseAction() {
		return new Action(Action.Type.STAY);
	}
/** 
 * Return the health of creature
 * @return Health of this creature
 */
	/* 
	 * Return the health of creature
	 * @return Health of this creature
	 */
	public double getHealth() {

		return health;
	}
/**
 * @return the X location of creature
 */
	/*
	 * @return the X location of creature
	 */
	public int getX() {
		// TODO Auto-generated method stub
		return x;
	}
/**
 * @return the Y location of creature
 */
	/*
	 * @return the Y location of creature
	 */

	public int getY() {
		// TODO Auto-generated method stub
		return y;
	}
/** 
 * Produces one more creature to a free direction that this creature has
 * @param direction
 * @return
 */
	/* 
	 * Produces one more creature to a free direction that this creature has
	 * @param direction
	 * @return
	 */
	public Creature reproduce(Direction direction) {
		

		return null;
	}

 /**
  * Stays 
  */
	 /*
	  * Stays 
	  */
	public void stay() {

	}
/**
 * Attack a creature 
 * @param attackedCreature
 */
	/*
	 * Attack a creature 
	 * @param attackedCreature
	 */
	public void attack(Creature attackedCreature) {
		// TODO Auto-generated method stub

	}
	/**
	 * Shows the creature on the map
	 * @param panel
	 */
	/*
	 * Shows the creature on the map
	 * @param panel
	 */


	public void draw(GridPanel panel) {

	}
/** 
 * Move to free direction that this creature has
 * @param direction
 */
	/* 
	 * Move to free direction that this creature has
	 * @param direction
	 */
	public void move(Direction direction) {
		if (direction == Direction.DOWN) {
			this.setY(this.getY()+1);

		}
		if (direction == Direction.UP) {
			this.setY(this.getY()-1);

		}
		if (direction == Direction.LEFT) {
			this.setX(this.getX()-1);

		}
		if (direction == Direction.RIGHT) {
			this.setX(this.getX()+1);

		}
		
	}

}
