package com.priyank.shareAnalyser.model;

import java.util.List;

public class HighestValueResult {
	private Long highestValue;
	private List<YearMonthModel> yearMonths = null;

	public HighestValueResult(Long highestValue, List<YearMonthModel> yearMonths) {
		this.highestValue = highestValue;
		this.yearMonths = yearMonths;
	}

	public Long getHighestValue() {
		return highestValue;
	}

	public void setHighestValue(Long highestValue) {
		this.highestValue = highestValue;
	}

	public List<YearMonthModel> getYearMonths() {
		return yearMonths;
	}

	public void setYearMonths(List<YearMonthModel> yearMonths) {
		this.yearMonths = yearMonths;
	}

}
