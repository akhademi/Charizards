package com.project.bookstore.repository;

import com.project.bookstore.model.*;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderRepo {
	
	// return cart of specified user ID
	public EntityOrder findCartByID(String userID) {
		return null;
	}
	
	// add a new cart and if successful, return cart
	public EntityOrder insertNewCart(String userID) {
		return null;
	}
	
	// return items in specified cart
	public List<CartItem> returnCartData(int orderID) {
		return null;
	}
	
	// return order of specified book ID (BID)
	public List<InputDataOrderProcessed> returnOrderByBID(int bid) {
		return null;
	}
	
	// add items to cart
	public int addItems(int orderID, List<EntityOrderDetail> items) {
		return 0;
	}
		
	// remove item from cart
	public int removeItem(int orderID, int bid) {
		return 0;
	}
	
	// increase quantity of book in cart if same book ordered again
	public int incSubmitAttempts(String userID, int orderID) {
		return 0;
	}
	
	// submit order
	public int submitOrder(String userId, int orderId) {
		return 0;
	}
	
}
