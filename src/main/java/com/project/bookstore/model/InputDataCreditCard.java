package com.project.bookstore.model;

public class InputDataCartItem {

	private String userID;
	private int bookID;
	private int quantity;
	private double price;

	public InputDataCartItem() {
	}

	public InputDataCartItem(String userId, int bid, int quantity, double price) {
		this.userID = userId;
		this.bookID = bid;
		this.quantity = quantity;
		this.price = price;
	}

	public String getUserId() {
		return userID;
	}

	public int getBid() {
		return bookID;
	}

	public int getQuantity() {
		return quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setUserId(String userId) {
		this.userID = userId;
	}

	public void setBid(int bid) {
		this.bookID = bid;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}
