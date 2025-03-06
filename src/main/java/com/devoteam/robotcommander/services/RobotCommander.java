package com.devoteam.robotcommander.services;

import com.devoteam.robotcommander.DTOs.Room;
import org.springframework.stereotype.Component;

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
        System.out.println("Insert desired width and depth to continue...");

        Room room = null;
        while (room == null) {
            try {
                room = robotService.initializeRoom(scanner.nextLine());
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Try again...");
            }
        }

        System.out.println("Robot Commander Out!");
        scanner.close();
    }
}
