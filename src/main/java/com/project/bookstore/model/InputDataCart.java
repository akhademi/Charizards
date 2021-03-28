package com.project.bookstore.model;

import java.util.List;

public class InputDataCart {
	private String userID;
	private List<OrderDetailEntity> itemList;

	public InputDataCart() {
	}

	public InputDataCart(String userId, List<OrderDetailEntity> itemList) {
		this.userID = userId;
		this.itemList = itemList;
	}

	public String getUserId() {
		return userID;
	}

	public List<OrderDetailEntity> getItemList() {
		return itemList;
	}

	public void setUserId(String userId) {
		this.userID = userId;
	}

	public void setItemList(List<OrderDetailEntity> itemList) {
		this.itemList = itemList;
	}

}
