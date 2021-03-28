package com.project.bookstore.model;

public class CartItem {
	private int bookID;
	private String bookTitle;
	private double bookPrice;
	private String image;
	private int quantity;
	private double amount;

	public int getBid() {
		return bookID;
	}

	public String getTitle() {
		return bookTitle;
	}

	public double getPrice() {
		return bookPrice;
	}

	public String getImage() {
		return image;
	}

	public int getQuantity() {
		return quantity;
	}

	public double getAmount() {
		return amount;
	}

	public void setBid(int bid) {
		this.bookID = bid;
	}

	public void setTitle(String title) {
		this.bookTitle = title;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setPrice(double price) {
		this.bookPrice = price;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
}
