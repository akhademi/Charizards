package com.project.bookstore.model;

public class InputDataOrderProcessed {
	private String bookTitle;
	private double bookPrice;
	private String userID;
	private int orderID;
	private String orderDate;
	private int quantity;

	public InputDataOrderProcessed() {
	}

	public String getTitle() {
		return bookTitle;
	}

	public double getPrice() {
		return bookPrice;
	}

	public String getUserId() {
		return userID;
	}

	public int getOrderId() {
		return orderID;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setTitle(String title) {
		this.bookTitle = title;
	}

	public void setPrice(double price) {
		this.bookPrice = price;
	}

	public void setUserId(String userId) {
		this.userID = userId;
	}

	public void setOrderId(int orderId) {
		this.orderID = orderId;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public int getQuantity() {
		return quantity;
	}

	public InputDataOrderProcessed(String title, double price, int orderId, String userId, String orderDate) {
		this.bookTitle = title;
		this.bookPrice = price;
		this.orderID = orderId;
		this.userID = userId;
		this.orderDate = orderDate;
	}
}
