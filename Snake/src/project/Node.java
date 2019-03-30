package project;

import java.awt.Color;

import game.Direction;
import game.Drawable;

import naturesimulator.Action;

import naturesimulator.LocalInformations;
import ui.GridPanel;

/**
 * 
 * @author ÝBABE
 *
 */

public class Node implements Drawable {
	private Node next;
	private Node previous;

	/**
	 * get the previous node
	 * 
	 * @return
	 */
	public Node getPrevious() {
		return previous;
	}

	/**
	 * 
	 * @param previous
	 */
	public void setPrevious(Node previous) {
		this.previous = previous;
	}

	private Color colour;
	private int x;
	private int y;
	private Direction direction;

	/**
	 * Constructor for each part of snake
	 * 
	 * @param x
	 * @param y
	 * @param color
	 * @param next
	 * @param previous
	 */
	public Node(int x, int y, Color color, Node next, Node previous) {
		this.colour = color;
		this.x = x;
		this.y = y;
		this.next = next;
		this.previous = previous;

	}

	/**
	 * gets direction to move for nodes that has next
	 * 
	 * @return
	 */
	public Direction getDirection() {
		return direction;
	}

	/**
	 * sets the direction for back side nodes
	 * 
	 * @param direction
	 */
	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	/**
	 * gets color to draw it
	 * 
	 * @return
	 */
	public Color getColour() {
		return colour;
	}

	/**
	 * Sets color
	 * 
	 * @param colour
	 */
	public void setColour(Color colour) {
		this.colour = colour;
	}

	/**
	 * Gets x location
	 * 
	 * @return
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets x location
	 * 
	 * @param x
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Gets y location
	 * 
	 * @return
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets y location
	 * 
	 * @param y
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Gets the next node
	 * 
	 * @return
	 */
	public Node getNext() {
		return next;
	}

	/**
	 * Sets the next node
	 * 
	 * @param next
	 */

	public void setNext(Node next) {
		this.next = next;
	}

	/**
	 * Draws the node on the panel
	 */
	@Override
	public void draw(GridPanel panel) {

		panel.drawSquare(getX(), getY(), getColour());

	}

	/**
	 * produce one more Node according to localinformation of last Node of the snake
	 * that the node is belong to
	 * 
	 * @param direction
	 * @return
	 */
	public Node reproduce(Direction direction) {
		if (direction == Direction.DOWN) {

			Node newnode = new Node(this.getX(), (this.getY() + 1), Color.BLUE, this, null);
			this.setPrevious(newnode);

			return newnode;

		} else if (direction == Direction.UP) {

			Node newnode = new Node(this.getX(), (this.getY() - 1), Color.BLUE, this, null);
			this.setPrevious(newnode);
			return newnode;

		} else if (direction == Direction.LEFT) {

			Node newnode = new Node((this.getX() - 1), this.getY(), Color.BLUE, this, null);
			this.setPrevious(newnode);
			return newnode;

		} else if (direction == Direction.RIGHT) {

			Node newnode = new Node((this.getX() + 1), this.getY(), Color.BLUE, this, null);
			this.setPrevious(newnode);
			return newnode;

		}
		return null;
	}

	/**
	 * Stays if the choose action return Stay action and set the Direction null
	 */
	public void stay() {
	//	System.out.println("VALLA KANKA BEKLÝYORUZ BAKALIM ALLAH KERÝM");
		this.setX(this.getX());
		this.setY(this.getY());
		this.setDirection(null);

	}

	public void move(Direction direction) {
		if (direction == Direction.DOWN) {
			this.setY(this.getY() + 1);

		}
		if (direction == Direction.UP) {
			this.setY(this.getY() - 1);

		}
		if (direction == Direction.LEFT) {
			this.setX(this.getX() - 1);

		}
		if (direction == Direction.RIGHT) {
			this.setX(this.getX() + 1);

		}

	}

	/**
	 * Chooses a action according to the location of food and localinformation that
	 * the Node has
	 * 
	 * @param localInformation
	 * @param ourfood
	 * @return
	 */

