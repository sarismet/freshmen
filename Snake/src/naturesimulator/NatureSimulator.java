package naturesimulator;

import game.Direction;
import game.GridGame;
import project.*;

import java.awt.Color;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;

/**
 * Class that implements the game logic for Nature Simulator.
 */
public class NatureSimulator extends GridGame {

	//public List<List<Node>> snakes = new ArrayList<List<Node>>();

	public static int devir = 0;
	public static int allindex = 0;
	public static int tempindex = 0;
	private List<FoodNode> food;

	public List<Node> creatures;
	private List<Node>[] alltemp;

	private List<Node>[] allsnakes;

	private Node[][] creaturesMap;
	

	/**
	 * Nodes a new Nature Simulator game instance
	 * 
	 * @param gridWidth
	 *            number of grid squares along the width
	 * @param gridHeight
	 *            number of grid squares along the height
	 * @param gridSquareSize
	 *            size of a grid square in pixels
	 * @param frameRate
	 *            frame rate (number of timer ticks per second)
	 */
	public NatureSimulator(int gridWidth, int gridHeight, int gridSquareSize, int frameRate, int maxsnakessize) {
		super(gridWidth, gridHeight, gridSquareSize, frameRate);

		food = new ArrayList<>();

		creatures = new ArrayList<>();

		creaturesMap = new Node[gridWidth][gridHeight];

		allsnakes = new ArrayList[maxsnakessize];
		alltemp = new ArrayList[maxsnakessize];

		allindex++;
		for (int i = 0; i < maxsnakessize; i++) {
			allsnakes[i] = new ArrayList<Node>();
		}
		allsnakes[0] = creatures;
		for (int i = 0; i < maxsnakessize; i++) {
			alltemp[i] = new ArrayList<Node>();
		}

	}

