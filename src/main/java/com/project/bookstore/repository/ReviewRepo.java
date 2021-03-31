package com.project.bookstore.repository;

import com.project.bookstore.controller.UserCtrl;
import com.project.bookstore.miscellaneous.FrontEndComs;
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
public class ReviewRepo {

	Logger log = LoggerFactory.getLogger(UserCtrl.class);

	@Autowired
	private EntityManager entityManager;
	private Session getSession() {
		return entityManager.unwrap(Session.class);
	}

	// return reviews of specified book
	public List<EntityReview> getReviews(int bid) {
		Session session = getSession();

		Query<?> query = session.createNativeQuery("SELECT * FROM review WHERE bid = :bid").addEntity(EntityReview.class);
		query.setParameter("bid", bid);

		return (List<EntityReview>) query.getResultList();
	}

	// add review for specified book
	@Transactional
	public int addReview(int bid, String userID, double starRating, String message) {
		Session session = getSession();
		
		try {
			Query<?> query = session.createNativeQuery("INSERT into review (BID, USER_ID, STARS, MESSAGE, USER_NAME) values " +
					"(:bid, :userId, :stars, :message, (SELECT u.FIRST_NAME FROM user u WHERE u.USER_ID = :userID limit 1))");
			query.setParameter("bid", bid);
			query.setParameter("userId", userID);
			query.setParameter("stars", starRating);
			query.setParameter("message", message);
			
			return query.executeUpdate();
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return FrontEndComs.RESULT_UNKNOWN_ERROR;
		}
	}
}
