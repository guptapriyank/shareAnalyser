package com.priyank.shareAnalyser.service;

import com.priyank.shareAnalyser.model.Company;
import com.priyank.shareAnalyser.model.HighestValueResult;

public interface CalculateService {
	HighestValueResult getHighestPriceYearAndMonth(Company company);
}
