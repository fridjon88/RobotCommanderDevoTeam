package com.devoteam.robotcommander.services;

import com.devoteam.robotcommander.DTOs.Robot;
import com.devoteam.robotcommander.DTOs.Room;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Scanner;

@Component
public class RobotCommander {

    private final RobotService robotService;

    public RobotCommander(RobotService robotService) {
        this.robotService = robotService;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Greetings, Robot Commander!");
        System.out.println("Insert desired size of the room, input width and depth to continue...");

        // Initialize the room
        Room room = null;
        while (room == null) {
            try {
                room = robotService.initializeRoom(scanner.nextLine());
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Try again...");
            }
        }

        // Place the robot
        System.out.println("Insert desired Robot placement - width, depth and what direction it should face");
        Robot robot = null;
        while (robot == null) {
            try {
                robot = robotService.initializeRobotPosition(scanner.nextLine(), room);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Try again...");
            }
        }

        // Move the robot
        System.out.println("Where do you want to go? L = Left, R = Right, F = Forward");
        Robot movedRobot = null;
        while (movedRobot == null) {
            try {
                movedRobot = robotService.moveRobot(scanner.nextLine(), robot, room);
                System.out.println(MessageFormat.format("Robot moved to {0} {1} {2}",
                        movedRobot.getWidth(), movedRobot.getDepth(), movedRobot.getDirection()));
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Try again...");
            }
        }

        System.out.println("Robot Commander Out!");
        scanner.close();
    }
}
