package com.priyank.shareAnalyser.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Company implements Serializable {
	private long id;
	private String name;
	private Map<Integer, Map<String, Long>> pricePerMonthPerYearMap = null;

	public Company(long id, String name) {
		this.id = id;
		this.name = name;
		pricePerMonthPerYearMap = new HashMap<Integer, Map<String, Long>>();
	}

	public Map<String, Long> getpricePerMonthsForYear(int year) {
		Map<String, Long> pricePerMonthMap = pricePerMonthPerYearMap.get(year);
		if (pricePerMonthMap == null) {
			pricePerMonthMap = new HashMap<String, Long>();
			pricePerMonthPerYearMap.put(year, pricePerMonthMap);
		}
		return pricePerMonthMap;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<Integer, Map<String, Long>> getPricePerMonthPerYearMap() {
		return pricePerMonthPerYearMap;
	}

	public void setPricePerMonthPerYearMap(
			Map<Integer, Map<String, Long>> pricePerMonthPerYearMap) {
		this.pricePerMonthPerYearMap = pricePerMonthPerYearMap;
	}

	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name
				+ ", pricePerMonthPerYearMap=" + pricePerMonthPerYearMap + "]";
	}

}