	@Override
	protected void timerTick() {
		// TODO Auto-generated method stub

		int x = 0;

		// to decide the number of free locations

		for (int i = 0; i < super.getGridWidth(); i++) {
			for (int j = 0; j < super.getGridHeight(); j++) {
				if (creaturesMap[i][j] != null) {
					x++;
				}
			}
		}
		System.out.println("TOPLAM NODE" + (x));
		/*// to control if there is food or not if there is no food it would add a food
		if (food.isEmpty()) {
			System.out.println("ADD FOOD 1111111");

			AddFood();

		}*/

		devir++;
		System.out.println(devir + " YILANLARIN SAYISI " + allindex);
	//	System.out.println("YEMEÐÝN KORDINATLARI X:" + food.get(0).getX() + "  Y: " + food.get(0).getY());
		// the first loop allindex present the number of food

		for (int i = 0; i < allindex; i++) {
			boolean divide = false;
			// to control if there is food or not if there is no food it would add a food
			if (food.isEmpty()) {
				System.out.println("ADD FOOD 2222222222222");
				AddFood();

			}

			Node head = allsnakes[i].get(0);
			int height = 0;
			while (head.getPrevious() != null) {
				head = head.getPrevious();
				height++;
				System.out.println(height);
			}
			height++;
			System.out.println(height);
			// the loop for each snakes
			for (int j = 0; j < height; j++) {
				
				// to control if there is food or not if there is no food it would add a food
			/*	if (food.isEmpty()) {
					for (int j2 = 0; j2 < allindex; j2++) {

						Node head2 = allsnakes[j2].get(0);
						int height2 = 0;
						while (head2.getPrevious() != null) {
							head2 = head2.getPrevious();
							height2++;
							System.out.println(height2);
						}
						height2++;
						System.out.println(height2);
						for (int k = 0; k < height2; k++) {
							
					
						updateCreaturesMap(allsnakes[j2].get(k).getX(), allsnakes[j2].get(k).getY(),
								allsnakes[j2].get(k));
							}
					}
					System.out.println("ADD FOOD 33333333333333333333333333333333333");
					AddFood();

				}*/
				// we make the node null because if it takes one more step and change its
				// location the old location would be null
				updateCreaturesMap(allsnakes[i].get(j).getX(), allsnakes[i].get(j).getY(), null);
				Action action ;
				if(allsnakes[i].get(j).getNext()==null) {
					 action = (allsnakes[i].get(j))
							.chooseAction(createLocalInformationForCreature((allsnakes[i].get(j))), food.get(0));
				}else {
					
					 action = (allsnakes[i].get(j))
							.chooseAction(createLocalInformationForCreature((allsnakes[i].get(j))), null);
				}
				// to choose action
				//Action action = (allsnakes[i].get(j))
					//	.chooseAction(createLocalInformationForCreature((allsnakes[i].get(j))), food.get(0));
				if (action.getType() == Action.Type.ATTACK) {
					System.out.println("SALDIRIYORUMMMMMMMMMM");

					Node attackedCreature = getCreatureAtDirection((allsnakes[i].get(j)).getX(),
							allsnakes[i].get(j).getY(), action.getDirection());

					allsnakes[i].get(j).attack(attackedCreature, action.getDirection());

					if (removeCreature(attackedCreature)) {
						divide = true;

					} else {
						System.out.println(
								"LABARRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR");
						allsnakes[i].get(j).stay();

					}

				} else if (action.getType() == Action.Type.MOVE) {

					if (isDirectionFree((allsnakes[i].get(j)).getX(), (allsnakes[i].get(j)).getY(),
							action.getDirection())) {
						System.out.println("MOVEEEEEEEEEEEEEEEEEEEEEEE");

						(allsnakes[i].get(j)).move(action.getDirection());

					} else {

						System.out.println("NEDEN BU HATALAR OLUYORRRRRRRRRRRRRR" + "j nin numarasý" + j);
						allsnakes[i].get(j).stay();
						updateCreaturesMap(allsnakes[i].get(j).getX(), allsnakes[i].get(j).getY(), allsnakes[i].get(j));
					}

				} else if (action.getType() == Action.Type.STAY) {

					allsnakes[i].get(j).stay();

				}

				updateCreaturesMap(allsnakes[i].get(j).getX(), allsnakes[i].get(j).getY(), allsnakes[i].get(j));

			}
			// if the food would be vanish (there is no case to say no) this if condition
			// add one more node

			if (divide == true) {
				List<Direction> FreeDirectionsToProduce = createLocalInformationForCreature(
						allsnakes[i].get((height - 1))).getFreeDirections();
				Direction directiontoproduce = LocalInformations.getRandomDirection(FreeDirectionsToProduce);
				Node newnode = allsnakes[i].get(height - 1).reproduce(directiontoproduce);
				if (newnode != null) {
					allsnakes[i].add(newnode);
					addDrawable(newnode);
					creaturesMap[newnode.getX()][newnode.getY()] = newnode;
					updateCreaturesMap(newnode.getX(), newnode.getY(), newnode);
					
					height++;
				} else {
				
					System.out.println(
							"HATALARRRRRRRRRRRRRRRRRR HATALARRRRRRRRRRRRRRRR GÖNLÜMÜ YARALARRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR");

				}

			}
			// the if condition to control if the snake has 8 height or not
			if (height == 8) {

				System.out.println("SÝMDÝ BÝ BÖLÜNELÝM DE ZAAAAAAAAAAaaaaaaaaaaaaaaaaaaaaaaa");
				System.out.print("   " + allindex);

				alltemp[tempindex] = new ArrayList<Node>();
				allsnakes[i].get(3).setPrevious(null);
				System.out.println("sdfsdfsdljfdsklfjsdffd");
				allsnakes[i].get(4).setNext(allsnakes[i].get(5));
				allsnakes[i].get(4).setPrevious(null);
				allsnakes[i].get(4).setColour(Color.BLUE);

				alltemp[tempindex].add(allsnakes[i].get(4));

				allsnakes[i].get(5).setNext(allsnakes[i].get(6));
				allsnakes[i].get(5).setPrevious(allsnakes[i].get(4));
				allsnakes[i].get(5).setColour(Color.BLUE);

				alltemp[tempindex].add(allsnakes[i].get(5));

				allsnakes[i].get(6).setNext(allsnakes[i].get(7));
				allsnakes[i].get(6).setPrevious(allsnakes[i].get(5));
				allsnakes[i].get(6).setColour(Color.BLUE);

				alltemp[tempindex].add(allsnakes[i].get(6));

				allsnakes[i].get(7).setNext(null);
				allsnakes[i].get(7).setPrevious(allsnakes[i].get(6));
				allsnakes[i].get(7).setColour(Color.RED);

				System.out.println("sdfsdfsdljfdsklfjsdffd");
				alltemp[tempindex].add(allsnakes[i].get(7));

				removeDrawable(allsnakes[i].get(4));
				System.out.println("sdfsdfsdljfdsklfjsdffd");
				creaturesMap[allsnakes[i].get(4).getX()][allsnakes[i].get(4).getY()] = null;
				updateCreaturesMap(allsnakes[i].get(4).getX(), allsnakes[i].get(4).getY(), allsnakes[i].get(4));
				allsnakes[i].remove(4);

				removeDrawable(allsnakes[i].get(4));

				creaturesMap[allsnakes[i].get(4).getX()][allsnakes[i].get(4).getY()] = null;
				updateCreaturesMap(allsnakes[i].get(4).getX(), allsnakes[i].get(4).getY(), allsnakes[i].get(4));
				allsnakes[i].remove(4);

				removeDrawable(allsnakes[i].get(4));

				creaturesMap[allsnakes[i].get(4).getX()][allsnakes[i].get(4).getY()] = null;
				updateCreaturesMap(allsnakes[i].get(4).getX(), allsnakes[i].get(4).getY(), allsnakes[i].get(4));
				allsnakes[i].remove(4);

				removeDrawable(allsnakes[i].get(4));

				creaturesMap[allsnakes[i].get(4).getX()][allsnakes[i].get(4).getY()] = null;
				updateCreaturesMap(allsnakes[i].get(4).getX(), allsnakes[i].get(4).getY(), allsnakes[i].get(4));
				allsnakes[i].remove(4);

				allindex++;
				allsnakes[allindex - 1].add(alltemp[tempindex].get(3));
				addDrawable(allsnakes[allindex - 1].get(0));
				creaturesMap[allsnakes[allindex - 1].get(0).getX()][allsnakes[allindex - 1].get(0)
						.getY()] = allsnakes[allindex - 1].get(0);
				updateCreaturesMap(allsnakes[allindex - 1].get(0).getX(), allsnakes[allindex - 1].get(0).getY(),
						allsnakes[allindex - 1].get(0));

				allsnakes[allindex - 1].add(alltemp[tempindex].get(2));
				addDrawable(allsnakes[allindex - 1].get(1));
				creaturesMap[allsnakes[allindex - 1].get(1).getX()][allsnakes[allindex - 1].get(1)
						.getY()] = allsnakes[allindex - 1].get(1);
				updateCreaturesMap(allsnakes[allindex - 1].get(1).getX(), allsnakes[allindex - 1].get(1).getY(),
						allsnakes[allindex - 1].get(1));

				allsnakes[allindex - 1].add(alltemp[tempindex].get(1));
				addDrawable(allsnakes[allindex - 1].get(2));
				creaturesMap[allsnakes[allindex - 1].get(2).getX()][allsnakes[allindex - 1].get(2)
						.getY()] = allsnakes[allindex - 1].get(2);
				updateCreaturesMap(allsnakes[allindex - 1].get(2).getX(), allsnakes[allindex - 1].get(2).getY(),
						allsnakes[allindex - 1].get(2));

				allsnakes[allindex - 1].add(alltemp[tempindex].get(0));
				addDrawable(allsnakes[allindex - 1].get(3));
				creaturesMap[allsnakes[allindex - 1].get(3).getX()][allsnakes[allindex - 1].get(3)
						.getY()] = allsnakes[allindex - 1].get(3);
				updateCreaturesMap(allsnakes[allindex - 1].get(3).getX(), allsnakes[allindex - 1].get(3).getY(),
						allsnakes[allindex - 1].get(3));

				

				tempindex++;

			}

		}

	}

