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

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

public class UserRepo {

	Logger log = LoggerFactory.getLogger(UserCtrl.class);

	@Autowired
	private EntityManager entityManager;
	private Session getSession() {
		return entityManager.unwrap(Session.class);
	}

	// method to sign up a new user, this means adding them
	@Transactional
	public int signupUser(EntityUser user) {
		Session session = getSession();

		try {
			Query<?> query = session.createNativeQuery("INSERT into user(USER_ID, FIRST_NAME, LAST_NAME, USER_TYPE, EMAIL, PASSWORD" +
					") VALUES (:user_id, :f_name, :l_name, :user_type, :email, :password)");
			query.setParameter("user_id", UUID.randomUUID().toString());
			query.setParameter("f_name", user.getFirst_name());
			query.setParameter("l_name", user.getLast_name());
			query.setParameter("user_type", Users.UserType.CUSTOMER.getValue());
			query.setParameter("email", user.getEmail());
			query.setParameter("password", (user.getPassword()));

			return query.executeUpdate();

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return FrontEndComs.RESULT_UNKNOWN_ERROR;
		}
	}

	@Transactional
	public EntityUser getUser(String email) {
	    Session session = getSession();
	    
	    try {
	      Query<?> query = session.createNativeQuery("SELECT * FROM user WHERE EMAIL = :email and (USER_TYPE = :user_type1 or USER_TYPE = :user_type2)").addEntity(EntityUser.class);
	      query.setParameter("email", email);
	      query.setParameter("user_type1", Users.UserType.CUSTOMER.getValue());
	      query.setParameter("user_type2", Users.UserType.ADMIN.getValue());
	      
	      return (EntityUser)query.getSingleResult();
	      
	    } catch (Exception e) {
	      log.error(e.getMessage(), e);
	      return null;
	    }
	}

	@Transactional
	public EntityUser getAdminByUserID(String userID) {
	    Session session = getSession();
	    
	    try {
	      Query<?> query = session.createNativeQuery("SELECT * FROM user WHERE USER_ID = :userId and USER_TYPE = :user_type").addEntity(EntityUser.class);
	      query.setParameter("userId", userID);
	      query.setParameter("user_type", Users.UserType.ADMIN.getValue());
	      
	      return (EntityUser)query.getSingleResult();
	      
	    } catch (Exception e) {
	      log.error(e.getMessage(), e);
	      return null;
	    }
	}

	@Transactional
	public int addAddress(InputDataAddress addressInfo) {
		try {
			Session session = getSession();
			
			UUID uuid = UUID.randomUUID();
			Query<?> query = session.createNativeQuery("INSERT into ADDRESS (STREET_NO, STREET_NAME, CITY, PROVINCE, COUNTRY, ZIP, PHONE, ADDRESS_ID) " +
					"values (:streetNo, :streetName, :city, :province, :country, :zip, :phone, :uuid)");
			query.setParameter("streetNo", addressInfo.getStreetNo());
			query.setParameter("streetName", addressInfo.getStreetName());
			query.setParameter("city", addressInfo.getCity());
			query.setParameter("province", addressInfo.getProvince());
			query.setParameter("country", addressInfo.getCountry());
			query.setParameter("zip", addressInfo.getZip());
			query.setParameter("phone", addressInfo.getPhone());
			query.setParameter("uuid", uuid.toString());
			
			int result = query.executeUpdate();

			// update user table with the addressId
			if (result == 1) {
				query = session.createNativeQuery("update USER set ADDRESS_ID = :uuid where USER_ID = :userId");
				query.setParameter("uuid", uuid.toString());
				query.setParameter("userId", addressInfo.getUserId());
				
				return query.executeUpdate();
			}
			
			return FrontEndComs.RESULT_UNKNOWN_ERROR;
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return FrontEndComs.RESULT_UNKNOWN_ERROR;
		}
	}

	@Transactional
	public EntityAddressModel getAddress(String userID) {
	    Session session = getSession();
	    
	    try {
	      Query<?> query = session.createNativeQuery("select A.* from ADDRESS A join USER U on A.ADDRESS_ID = U.ADDRESS_ID where " +
	              "U.USER_ID = :userId and U.USER_TYPE = :userType").addEntity(EntityAddressModel.class);
	      query.setParameter("userId", userID);
	      query.setParameter("userType", Users.UserType.CUSTOMER.getValue());
	      
	      return (EntityAddressModel)query.getSingleResult();
	      
	    } catch (Exception e) {
	      log.error(e.getMessage(), e);
	      return null;
	    }
	}

	@Transactional
	public boolean emailExist(String email) {
	    Session session = getSession();
	    
	    try {
	      Query<?> query = session.createNativeQuery("select * from user where email = :email");
	      query.setParameter("email", email);
	      
	      return query.getSingleResult() != null;
	      
	    } catch (Exception e) {
	      log.error(e.getMessage(), e);
	      return false;
	    }
	}

}
