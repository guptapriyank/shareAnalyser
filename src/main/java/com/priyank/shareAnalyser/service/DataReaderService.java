package com.priyank.shareAnalyser.service;

import java.util.Map;

import com.priyank.shareAnalyser.model.Company;

public interface DataReaderService {
	Map<Integer, Company> readShareFile();

	Map<Integer, Company> getCompanyMap();
}