	/**
	 * removes the food
	 * 
	 * @param creature
	 * @return
	 */
	protected boolean removeCreature(Node creature) {

		food.remove(creature);
		//food.clear();
		removeDrawable(creature);

		creaturesMap[creature.getX()][creature.getY()] = null;
		updateCreaturesMap(creature.getX(), creature.getY(), null);

		return true;

	}

	/**
	 * adds a food
	 */

	public void AddFood() {

		int food_x = (int) (Math.random() * super.getGridWidth());

		int food_y = (int) (Math.random() * super.getGridHeight());

		while (creaturesMap[food_x][food_y] != null) {
			food_x = (int) (Math.random() * super.getGridWidth());

			food_y = (int) (Math.random() * super.getGridHeight());

		}

		food.add(new FoodNode(food_x, food_y, new Color(128, 128, 0)));

		addDrawable(food.get(0));
		creaturesMap[food.get(0).getX()][food.get(0).getY()] = food.get(0);

		updateCreaturesMap(food.get(0).getX(), food.get(0).getY(), food.get(0));
		System.out.println("YEMEÐÝN KORDINATLARI X:" + food.get(0).getX() + "  Y: " + food.get(0).getY());

	}

	/**
	 * to check the location is free or not
	 * 
	 * @param x
	 * @param y
	 * @return
	 */

