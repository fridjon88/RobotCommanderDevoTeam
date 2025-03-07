package com.devoteam.robotcommander.services;

import com.devoteam.robotcommander.DTOs.Robot;
import com.devoteam.robotcommander.DTOs.Room;
import com.devoteam.robotcommander.enums.Direction;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

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
        Direction direction = Direction.valueOf(strings[2]);

        isValidPosition(validDimensions, room);

        return new Robot(validDimensions.depth, validDimensions.width, direction);
    }

    private void isValidPosition(Dimensions dimensions, Room room) {
        boolean widthIsInRange = dimensions.width() > room.width() || dimensions.width() > 0;
        boolean depthIsInRange = dimensions.depth() > room.depth() || dimensions.depth() > 0;
        if (!(widthIsInRange || depthIsInRange)) {
            throw new IllegalStateException(MessageFormat.format("Robot is out of range! Can not go to W:{0} D:{1}", dimensions.width(), dimensions.depth()));
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
            throw new IllegalArgumentException(MessageFormat.format("Your input is not supported - Width:{0} and Depth:{1}", input[0], input[1]));
        }

        if (width <= 0 || depth <= 0) {
            throw new IllegalArgumentException(MessageFormat.format("Your input is not supported - Width:{0} and Depth:{1}", input[0], input[1]));
        }
        return new Dimensions(width, depth);
    }

    private record Dimensions(long width, long depth) {
    }
}
