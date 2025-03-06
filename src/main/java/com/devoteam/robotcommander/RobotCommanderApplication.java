package com.devoteam.robotcommander;

import com.devoteam.robotcommander.services.RobotCommander;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Scanner;

@SpringBootApplication
public class RobotCommanderApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(RobotCommanderApplication.class, args);

		RobotCommander robotCommander = context.getBean(RobotCommander.class);
		robotCommander.run();
	}

}
