package com.as.simulator;

import org.citrusframework.simulator.http.SimulatorRestAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * https://citrusframework.org/news/2017/09/21/introducing-citrus-simulator/
 */
@SpringBootApplication
public class Simulator extends SimulatorRestAdapter {

	public static void main(String[] args) {
		SpringApplication.run(Simulator.class, args);
	}

}