	protected boolean free(int x, int y) {
		if (creaturesMap[x][y] == null) {
			return true;
		}
		return false;
	}

	/**
	 * adds a new node
	 * 
	 * @param node
	 * @param addlist
	 * @return
	 */
	public boolean addCreature(Node node, List<Node> addlist) {

		addlist.add(node);
		addDrawable(node);
		creaturesMap[node.getX()][node.getY()] = node;
		updateCreaturesMap(node.getX(), node.getY(), node);
		return true;

	}

	/**
	 * gives the information of environment
	 * 
	 * @param creature
	 * @return
	 */

	private LocalInformations createLocalInformationForCreature(Node creature) {
		int x = creature.getX();
		int y = creature.getY();

		HashMap<Direction, Node> creatureses = new HashMap<>();
		creatureses.put(Direction.UP, getCreatureAtPosition(x, y - 1));
		creatureses.put(Direction.DOWN, getCreatureAtPosition(x, y + 1));
		creatureses.put(Direction.LEFT, getCreatureAtPosition(x - 1, y));
		creatureses.put(Direction.RIGHT, getCreatureAtPosition(x + 1, y));

		ArrayList<Direction> freeDirections = new ArrayList<>();
		if (creatureses.get(Direction.UP) == null && isPositionInsideGrid(x, y - 1)) {
			freeDirections.add(Direction.UP);
		}
		if (creatureses.get(Direction.DOWN) == null && isPositionInsideGrid(x, y + 1)) {
			freeDirections.add(Direction.DOWN);
		}
		if (creatureses.get(Direction.LEFT) == null && isPositionInsideGrid(x - 1, y)) {
			freeDirections.add(Direction.LEFT);
		}
		if (creatureses.get(Direction.RIGHT) == null && isPositionInsideGrid(x + 1, y)) {
			freeDirections.add(Direction.RIGHT);
		}

		return new LocalInformations(getGridWidth(), getGridHeight(), creatureses, freeDirections);
	}

	/**
	 * Checks wheter the location is valid or not
	 * 
	 * @param x
	 * @param y
	 * @return
	 */

	private boolean isPositionInsideGrid(int x, int y) {
		return (x >= 0 && x < getGridWidth()) && (y >= 0 && y < getGridHeight());
	}

	/**
	 * Updates the Map that contains nodes
	 * 
	 * @param x
	 * @param y
	 * @param creature
	 */

	protected void updateCreaturesMap(int x, int y, Node creature) {

		creaturesMap[x][y] = creature;

	}

	/**
	 * Gets the nodes to a position available
	 * 
	 * @param x
	 * @param y
	 * @return
	 */

	private Node getCreatureAtPosition(int x, int y) {

		if (!isPositionInsideGrid(x, y)) {
			return null;
		}
		return creaturesMap[x][y];
	}

	/**
	 * Gets the creatures to the Direction determined
	 */

	private Node getCreatureAtDirection(int x, int y, Direction direction) {
		if (direction == null) {
			return null;
		}
		int xTarget = x;
		int yTarget = y;
		if (direction == Direction.UP) {
			yTarget--;
		} else if (direction == Direction.DOWN) {
			yTarget++;
		} else if (direction == Direction.LEFT) {
			xTarget--;
		} else if (direction == Direction.RIGHT) {
			xTarget++;
		}
		return getCreatureAtPosition(xTarget, yTarget);
	}

	/**
	 * Checks wheter the location is valid or not
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean isPositionFree(int x, int y) {
		return isPositionInsideGrid(x, y);

	}

	/**
	 * Checks wheter the location is valid or not
	 * 
	 * @param x
	 * @param y
	 * @param direction
	 * @return
	 */
	private boolean isDirectionFree(int x, int y, Direction direction) {
		if (direction == null) {
			return false;
		}
		int xTarget = x;
		int yTarget = y;
		if (direction == Direction.UP) {
			yTarget--;
		} else if (direction == Direction.DOWN) {
			yTarget++;
		} else if (direction == Direction.LEFT) {
			xTarget--;
		} else if (direction == Direction.RIGHT) {
			xTarget++;
		}
		return isPositionFree(xTarget, yTarget);
	}

}
