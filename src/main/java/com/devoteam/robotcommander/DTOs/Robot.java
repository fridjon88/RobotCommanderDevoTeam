package com.devoteam.robotcommander.DTOs;

import com.devoteam.robotcommander.enums.Direction;

public final class Robot {

    private long width;
    private long depth;
    private Direction direction;

    public Robot(long width, long depth, Direction facingDirection) {
        this.width = width;
        this.depth = depth;
        this.direction = facingDirection;
    }

    public long getWidth() {
        return width;
    }

    public void setWidth(long width) {
        this.width = width;
    }

    public long getDepth() {
        return depth;
    }

    public void setDepth(long depth) {
        this.depth = depth;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
