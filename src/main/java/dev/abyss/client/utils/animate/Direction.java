package dev.abyss.client.utils.animate;

public enum Direction {

    FORWARDS,
    BACKWARDS;

    public Direction opposite() {
        if (this == Direction.FORWARDS) {
            return Direction.BACKWARDS;
        } else {
        	return Direction.FORWARDS;
        }
    }
}