	public Action chooseAction(LocalInformations localInformation, Node ourfood) {
		// if the next node is null that means that it is head of snake
		if (this.getNext() == null) {
			// if x and food ' s x is equal that means that the head have to go towards the
			// food
			if (this.getX() == ourfood.getX()) {
				if (this.getY() < ourfood.getY()) {
					if (localInformation.getCreatureDown() instanceof FoodNode) {
						this.setDirection(Direction.DOWN);
						return new Action(Action.Type.ATTACK, Direction.DOWN);
					} else {
						if (!(localInformation.getCreatureDown() instanceof Node)
								&& ((this.getY() + 1) < localInformation.getGridHeight())) {

							this.setDirection(Direction.DOWN);
							return new Action(Action.Type.MOVE, Direction.DOWN);

						} else if (!(localInformation.getCreatureRight() instanceof Node)
								&& ((this.getX() + 1) < localInformation.getGridWidth())) {

							this.setDirection(Direction.RIGHT);
							return new Action(Action.Type.MOVE, Direction.RIGHT);

						} else if (!(localInformation.getCreatureLeft() instanceof Node) && ((this.getX() - 1) >= 0)) {

							this.setDirection(Direction.LEFT);
							return new Action(Action.Type.MOVE, Direction.LEFT);

						} else if (!(localInformation.getCreatureUp() instanceof Node) && ((this.getY() - 1) >= 0)) {

							this.setDirection(Direction.UP);
							return new Action(Action.Type.MOVE, Direction.UP);

						} else {
							System.out.println("ARTIK  YAPAKCAK BÝR ÞEY KALMADI");
							this.setDirection(null);
							return new Action(Action.Type.STAY);

						}

					}

				} else if (this.getY() > ourfood.getY()) { // DONE
					if (localInformation.getCreatureUp() instanceof FoodNode) {
						this.setDirection(Direction.UP);
						return new Action(Action.Type.ATTACK, Direction.UP);
					} else {
						if (!(localInformation.getCreatureUp() instanceof Node) && ((this.getY() - 1) >= 0)) {

							this.setDirection(Direction.UP);
							return new Action(Action.Type.MOVE, Direction.UP);
						} else if (!(localInformation.getCreatureRight() instanceof Node)
								&& ((this.getX() + 1) < localInformation.getGridWidth())) {

							this.setDirection(Direction.RIGHT);
							return new Action(Action.Type.MOVE, Direction.RIGHT);

						}

						else if (!(localInformation.getCreatureLeft() instanceof Node) && ((this.getX() - 1) >= 0)) {

							this.setDirection(Direction.LEFT);
							return new Action(Action.Type.MOVE, Direction.LEFT);

						} else if (!(localInformation.getCreatureDown() instanceof Node)
								&& ((this.getY() + 1) < localInformation.getGridHeight())) {

							this.setDirection(Direction.DOWN);
							return new Action(Action.Type.MOVE, Direction.DOWN);

						} else {

							System.out.println("ARTIK  YAPAKCAK BÝR ÞEY KALMADI");
							this.setDirection(null);
							return new Action(Action.Type.STAY);

						}

					}

				}

			} else if (this.getY() == ourfood.getY()) {
				// if y and food ' s y is equal that means that the head have to go towards the
				// food
				if (this.getX() < ourfood.getX()) {
					// if x is lower than fod's x it turns according to condition that it has
					if (localInformation.getCreatureRight() instanceof FoodNode) {
						this.setDirection(Direction.RIGHT);
						return new Action(Action.Type.ATTACK, Direction.RIGHT);
					} else {
						if (!(localInformation.getCreatureRight() instanceof Node)
								&& ((this.getX() + 1) < localInformation.getGridWidth())) {

							this.setDirection(Direction.RIGHT);
							return new Action(Action.Type.MOVE, Direction.RIGHT);

						} else if (!(localInformation.getCreatureDown() instanceof Node)
								&& ((this.getY() + 1) < localInformation.getGridHeight())) {

							this.setDirection(Direction.DOWN);
							return new Action(Action.Type.MOVE, Direction.DOWN);

						} else if (!(localInformation.getCreatureUp() instanceof Node) && ((this.getY() - 1) >= 0)) {

							this.setDirection(Direction.UP);
							return new Action(Action.Type.MOVE, Direction.UP);
						} else if (!(localInformation.getCreatureLeft() instanceof Node) && ((this.getX() - 1) >= 0)) {

							this.setDirection(Direction.LEFT);
							return new Action(Action.Type.MOVE, Direction.LEFT);

						} else {

							System.out.println("ARTIK  YAPAKCAK BÝR ÞEY KALMADI");
							this.setDirection(null);
							return new Action(Action.Type.STAY);

						}

					}

				} else if (this.getX() > ourfood.getX()) {
					// if x is higher than fod's x it turns according to condition that it has
					if (localInformation.getCreatureLeft() instanceof FoodNode) {
						this.setDirection(Direction.LEFT);
						return new Action(Action.Type.ATTACK, Direction.LEFT);
					} else {
						if (!(localInformation.getCreatureLeft() instanceof Node) && ((this.getX() - 1) >= 0)) {

							this.setDirection(Direction.LEFT);
							return new Action(Action.Type.MOVE, Direction.LEFT);

						} else if (!(localInformation.getCreatureUp() instanceof Node) && ((this.getY() - 1) >= 0)) {

							this.setDirection(Direction.UP);
							return new Action(Action.Type.MOVE, Direction.UP);
						} else if (!(localInformation.getCreatureDown() instanceof Node)
								&& ((this.getY() + 1) < localInformation.getGridHeight())) {

							this.setDirection(Direction.DOWN);
							return new Action(Action.Type.MOVE, Direction.DOWN);

						} else if (!(localInformation.getCreatureRight() instanceof Node)
								&& ((this.getX() + 1) < localInformation.getGridWidth())) {

							this.setDirection(Direction.RIGHT);
							return new Action(Action.Type.MOVE, Direction.RIGHT);

						} else {

							System.out.println("ARTIK  YAPAKCAK BÝR ÞEY KALMADI");
							this.setDirection(null);
							return new Action(Action.Type.STAY);

						}

					}

				}

			} else if (this.getX() != ourfood.getX() && this.getY() != ourfood.getY() && this.getNext() == null) {
				// if the local x,y and food's x,y are not equal

				if (this.getX() > ourfood.getX() && this.getY() < ourfood.getY()) {
					if (!(localInformation.getCreatureLeft() instanceof Node) && ((this.getX() - 1) >= 0)) {

						this.setDirection(Direction.LEFT);
						return new Action(Action.Type.MOVE, Direction.LEFT);

					} else if (!(localInformation.getCreatureDown() instanceof Node)
							&& ((this.getY() + 1) < localInformation.getGridHeight())) {

						this.setDirection(Direction.DOWN);
						return new Action(Action.Type.MOVE, Direction.DOWN);

					} else if (!(localInformation.getCreatureUp() instanceof Node) && ((this.getY() - 1) >= 0)) {

						this.setDirection(Direction.UP);
						return new Action(Action.Type.MOVE, Direction.UP);
					} else if (!(localInformation.getCreatureRight() instanceof Node)
							&& ((this.getX() + 1) < localInformation.getGridWidth())) {

						this.setDirection(Direction.RIGHT);
						return new Action(Action.Type.MOVE, Direction.RIGHT);

					} else {

						System.out.println("ARTIK  YAPAKCAK BÝR ÞEY KALMADI");
						this.setDirection(null);
						return new Action(Action.Type.STAY);

					}

				} else if (this.getX() > ourfood.getX() && this.getY() > ourfood.getY()) {
					if (!(localInformation.getCreatureLeft() instanceof Node) && ((this.getX() - 1) >= 0)) {

						this.setDirection(Direction.LEFT);
						return new Action(Action.Type.MOVE, Direction.LEFT);

					} else if (!(localInformation.getCreatureUp() instanceof Node) && ((this.getY() - 1) >= 0)) {

						this.setDirection(Direction.UP);
						return new Action(Action.Type.MOVE, Direction.UP);
					} else if (!(localInformation.getCreatureRight() instanceof Node)
							&& ((this.getX() + 1) < localInformation.getGridWidth())) {

						this.setDirection(Direction.RIGHT);
						return new Action(Action.Type.MOVE, Direction.RIGHT);

					} else if (!(localInformation.getCreatureDown() instanceof Node)
							&& ((this.getY() + 1) < localInformation.getGridHeight())) {

						this.setDirection(Direction.DOWN);
						return new Action(Action.Type.MOVE, Direction.DOWN);

					} else {

						System.out.println("ARTIK  YAPAKCAK BÝR ÞEY KALMADI");
						this.setDirection(null);
						return new Action(Action.Type.STAY);

					}

				} else if (this.getX() < ourfood.getX() && this.getY() > ourfood.getY()) {
					if (!(localInformation.getCreatureRight() instanceof Node)
							&& ((this.getX() + 1) < localInformation.getGridWidth())) {

						this.setDirection(Direction.RIGHT);
						return new Action(Action.Type.MOVE, Direction.RIGHT);

					} else if (!(localInformation.getCreatureUp() instanceof Node) && ((this.getY() - 1) >= 0)) {

						this.setDirection(Direction.UP);
						return new Action(Action.Type.MOVE, Direction.UP);
					} else if (!(localInformation.getCreatureLeft() instanceof Node) && ((this.getX() - 1) >= 0)) {

						this.setDirection(Direction.LEFT);
						return new Action(Action.Type.MOVE, Direction.LEFT);

					} else if (!(localInformation.getCreatureDown() instanceof Node)
							&& ((this.getY() + 1) < localInformation.getGridHeight())) {

						this.setDirection(Direction.DOWN);
						return new Action(Action.Type.MOVE, Direction.DOWN);

					} else {

						System.out.println("ARTIK  YAPAKCAK BÝR ÞEY KALMADI");
						this.setDirection(null);
						return new Action(Action.Type.STAY);

					}

				} else if (this.getX() < ourfood.getX() && this.getY() < ourfood.getY()) {

					if (!(localInformation.getCreatureRight() instanceof Node)
							&& ((this.getX() + 1) < localInformation.getGridWidth())) {

						this.setDirection(Direction.RIGHT);
						return new Action(Action.Type.MOVE, Direction.RIGHT);

					} else if (!(localInformation.getCreatureDown() instanceof Node)
							&& ((this.getY() + 1) < localInformation.getGridHeight())) {

						this.setDirection(Direction.DOWN);
						return new Action(Action.Type.MOVE, Direction.DOWN);

					} else if (!(localInformation.getCreatureUp() instanceof Node) && ((this.getY() - 1) >= 0)) {

						this.setDirection(Direction.UP);
						return new Action(Action.Type.MOVE, Direction.UP);
					} else if (!(localInformation.getCreatureLeft() instanceof Node) && ((this.getX() - 1) >= 0)) {

						this.setDirection(Direction.LEFT);
						return new Action(Action.Type.MOVE, Direction.LEFT);

					} else {

						System.out.println("ARTIK  YAPAKCAK BÝR ÞEY KALMADI");
						this.setDirection(null);
						return new Action(Action.Type.STAY);

					}

				}

			}
			this.setDirection(null);
			return new Action(Action.Type.STAY);

		} // DONE

		// if its next is not null that meains that it is not head and its color is blue
		// this node moves accoring to its next's movement
		if (this.getNext() != null) {
			if (this.getNext().getDirection() != null) {
				if (this.getX() == this.getNext().getX() && this.getY() != this.getNext().getY()) {

					if (this.getY() < this.getNext().getY()) {

						this.setDirection(Direction.DOWN);
						return new Action(Action.Type.MOVE, Direction.DOWN);

					} else if (this.getY() > this.getNext().getY()) {

						this.setDirection(Direction.UP);
						return new Action(Action.Type.MOVE, Direction.UP);

					}

				}

				else if (this.getY() == this.getNext().getY() && this.getX() != this.getNext().getX()) {

					if (this.getX() < this.getNext().getX()) {

						this.setDirection(Direction.RIGHT);
						return new Action(Action.Type.MOVE, Direction.RIGHT);

					} else if (this.getX() > this.getNext().getX()) {

						this.setDirection(Direction.LEFT);
						return new Action(Action.Type.MOVE, Direction.LEFT);

					}

				} else if (this.getY() != this.getNext().getY() && this.getX() != this.getNext().getX()) {
					if (this.getX() < this.getNext().getX() && this.getY() > this.getNext().getY()) {
						if (this.getNext().getDirection() == Direction.RIGHT) {
							this.setDirection(Direction.UP);
							return new Action(Action.Type.MOVE, Direction.UP);

						} else if (this.getNext().getDirection() == Direction.UP) {
							this.setDirection(Direction.RIGHT);
							return new Action(Action.Type.MOVE, Direction.RIGHT);

						}

					} else if (this.getX() > this.getNext().getX() && this.getY() > this.getNext().getY()) {
						if (this.getNext().getDirection() == Direction.UP) {
							this.setDirection(Direction.LEFT);
							return new Action(Action.Type.MOVE, Direction.LEFT);

						} else if (this.getNext().getDirection() == Direction.LEFT) {
							this.setDirection(Direction.UP);
							return new Action(Action.Type.MOVE, Direction.UP);

						}

					} else if (this.getX() > this.getNext().getX() && this.getY() < this.getNext().getY()) {
						if (this.getNext().getDirection() == Direction.DOWN) {
							this.setDirection(Direction.LEFT);
							return new Action(Action.Type.MOVE, Direction.LEFT);

						} else if (this.getNext().getDirection() == Direction.LEFT) {
							this.setDirection(Direction.DOWN);
							return new Action(Action.Type.MOVE, Direction.DOWN);

						}

					} else if (this.getX() < this.getNext().getX() && this.getY() < this.getNext().getY()) {
						if (this.getNext().getDirection() == Direction.RIGHT) {
							this.setDirection(Direction.DOWN);
							return new Action(Action.Type.MOVE, Direction.DOWN);

						} else if (this.getNext().getDirection() == Direction.DOWN) {
							this.setDirection(Direction.RIGHT);
							return new Action(Action.Type.MOVE, Direction.RIGHT);

						}

					}

				}

			} else {
				this.setDirection(null);
				return new Action(Action.Type.STAY);

			}

		}

		this.setDirection(null);

		System.out.println("BEN BÝR NODUM VE BOÞ BOÞ GEZÝYORUM BÝR HATA OLMAMALI");
		return new Action(Action.Type.STAY);

	}

	/**
	 * attacks the food
	 * 
	 * @param attackedCreature
	 * @param direction
	 * @return
	 */
	public boolean attack(Node attackedCreature, Direction direction) {

		if (attackedCreature instanceof FoodNode) {

			this.setX(attackedCreature.getX());
			this.setY(attackedCreature.getY());
			this.setDirection(direction);
			return true;
		}
		return false;
	}

}
