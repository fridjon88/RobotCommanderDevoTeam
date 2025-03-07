package com.devoteam.robotcommander.services;

import com.devoteam.robotcommander.DTOs.Robot;
import com.devoteam.robotcommander.DTOs.Room;

public interface RobotService {
    Room initializeRoom(String s);

    Robot initializeRobotPosition(String s, Room room);

    Robot moveRobot(String s, Robot robot, Room room);
}
