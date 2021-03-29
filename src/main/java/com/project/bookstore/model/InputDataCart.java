package com.project.bookstore.model;

import java.util.List;

public class InputDataCart {
	private String userID;
	private List<EntityOrderDetail> itemList;

	public InputDataCart() {
	}

	public InputDataCart(String userId, List<EntityOrderDetail> itemList) {
		this.userID = userId;
		this.itemList = itemList;
	}

	public String getUserId() {
		return userID;
	}

	public List<EntityOrderDetail> getItemList() {
		return itemList;
	}

	public void setUserId(String userId) {
		this.userID = userId;
	}

	public void setItemList(List<EntityOrderDetail> itemList) {
		this.itemList = itemList;
	}

}
