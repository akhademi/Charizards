package com.project.bookstore.repository;

import com.project.bookstore.controller.UserCtrl;
import com.project.bookstore.miscellaneous.*;
import com.project.bookstore.model.*;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Repository
public class OrderRepo {

	Logger log = LoggerFactory.getLogger(UserCtrl.class);

	@Autowired
	private EntityManager entityManager;
	private Session getSession() {
		return entityManager.unwrap(Session.class);
	}

	// return cart of specified user ID
	@Transactional
	public EntityOrder findCartByID(String userID) {
		try {
			Session session = getSession();
			Query<?> query = session.createNativeQuery("SELECT * FROM ORDER WHERE USER_ID = :userID AND (STATUS = :status) limit 1").addEntity(EntityOrder.class);
			query.setParameter("userID", userID);
			query.setParameter("status", OrderState.OrderStatus.IN_CART.getValue());

			return (EntityOrder)query.getSingleResult();

		} catch(Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	// add a new cart and if successful, return cart
	@Transactional
	public EntityOrder insertNewCart(String userID) {
		try {
			Session session = getSession();
			Query<?> query = session.createNativeQuery("INSERT INTO ORDER (user_id, status) values(:userID, :status)");
			query.setParameter("userID", userID);
			query.setParameter("status", OrderState.OrderStatus.IN_CART.getValue());

			int res = query.executeUpdate();	// update the query to insert the new cart and its parameters

			// if the parameters were updated successfully (res == 1)
			// this means that the cart was successfully added, and return cart
			if (res == 1) { 
				return findCartByID(userID);
			}

			return null;

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	// return items in specified cart
	@Transactional
	public List<CartItem> returnCartData(int orderID) {
		try {
			Session session = getSession();
			Query<?> query = session.createNativeQuery("SELECT od.bid, B.title, B.images, od.quantity, od.price, (od.quantity * od.price) as amount from " +
					"ORDER_DETAIL od join BOOK B on od.BID = B.BID " +
					"WHERE od.ORDER_ID = :orderID");
			query.setParameter("orderID", orderID);

			List<Object[]> itemList = (List<Object[]>)query.getResultList();
			List<CartItem> cartItems = new ArrayList<>();

			for (Object[] bookData: itemList) {
				CartItem item = new CartItem();

				item.setBid((int) bookData[0]);
				item.setTitle(String.valueOf(bookData[1]));
				item.setImage(String.valueOf(bookData[2]));
				item.setQuantity((int) bookData[3]);
				item.setPrice(Util.roundDouble((Double) bookData[4]));
				item.setAmount(Util.roundDouble((Double) bookData[5]));

				cartItems.add(item);
			}

			return cartItems;

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	// return order of specified book ID (BID)
	@Transactional
	public List<InputDataOrderProcessed> returnOrderByBID(int bid) {
		try {
			Session session = getSession();
			Query<?> query = session.createNativeQuery("SELECT B.title, B.price, " + 
					"OD.QUANTITY, O.* FROM order O " +
					"join ORDER_DETAIL OD on O.ORDER_ID = OD.ORDER_ID " +
					"join BOOK B on OD.BID = B.BID " +
					"WHERE O.STATUS = 1 and B.BID = :bid");
			query.setParameter("bid", bid);

			List<Object[]> itemList = (List<Object[]>)query.getResultList();
			List<InputDataOrderProcessed> orders = new ArrayList<>();

			for (Object[] orderData: itemList){
				InputDataOrderProcessed item = new InputDataOrderProcessed();

				item.setTitle(String.valueOf(orderData[0]));
				item.setPrice(Util.roundDouble((Double) orderData[1]));
				BigInteger temp = new BigInteger(String.valueOf(orderData[2]));
				item.setQuantity(temp.intValue());
				item.setOrderId((int) orderData[3]);
				item.setUserId(String.valueOf(orderData[4]));
				item.setOrderDate(((Date) orderData[5]).toString());

				orders.add(item);
			}

			return orders;

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	// add items to cart
	@Transactional
	public int addItems(int orderID, List<EntityOrderDetail> items) {
		int result = 0;

		try {
			for (EntityOrderDetail orderDetail: items) {
				Session session = getSession();

				// NOTE: if item already exists in cart, increase the quantity instead
				// Otherwise add item as usual
				Query<?> query = session.createNativeQuery("merge into ORDER_DETAIL as od using" + 
						" (values(:orderID, :bid, :quantity, :price)) as data(A, B, C, D) " +
						"on od.ORDER_ID = data.A and od.BID = data.B " +
						"when matched then update set od.QUANTITY = od.QUANTITY + data.C " +
						"when not matched then INSERT values (data.A, data.B, data.C, data.D)");
				query.setParameter("orderID", orderID);
				query.setParameter("bid", orderDetail.getBid());
				query.setParameter("quantity", orderDetail.getQuantity());
				query.setParameter("price", orderDetail.getPrice());

				result = query.executeUpdate();
			}

			return result;

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return FrontEndComs.RESULT_UNKNOWN_ERROR;
		}
	}

	// remove item from cart
	@Transactional
	public int removeItem(int orderID, int bid) {
		int result = 0;

		try {
			Session session = getSession();
			
			// NOTE: If item already exists in cart, decrease the quantity
			Query<?> query = session.createNativeQuery("DELETE FROM ORDER_DETAIL WHERE ORDER_ID = :orderID and BID = :bid");
			query.setParameter("orderID", orderID);
			query.setParameter("bid", bid);
			
			result = query.executeUpdate();
			return result;

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return FrontEndComs.RESULT_UNKNOWN_ERROR;
		}
	}

	// increase quantity of book in cart if same book ordered again
	@Transactional
	public int incSubmitAttempts(String userID, int orderID) {
		int result = 0;
		
		try {
			Session session = getSession();
			
			// increase the number of submit attempts (max is 3 as defined in the Constraints class)
			Query<?> query = session.createNativeQuery("UPDATE ORDER set SUBMIT_ATTEMPTS = SUBMIT_ATTEMPTS + 1 WHERE USER_ID = :userID and ORDER_ID = :orderID");
			query.setParameter("orderID", orderID);
			query.setParameter("userID", userID);
			
			result = query.executeUpdate();
			return result;

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return FrontEndComs.RESULT_UNKNOWN_ERROR;
		}
	}

	// submit order
	@Transactional
	public int submitOrder(String userID, int orderID) {
		int result = 0;

		try {
			Session session = getSession();
			
			// update the status of the order to ORDERED once it has been submitted
			Query<?> query = session.createNativeQuery("UPDATE ORDER set STATUS = :status WHERE USER_ID = :userID and ORDER_ID = :orderID");
			query.setParameter("status", OrderState.OrderStatus.ORDERED.getValue());
			query.setParameter("orderID", orderID);
			query.setParameter("userID", userID);
			
			result = query.executeUpdate();
			return result;

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return FrontEndComs.RESULT_UNKNOWN_ERROR;
		}
	}

}
