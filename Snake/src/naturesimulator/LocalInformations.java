package naturesimulator;

import game.Direction;
import project.*;


import java.util.HashMap;
import java.util.List;


public class LocalInformations {

    private int gridWidth;
    private int gridHeight;

    private HashMap<Direction, Node> creatures;
    private List<Direction> freeDirections;


    LocalInformations(int gridWidth, int gridHeight,
                     HashMap<Direction, Node> creatures, List<Direction> freeDirections) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.creatures = creatures;
        this.freeDirections = freeDirections;
    }


    public int getGridWidth() {
        return gridWidth;
    }


    public int getGridHeight() {
        return gridHeight;
    }


    public Node getCreatureUp() {
        return creatures.get(Direction.UP);
    }


    public Node getCreatureDown() {
        return creatures.get(Direction.DOWN);
    }

    public Node getCreatureLeft() {
        return creatures.get(Direction.LEFT);
    }


    public Node getCreatureRight() {
        return creatures.get(Direction.RIGHT);
    }


    public List<Direction> getFreeDirections() {
        return freeDirections;
    }


    public static Direction getRandomDirection(List<Direction> possibleDirections) {
        if (possibleDirections.isEmpty()) {
            return null;
        }
        int randomIndex = (int)(Math.random() * possibleDirections.size());
        return possibleDirections.get(randomIndex);
    }

}
