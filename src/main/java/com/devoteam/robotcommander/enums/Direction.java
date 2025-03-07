package com.devoteam.robotcommander.enums;

public enum Direction {
    N(0),
    E(1),
    S(2),
    W(3);

    final int direction;

    Direction(int i) {
        this.direction = i;
    }

    public int getDirectionValue() {
        return direction;
    }

    public static Direction getDirection(int direction) {
        return Direction.values()[direction];
    }
}
