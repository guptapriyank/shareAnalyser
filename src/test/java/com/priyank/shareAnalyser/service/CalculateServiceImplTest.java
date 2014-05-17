package com.priyank.shareAnalyser.service;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.priyank.shareAnalyser.model.Company;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("/test-context.xml")
public class CalculateServiceImplTest {

	@Autowired
	private DataReaderService dataReaderService;

	@Autowired
	private CalculateService calculateService;

	private Map<Integer, Company> readShareFile;

	@Before
	public void setUp() throws IOException {
		readShareFile = dataReaderService.readShareFile("shareData");
	}

	@Test
	public void testGetHighestPriceYearAndMonth() {
		Company companyA = readShareFile.get(0);
		assertEquals("Company A highest price should be 11", calculateService
				.getHighestPriceYearAndMonth(companyA).getHighestValue()
				.intValue(), 11);
		assertEquals("Company A highest price year month count should be 12",
				calculateService.getHighestPriceYearAndMonth(companyA)
						.getYearMonths().size(), 12);
		Company companyC = readShareFile.get(2);
		assertEquals("Company C highest price should be 24", calculateService
				.getHighestPriceYearAndMonth(companyC).getHighestValue()
				.intValue(), 24);
		assertEquals("Company C highest price year month count should be 1",
				calculateService.getHighestPriceYearAndMonth(companyC)
						.getYearMonths().size(), 1);
		assertEquals("Company C highest price year should be 1990",
				calculateService.getHighestPriceYearAndMonth(companyC)
						.getYearMonths().get(0).getYear(), 1990);
		assertEquals("Company C highest price month should be Nov",
				calculateService.getHighestPriceYearAndMonth(companyC)
						.getYearMonths().get(0).getMonth(), "Nov");
	}
}
