package com.priyank.shareAnalyser.service;

import java.io.IOException;
import java.util.Map;

import com.priyank.shareAnalyser.model.Company;

public interface DataReaderService {
	Map<Integer, Company> readShareFile(String fileName) throws IOException;

	Map<Integer, Company> getCompanyMap();
}
