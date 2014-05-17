package com.priyank.shareAnalyser.controller;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.priyank.shareAnalyser.model.Company;
import com.priyank.shareAnalyser.model.HighestValueResult;
import com.priyank.shareAnalyser.model.YearMonthModel;
import com.priyank.shareAnalyser.service.CalculateService;
import com.priyank.shareAnalyser.service.DataReaderService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("/test-context.xml")
public class HomeControllerTest {

	@Inject
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Mock
	private DataReaderService dataReaderService;

	@Mock
	private CalculateService calculateService;

	@InjectMocks
	@Resource
	private HomeController homeController;

	public MediaType APPLICATION_JSON_UTF8 = new MediaType(
			MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private HighestValueResult highestValueResult;

	private Map<Integer, Company> companyMap = new HashMap<Integer, Company>();

	@Before
	public void setUp() throws IOException {
		MockitoAnnotations.initMocks(this);
		Mockito.when(dataReaderService.readShareFile(Mockito.anyString()))
				.thenReturn(new HashMap<Integer, Company>());
		this.mockMvc = MockMvcBuilders.standaloneSetup(homeController).build();
		populateCompanyMap();
		populateHighestValueResult();
	}

	private void populateCompanyMap() {
		companyMap.put(0, new Company(0, "Company 0"));
		companyMap.put(1, new Company(1, "Company 1"));
		companyMap.put(2, new Company(2, "Company 2"));
		companyMap.put(3, new Company(3, "Company 3"));
		companyMap.put(4, new Company(4, "Company 4"));
	}

	private void populateHighestValueResult() {
		List<YearMonthModel> yearMonthModels = new ArrayList<YearMonthModel>();
		yearMonthModels.add(new YearMonthModel(1990, "Jan"));
		yearMonthModels.add(new YearMonthModel(1990, "Mar"));
		yearMonthModels.add(new YearMonthModel(1990, "Sep"));
		yearMonthModels.add(new YearMonthModel(1991, "Feb"));
		highestValueResult = new HighestValueResult(30L, yearMonthModels);
	}

	@Test
	public void testHome() throws Exception {
		this.mockMvc.perform(get("/")).andExpect(status().isOk())
				.andExpect(view().name("home"));
	}

	@Test
	public void testGetAllStocks() throws Exception {
		Mockito.when(dataReaderService.getCompanyMap()).thenReturn(companyMap);
		this.mockMvc
				.perform(get("/getAllStocks"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$[0].id", is(0)))
				.andExpect(
						jsonPath("$[0].name", is(companyMap.get(0).getName())));
	}

	@Test
	public void testGetStock() throws Exception {
		Mockito.when(
				calculateService.getHighestPriceYearAndMonth(Mockito
						.any(Company.class))).thenReturn(highestValueResult);
		this.mockMvc
				.perform(get("/stocks/0/highest"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(
						jsonPath("$.highestValue", is(highestValueResult
								.getHighestValue().intValue())))
				.andExpect(
						jsonPath("$.yearMonths[0].month", is(highestValueResult
								.getYearMonths().get(0).getMonth())))
				.andExpect(
						jsonPath("$.yearMonths[0].year", is(highestValueResult
								.getYearMonths().get(0).getYear())));
	}

}
