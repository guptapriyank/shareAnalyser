package com.priyank.shareAnalyser.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Component;

import com.priyank.shareAnalyser.model.Company;
import com.priyank.shareAnalyser.model.HighestValueResult;
import com.priyank.shareAnalyser.model.YearMonthModel;

@Component
public class CalculateServiceImpl implements CalculateService {

	@Override
	public HighestValueResult getHighestPriceYearAndMonth(Company company) {
		Long tempHighestValue = 0l;
		ArrayList<Long> tempList = null;
		List<YearMonthModel> yearMonths = new ArrayList<YearMonthModel>();
		Comparator<Long> comparator = Collections.reverseOrder();
		Map<Integer, Map<String, Long>> pricePerMonthPerYearMap = company
				.getPricePerMonthPerYearMap();
		for (Map.Entry<Integer, Map<String, Long>> pricePerMonthPerYearEntry : pricePerMonthPerYearMap
				.entrySet()) {
			tempList = new ArrayList<Long>(pricePerMonthPerYearEntry.getValue()
					.values());
			Collections.sort(tempList, comparator);
			if (tempList.get(0) > tempHighestValue) {
				tempHighestValue = tempList.get(0);
				yearMonths.clear();
			}
			for (Entry<String, Long> entry : pricePerMonthPerYearEntry
					.getValue().entrySet()) {
				if (entry.getValue() == tempHighestValue) {
					yearMonths.add(new YearMonthModel(pricePerMonthPerYearEntry
							.getKey(), entry.getKey()));
				}
			}
		}

		return new HighestValueResult(tempHighestValue, yearMonths);
	}
}
