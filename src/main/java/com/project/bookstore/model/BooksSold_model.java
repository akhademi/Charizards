package com.project.bookstore.model;

public class BooksSold_model {
// Book ID
	private int book_id;
// Book title
	private String book_title;
// Price of the book
	private double book_price;
// Book Quantities want to buy
	private int book_quantity;

	public BooksSold_model() {
	}

	public BooksSold_model(int bid, String title, double price, int quantity) {
		this.book_id = bid;
		this.book_title = title;
		this.book_price = price;
		this.book_quantity = quantity;
	}

	public int getBid() {
		return book_id;
	}

	public String getTitle() {
		return book_title;
	}

	public double getPrice() {
		return book_price;
	}

	public int getQuantity() {
		return book_quantity;
	}

	public void setBid(int bid) {
		this.book_id = bid;
	}

	public void setTitle(String title) {
		this.book_title = title;
	}

	public void setPrice(double price) {
		this.book_price = price;
	}

	public void setQuantity(int quantity) {
		this.book_quantity = quantity;
	}

}
