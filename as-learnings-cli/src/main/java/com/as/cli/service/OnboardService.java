package com.as.cli.service;

import java.util.List;

public interface OnboardService {

	void onboardChoice(String brand, String pid, List<String> rates, Integer los, Integer maxnrcs);
}
