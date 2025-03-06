package com.devoteam.robotcommander.services;

import com.devoteam.robotcommander.DTOs.Room;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
public class RobotServiceImpl implements RobotService {

    public Room initializeRoom(String values) {
        String[] s = values.split(" ");
        if (s.length != 2) {
            throw new IllegalArgumentException("Invalid number of arguments");
        }

        long width = 0;
        long depth = 0;
        try {
            width = Long.parseLong(s[0]);
            depth = Long.parseLong(s[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(MessageFormat.format("Your input is not supported - Width:{0} and Depth:{1}", width, depth));
        }

        if (width <= 0 || depth <= 0) {
            throw new IllegalArgumentException(MessageFormat.format("Your input is not supported - Width:{0} and Depth:{1}", width, depth));
        }

        return new Room(width, depth);
    }
}
