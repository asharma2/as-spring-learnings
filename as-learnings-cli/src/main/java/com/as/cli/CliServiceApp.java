package com.as.cli;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import com.as.cli.service.OnboardService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class CliServiceApp implements ApplicationRunner {

	@Autowired
	private OnboardService onboardService;

	public static void main(String[] args) {
		SpringApplication.run(CliServiceApp.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		String filePath = "D:\\documents\\agoda-choice\\onboarding\\RADISSON_2023-09-14.csv";
		Map<String, List<String[]>> pidData = Files.readAllLines(Paths.get(filePath)).stream()
				.filter(x -> !x.startsWith("@")).map(x -> x.split(",")).collect(Collectors.groupingBy(x -> x[1]));

		Map<String, String> maps = new HashMap<>();
		maps.put("SAGOD", "A00");
		maps.put("SAPR1A", "A01");
		maps.put("SAPR2A", "A02");
		maps.put("SP7AG", "AP7");
		maps.put("SP9AG", "AP9");
		maps.put("SF1AG", "AF1");
		maps.put("LOPQ", "A03");
		maps.put("LOPQ2", "A04");
		maps.put("LOPQ3", "A05");
		maps.put("LNET2", "A06");
		maps.put("SP1AG", "A07");
		maps.put("SP2AG", "A08");
		maps.put("SP3AG", "A09");
		maps.put("SP4AG", "A10");
		maps.put("SP5AG", "A11");
		maps.put("SP6AG", "A12");
		maps.put("SO2AG", "A13");
		maps.put("SO3AG", "A14");
		maps.put("SO1AG", "A16");
		maps.put("PKBBA", "A22");
		maps.put("SASFRT", "A15");

		pidData.forEach((pid, data) -> {
			List<String> rates = data.stream().filter(x -> maps.containsKey(x[2])).map(x -> maps.get(x[2]))
					.collect(Collectors.toList());
			Integer los = data.stream().map(x -> Integer.parseInt(x[3])).findFirst().get();
			Integer nrcs = data.stream().map(x -> Integer.parseInt(x[6])).findFirst().get();
			String brand = data.stream().map(x -> x[0]).findFirst().get();
			onboardService.onboardChoice(brand, pid, rates, los, nrcs);
		});

	}
}
