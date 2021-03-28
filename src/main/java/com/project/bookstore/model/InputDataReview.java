package com.project.bookstore.model;

public class InputDataReview {
	private int bookID;
	private String userID;
	private double stars;
	private String message;

	public InputDataReview() {
	}

	public int getBid() {
		return bookID;
	}

	public String getUserId() {
		return userID;
	}

	public double getStars() {
		return stars;
	}

	public String getMessage() {
		return message;
	}

	public void setBid(int bid) {
		this.bookID = bid;
	}

	public void setUserId(String userId) {
		this.userID = userId;
	}

	public void setStars(double stars) {
		this.stars = stars;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
