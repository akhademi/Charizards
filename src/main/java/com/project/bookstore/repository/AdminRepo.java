package com.project.bookstore.repository;

import com.project.bookstore.controller.UserCtrl;
import com.project.bookstore.miscellaneous.Util;
import com.project.bookstore.model.BooksSoldModel;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AdminRepo {

	Logger log = LoggerFactory.getLogger(UserCtrl.class);

	@Autowired
	private EntityManager entityManager;
	private Session getSession() {
		return entityManager.unwrap(Session.class);
	}

	@Transactional
	public List<BooksSoldModel> returnBooksSold() {
		try {
			Session session = getSession();
			
			Query<?> query = session.createNativeQuery("with summed as " +
					"    (select od.bid as bid, sum(od.quantity) as quantity from ORDER_DETAIL od " +
					"    join BOOK B on od.BID = B.BID" +
					"    left join ORDER O on O.order_id = od.order_id" +
					"    where O.status = 1" +
					"    group by od.bid) " +
					"select S.bid, B.title, B.price, S.quantity from summed S " +
					"join BOOK B on S.bid = B.bid ");
			
			List<Object[]> itemList = (List<Object[]>) query.getResultList();
			List<BooksSoldModel> books = new ArrayList<>();
			
			for (Object[] bookData: itemList) {
				BooksSoldModel item = new BooksSoldModel();
				
				item.setBid((int) bookData[0]);
				item.setTitle(String.valueOf(bookData[1]));
				item.setPrice(Util.roundDouble((Double) bookData[2]));
				BigInteger temp = new BigInteger(String.valueOf(bookData[3]));
				item.setQuantity(temp.intValue());
				
				books.add(item);
			}
			
			return books;
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	@Transactional
	public List<BooksSoldModel> topSoldBooks() {
		try {
			Session session = getSession();
			
			Query<?> query = session.createNativeQuery("with summed as " +
					"    (select od.bid as bid, sum(od.quantity) as quantity from ORDER_DETAIL od " +
					"    join BOOK B on od.BID = B.BID" +
					"    left join ORDER O on O.order_id = od.order_id" +
					"    where O.status = 1" +
					"    group by od.bid) " +
					"select S.bid, B.title, S.quantity from summed S " +
					"join BOOK B on S.bid = B.bid " +
					"order by S.quantity desc limit 10");
			
			List<Object[]> itemList = (List<Object[]>) query.getResultList();
			List<BooksSoldModel> books = new ArrayList<>();
			
			for (Object[] bookData: itemList) {
				BooksSoldModel item = new BooksSoldModel();
				
				item.setBid((int) bookData[0]);
				item.setTitle(String.valueOf(bookData[1]));
				BigInteger temp = new BigInteger(String.valueOf(bookData[2]));
				item.setQuantity(temp.intValue());
				
				books.add(item);
			}
			
			return books;
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

}
