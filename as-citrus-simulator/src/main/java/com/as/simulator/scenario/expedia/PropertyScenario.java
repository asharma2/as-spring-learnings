package com.as.simulator.scenario.expedia;

import org.citrusframework.simulator.scenario.AbstractSimulatorScenario;
import org.citrusframework.simulator.scenario.Scenario;
import org.citrusframework.simulator.scenario.ScenarioDesigner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Scenario("EXPEDIA_LIST_OF_PROPERTIES")
@RequestMapping(value = "/services/rest/expedia/products/properties", method = RequestMethod.GET)
public class PropertyScenario extends AbstractSimulatorScenario {

	@Override
	public void run(ScenarioDesigner scenario) {

		scenario.http().receive().get();

		scenario.http().send().response(HttpStatus.OK)
				.payload(new ClassPathResource("expedia/list_of_properties.json"));
	}
}
