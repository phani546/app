package com.myapp.model;

/**
 * @author Phani
 *
 */
public class Summary {
	private String id;
	private double totalAmount;
	private String month;
	private String year;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return "Summary [id=" + id + ", totalAmount=" + totalAmount + ", month=" + month + ", year=" + year + "]";
	}
	
	

}
