package com.devoteam.robotcommander;

import com.devoteam.robotcommander.DTOs.Room;
import com.devoteam.robotcommander.services.RobotService;
import com.devoteam.robotcommander.services.RobotServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        assertEquals(new Room(3, 10), room);
    }

    @ParameterizedTest
    @CsvSource({"3 3 10, Invalid number of arguments"})
    @CsvSource({"3, Invalid number of arguments"})
    @CsvSource({" , Invalid number of arguments"})
    @CsvSource({", Invalid number of arguments"})
    @CsvSource({"asdfasdf ff, Your input is not supported - Width:asdfasdf and Depth:ff"})
    @CsvSource({"99999999999999 00000000000000, Your input is not supported - Width:99999999999999 and Depth:00000000000000"})
    public void initializeRoomThrowsException(String input, String expectedMessage) {
        // setup
        RobotService robotService = new RobotServiceImpl();

        // assert
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> robotService.initializeRoom(input));
        assertEquals(illegalArgumentException.getMessage(), expectedMessage);
    }
}
