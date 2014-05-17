package com.priyank.shareAnalyser.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.priyank.shareAnalyser.model.Company;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("/test-context.xml")
public class DataReaderServiceImplTest {

	@Autowired
	private DataReaderService dataReaderService;

	@Before
	public void setUp() throws IOException {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void readShareFileTest() throws IOException {
		Map<Integer, Company> readShareFile = dataReaderService
				.readShareFile("shareData");
		assertNotNull("ReadShare object should not be null", readShareFile);
		assertEquals("Number of company should be 5", readShareFile.size(), 5);
		assertEquals("First Company name should be : 'Company A' ",
				readShareFile.get(0).getName(), "Company A");
		assertEquals("No of year should be only 2 as 1990 and 1991",
				readShareFile.get(0).getPricePerMonthPerYearMap().size(), 2);
		assertEquals("No. of months in 1990 year should be 12", readShareFile
				.get(0).getPricePerMonthPerYearMap().get(1990).size(), 12);
		assertEquals("No. of months in 1991 year should be 1", readShareFile
				.get(0).getPricePerMonthPerYearMap().get(1991).size(), 1);
		assertEquals("Share file for Company A in 1990 Jan should be 10",
				readShareFile.get(0).getPricePerMonthPerYearMap().get(1990)
						.get("Jan").intValue(), 10);
	}

	@Test
	public void getCompanyMapTest() throws IOException {
		Map<Integer, Company> readShareFile = dataReaderService
				.readShareFile("shareData");
		assertNotNull("ReadShare object should not be null",
				dataReaderService.getCompanyMap());
		assertEquals(
				"ReadShare object returned by readShareFile method should be equal to the object returned by getCompanyMap method",
				readShareFile, dataReaderService.getCompanyMap());
	}

	@Test(expected = FileNotFoundException.class)
	public void readShareFileFileNotFoundTest() throws IOException {
		dataReaderService.readShareFile("shareDataNoExists");
	}

	@Test(expected = NumberFormatException.class)
	public void readShareFileTNumberFormatTest() throws IOException {
		dataReaderService.readShareFile("shareDataNumberFormat");
	}

}
