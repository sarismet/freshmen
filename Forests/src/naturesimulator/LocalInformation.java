package naturesimulator;

import game.Direction;
import project.Creature;

import java.util.HashMap;
import java.util.List;


public class LocalInformation {

    private int gridWidth;
    private int gridHeight;

    private HashMap<Direction, Creature> creatures;
    private List<Direction> freeDirections;

    LocalInformation(int gridWidth, int gridHeight,
                     HashMap<Direction, Creature> creatures, List<Direction> freeDirections) {
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


    public Creature getCreatureUp() {
        return creatures.get(Direction.UP);
    }


    public Creature getCreatureDown() {
        return creatures.get(Direction.DOWN);
    }

    public Creature getCreatureLeft() {
        return creatures.get(Direction.LEFT);
    }

    
    public Creature getCreatureRight() {
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
