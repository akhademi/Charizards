package com.project.bookstore.repository;

import com.project.bookstore.controller.UserCtrl;
import com.project.bookstore.miscellaneous.OrderState;
import com.project.bookstore.model.*;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Repository
public class OrderRepo {

	Logger log = LoggerFactory.getLogger(UserCtrl.class);
	
	@Autowired
	private EntityManager entityManager;
	private Session getSession(){
		return entityManager.unwrap(Session.class);
	}

	// return cart of specified user ID
	@Transactional
	public EntityOrder findCartByID(String userID) {
		try {
			Session session = getSession();
			Query<?> query = session.createNativeQuery("SELECT * FROM ORDER WHERE USER_ID = :userID AND (STATUS = :status) limit 1").addEntity(EntityOrder.class);
			query.setParameter("userId", userID);
			query.setParameter("status", OrderState.OrderStatus.IN_CART.getValue());
			return (EntityOrder)query.getSingleResult();
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
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
