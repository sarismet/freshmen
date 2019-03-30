package naturesimulator;

import game.Direction;



public class Action {

    public enum Type {
        MOVE,
        REPRODUCE,
        ATTACK,
        STAY,
    }

    private final Type type;
    private final Direction direction;


    public Action(Type type) {
        if (type == Type.MOVE || type == Type.ATTACK || type == Type.REPRODUCE) {
            throw new IllegalArgumentException("You cannot create an action "
                    + "of type " + type + " without a direction");
        }
        this.type = type;
        direction = null;
    }


    public Action(Type type, Direction direction) {
        if (type == Type.STAY) {
            throw new IllegalArgumentException("Action of type "
                    + type + " should be created without a direction");
        }
        this.type = type;
        this.direction = direction;
    }


    public Type getType() {
        return type;
    }


    public Direction getDirection() {
        return direction;
    }

}
