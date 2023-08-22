package com.as.simulator;

import org.citrusframework.simulator.http.SimulatorRestAdapter;
import org.citrusframework.simulator.http.SimulatorRestConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;

import com.consol.citrus.endpoint.EndpointAdapter;
import com.consol.citrus.endpoint.adapter.StaticEndpointAdapter;
import com.consol.citrus.http.message.HttpMessage;
import com.consol.citrus.message.Message;

/**
 * https://citrusframework.org/news/2017/09/21/introducing-citrus-simulator/
 */
@SpringBootApplication
public class Simulator extends SimulatorRestAdapter {

	public static void main(String[] args) {
		SpringApplication.run(Simulator.class, args);
	}

	@Override
	public String urlMapping(SimulatorRestConfigurationProperties simulatorRestConfiguration) {
		return "/services/demand/v1/**";
	}

	@Override
	public EndpointAdapter fallbackEndpointAdapter() {
		return new StaticEndpointAdapter() {
			@Override
			protected Message handleMessageInternal(Message message) {
				return new HttpMessage().status(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		};
	}
}
