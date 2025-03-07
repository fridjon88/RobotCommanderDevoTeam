package com.devoteam.robotcommander;

import com.devoteam.robotcommander.DTOs.Robot;
import com.devoteam.robotcommander.DTOs.Room;
import com.devoteam.robotcommander.enums.Direction;
import com.devoteam.robotcommander.services.RobotService;
import com.devoteam.robotcommander.services.RobotServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class RobotServiceTest {

    @Test
    public void initializeRoomSuccessful() {
        // setup
        RobotService robotService = new RobotServiceImpl();

        // act
        Room room = robotService.initializeRoom("3 10");

        // assert
        Assertions.assertEquals(new Room(3, 10), room);
    }

    @ParameterizedTest
    @CsvSource({"3 3 10, Invalid number of arguments"})
    @CsvSource({"3, Invalid number of arguments"})
    @CsvSource({" , Invalid number of arguments"})
    @CsvSource({", Invalid number of arguments"})
    @CsvSource({"asdfasdf ff, Your input is not supported - Width: asdfasdf and Depth: ff"})
    @CsvSource({"99999999999999 00000000000000, Your input is not supported - Width: 99999999999999 and Depth: 00000000000000"})
    @CsvSource({"-1 -2, Your input is not supported - Width: -1 and Depth: -2"})
    public void initializeRoomThrowsException(String input, String expectedMessage) {
        // setup
        RobotService robotService = new RobotServiceImpl();

        // act & assert
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
                () -> robotService.initializeRoom(input));
        Assertions.assertEquals(expectedMessage, illegalArgumentException.getMessage());
    }

    @ParameterizedTest
    @CsvSource({"1 1 N"})
    @CsvSource({"10 10 E"})
    @CsvSource({"5 10 W"})
    @CsvSource({"10 1 S"})
    public void placeRobotSuccess(String input) {
        // setup
        RobotService robotService = new RobotServiceImpl();
        String[] inputArray = input.split(" ");
        long width = Long.parseLong(inputArray[0]);
        long depth = Long.parseLong(inputArray[1]);
        Direction direction = Direction.valueOf(inputArray[2]);

        // act
        Robot robot = robotService.initializeRobotPosition(input, new Room(10, 10));

        // assert
        Assertions.assertEquals(width, robot.getWidth());
        Assertions.assertEquals(depth, robot.getDepth());
        Assertions.assertEquals(direction, robot.getDirection());
    }

    @ParameterizedTest
    @CsvSource({"3 3, Invalid number of arguments"})
    @CsvSource({"3, Invalid number of arguments"})
    @CsvSource({" , Invalid number of arguments"})
    @CsvSource({"1 -1 N, Your input is not supported - Width: 1 and Depth: -1"})
    @CsvSource({"1 0 N, Your input is not supported - Width: 1 and Depth: 0"})
    @CsvSource({"N E S, Your input is not supported - Width: N and Depth: E"})
    @CsvSource({"1 1 B, Unknown direction: B"})
    @CsvSource({"10 10 N, Unknown direction: B"})
    @CsvSource({"11 11 N, Robot is out of range! Can not go to W: 11 D: 11"})
    @CsvSource({"1 0 N, Your input is not supported - Width: 1 and Depth: 0"})
    @CsvSource({"-1 1 N, Your input is not supported - Width: -1 and Depth: 1"})
    public void placeRobotThrowsException(String input, String expectedMessage) {
        // setup
        RobotService robotService = new RobotServiceImpl();

        // act & assert
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
                () -> robotService.initializeRobotPosition(input, new Room(10, 10)));
        Assertions.assertEquals(expectedMessage, illegalArgumentException.getMessage());
    }

    @ParameterizedTest
    @CsvSource({"RF, 10, 10, E"})
    @CsvSource({"RFLLF, 9, 10, W"})
    public void moveRobotSuccess(String input, String expectedWidth, String expectedDepth, String expectedFacingDirection) {
        // setup
        RobotService robotService = new RobotServiceImpl();

        // act
        Robot robot = robotService.moveRobot(input, new Robot(9, 10, Direction.N), new Room(10, 10));

        // assert
        Assertions.assertEquals(Long.valueOf(expectedWidth), robot.getWidth());
        Assertions.assertEquals(Long.valueOf(expectedDepth), robot.getDepth());
        Assertions.assertEquals(expectedFacingDirection, robot.getDirection().toString());

    }
}
