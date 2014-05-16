package com.priyank.shareAnalyser.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.priyank.shareAnalyser.model.Company;
import com.priyank.shareAnalyser.model.HighestValueResult;
import com.priyank.shareAnalyser.service.CalculateService;
import com.priyank.shareAnalyser.service.DataReaderService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	@Autowired
	private DataReaderService dataReaderService;

	@Autowired
	private CalculateService calculateService;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() throws Exception {
		Map<Integer, Company> readShareFile = dataReaderService.readShareFile();
		System.out.println(readShareFile);
		return "home";
	}

	@RequestMapping(value = "/getAllStocks", method = RequestMethod.GET)
	public @ResponseBody
	List<Company> getAllStocks() throws Exception {
		List<Company> companyList = new ArrayList<Company>(dataReaderService
				.getCompanyMap().values());
		return companyList;
	}

	@RequestMapping(value = "/getAllStocks/{stockId}/highest", method = RequestMethod.GET)
	public @ResponseBody
	HighestValueResult getStock(@PathVariable("stockId") Integer stockId)
			throws Exception {
		Map<Integer, Company> companyMap = dataReaderService.getCompanyMap();
		Company company = companyMap.get(stockId);
		return calculateService.getHighestPriceYearAndMonth(company);
	}

}
