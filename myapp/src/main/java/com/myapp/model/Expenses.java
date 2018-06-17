package com.myapp.model;

/**
 * @author Phani
 *
 */
public class Expenses {
    private String id;
    private String date;
    private String description;
    private String Category;
    private String subCategory;
    private Double amount;
    private boolean isArrier;
    private String arrierTo;
    private boolean isBorrowed;
    private String borrowedTo;
    private String remarks;
    private String url;
   
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCategory() {
		return Category;
	}
	public void setCategory(String category) {
		Category = category;
	}
	public String getSubCategory() {
		return subCategory;
	}
	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public boolean isArrier() {
		return isArrier;
	}
	public void setArrier(boolean isArrier) {
		this.isArrier = isArrier;
	}
	public String getArrierTo() {
		return arrierTo;
	}
	public void setArrierTo(String arrierTo) {
		this.arrierTo = arrierTo;
	}
	public boolean isBorrowed() {
		return isBorrowed;
	}
	public void setBorrowed(boolean isBorrowed) {
		this.isBorrowed = isBorrowed;
	}
	public String getBorrowedTo() {
		return borrowedTo;
	}
	public void setBorrowedTo(String borrowedTo) {
		this.borrowedTo = borrowedTo;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "Expenses [id=" + id + ", date=" + date + ", description=" + description + ", Category=" + Category
				+ ", subCategory=" + subCategory + ", amount=" + amount + ", isArrier=" + isArrier + ", arrierTo="
				+ arrierTo + ", isBorrowed=" + isBorrowed + ", borrowedTo=" + borrowedTo + ", remarks=" + remarks
				+ ", url=" + url + "]";
	}
}
