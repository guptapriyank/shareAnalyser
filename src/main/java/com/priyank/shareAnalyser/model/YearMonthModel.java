package com.priyank.shareAnalyser.model;

public class YearMonthModel {

	private int year;
	private String month;

	public YearMonthModel(int year, String month) {
		this.year = year;
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	@Override
	public String toString() {
		return "YearMonthModel [year=" + year + ", month=" + month + "]";
	}

}
