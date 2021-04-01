package com.project.bookstore.repository;

import com.project.bookstore.miscellaneous.Util;
import com.project.bookstore.miscellaneous.ErrorCodes;
import com.project.bookstore.controller.UserCtrl;
import com.project.bookstore.model.EntityReview;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.hibernate.query.Query;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class ReviewRepository {

  Logger log = LoggerFactory.getLogger(UserCtrl.class);

  @Autowired
  private EntityManager entityManager;
  private Session getSession(){
    return entityManager.unwrap(Session.class);
  }


  public List<EntityReview> getReviewsForBook(int bid){
    Session session = getSession();
    Query<?> query = session.createNativeQuery("select * from review where bid = :bid").addEntity(EntityReview.class);
    query.setParameter("bid", bid);
    return (List<EntityReview>)query.getResultList();
  }

  @Transactional
  public int addReview(int bid, String userId, double stars, String message){
    Session session = getSession();
    try{
      Query<?> query = session.createNativeQuery("insert into review (BID, USER_ID, STARS, MESSAGE, USER_NAME) values " +
              "(:bid, :userId, :stars, :message, (select u.FIRST_NAME from user u where u.USER_ID = :userId limit 1))");
      query.setParameter("bid", bid);
      query.setParameter("userId", userId);
      query.setParameter("stars", stars);
      query.setParameter("message", message);
      return query.executeUpdate();
    } catch (Exception e){
      log.error(e.getMessage(), e);
      return ErrorCodes.RESULT_UNKNOWN_ERROR;
    }
  }


}
