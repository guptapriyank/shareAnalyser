/**
 * 
 */
package com.priyank.shareAnalyser.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.priyank.shareAnalyser.model.Company;

/**
 * @author priyank
 * 
 */
@Service
public class DataReaderServiceImpl implements DataReaderService {

	private Map<Integer, Company> companyMap = new HashMap<Integer, Company>();

	@Autowired
	private ApplicationContext appContext;

	/**
	 * Read file from resource
	 */
	@Override
	public Map<Integer, Company> readShareFile(String fileName)
			throws IOException {
		Resource resource = appContext.getResource("classpath:" + fileName
				+ ".txt");
		String line = "";
		InputStream inputStream = resource.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(
				inputStream));
		boolean header = true;
		while ((line = br.readLine()) != null) {
			String[] data = line.split(",");
			if (header) {
				createCompanyProfile(companyMap, data);
				header = false;
			} else {
				persistCompanyData(companyMap, data);
			}
		}
		br.close();
		return companyMap;
	}

	/**
	 * @param companyMap
	 * @param companies
	 * @param data
	 */
	private void createCompanyProfile(Map<Integer, Company> companyMap,
			String[] data) {
		String companyString;
		for (int i = 0; i < data.length; i++) {
			if (i > 1) {
				companyString = data[i].trim();
				companyMap.put(i - 2, new Company(i - 2, companyString));
			}
		}
	}

	/**
	 * @param companyMap
	 * @param data
	 * @throws NumberFormatException
	 */
	private void persistCompanyData(Map<Integer, Company> companyMap,
			String[] data) throws NumberFormatException {
		Company company;
		int year = Integer.parseInt(data[0].trim());
		String month = data[1].trim();
		for (int i = 0; i < data.length; i++) {
			if (i > 1) {
				company = companyMap.get(i - 2);
				Map<String, Long> pricePerMonth = company
						.getpricePerMonthsForYear(year);
				pricePerMonth.put(month, Long.parseLong(data[i].trim()));
			}
		}
	}

	@Override
	public Map<Integer, Company> getCompanyMap() {
		return companyMap;
	}
}
