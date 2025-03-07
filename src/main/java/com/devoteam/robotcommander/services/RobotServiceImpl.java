package com.devoteam.robotcommander.services;

import com.devoteam.robotcommander.DTOs.Robot;
import com.devoteam.robotcommander.DTOs.Room;
import com.devoteam.robotcommander.enums.Direction;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
public class RobotServiceImpl implements RobotService {

    public Room initializeRoom(String values) {
        String[] input = validateInputLength(values, 2);

        Dimensions validDimensions = getValidateDimensions(input);

        return new Room(validDimensions.width(), validDimensions.depth());
    }

    public Robot initializeRobotPosition(String values, Room room) {
        String[] strings = validateInputLength(values, 3);
        Dimensions validDimensions = getValidateDimensions(strings);
        Direction direction;
        try {
            direction = Direction.valueOf(strings[2].toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown direction: " + strings[2].toUpperCase());
        }

        isValidPosition(validDimensions.depth, validDimensions.width, room);

        return new Robot(validDimensions.width, validDimensions.depth, direction);
    }

    public Robot moveRobot(String movementString, Robot robot, Room room) {
        validateInputLength(movementString, 1);
        List<String> input = movementString.chars()
                .mapToObj(c -> String.valueOf((char) c))
                .toList();

        Robot movedRobot = new Robot(robot.getWidth(), robot.getDepth(), robot.getDirection());

        for (String i : input) {

            switch (i) {
                // Makes use of modulus to circle through the directions
                case "L" ->
                        movedRobot.setDirection(Direction.getDirection((robot.getDirection().getDirectionValue() - 1) % 4));
                case "R" ->
                        movedRobot.setDirection(Direction.getDirection((robot.getDirection().getDirectionValue() + 1) % 4));
                case "F" -> movedRobot = moveRobotForward(movedRobot, room);
                default -> throw new IllegalArgumentException(MessageFormat.format("Invalid movement: {0}", i));
            }
        }

        return movedRobot;
    }

    private Robot moveRobotForward(Robot robot, Room room) {
        switch (robot.getDirection()) {
            case N -> robot.setDepth(robot.getDepth() + 1);
            case E -> robot.setWidth(robot.getWidth() + 1);
            case S -> robot.setDepth(robot.getDepth() - 1);
            case W -> robot.setWidth(robot.getWidth() - 1);
        }
        isValidPosition(robot.getWidth(), robot.getDepth(), room);
        return robot;
    }

    private void isValidPosition(long width, long depth, Room room) {
        boolean widthIsInRange = width <= room.width() && width > 0;
        boolean depthIsInRange = depth <= room.depth() && depth > 0;
        if (!(widthIsInRange && depthIsInRange)) {
            throw new IllegalArgumentException(MessageFormat.format("Robot is out of range! Can not go to W: {0} D: {1}", width, depth));
        }
    }

    private static String[] validateInputLength(String values, int expectedLength) {
        if (values == null || values.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid number of arguments");
        }
        String[] s = values.split(" ");
        if (s.length != expectedLength) {
            throw new IllegalArgumentException("Invalid number of arguments");
        }
        return s;
    }

    private static Dimensions getValidateDimensions(String[] input) {
        long width;
        long depth;
        try {
            width = Long.parseLong(input[0]);
            depth = Long.parseLong(input[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(MessageFormat.format("Your input is not supported - Width: {0} and Depth: {1}", input[0], input[1]));
        }

        if (width <= 0 || depth <= 0) {
            throw new IllegalArgumentException(MessageFormat.format("Your input is not supported - Width: {0} and Depth: {1}", input[0], input[1]));
        }
        return new Dimensions(width, depth);
    }

    private record Dimensions(long width, long depth) {
    }
}
