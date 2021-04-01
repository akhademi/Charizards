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
			Query<?> query = session.createNativeQuery("INSERT into user(USERID, FIRSTNAME, LASTNAME, USERTYPE, EMAIL, PASSWORD" +
					") VALUES (:userID, :firstName, :lastName, :userType, :email, :password)");
			query.setParameter("userID", UUID.randomUUID().toString());
			query.setParameter("firstName", user.getFirstName());
			query.setParameter("lastName", user.getLastName());
			query.setParameter("userType", Users.UserType.CUSTOMER.getValue());
			query.setParameter("email", user.getEmail());
			query.setParameter("password", (user.getPassword()));

			return query.executeUpdate();

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return FrontEndComs.RESULT_UNKNOWN_ERROR;
		}
	}

	// get user linked to specified email
	@Transactional
	public EntityUser getUser(String email) {
		Session session = getSession();

		try {
			Query<?> query = session.createNativeQuery("SELECT * FROM user WHERE EMAIL = :email and (USERTYPE = :user_type1 or USER_TYPE = :user_type2)").addEntity(EntityUser.class);
			query.setParameter("email", email);
			query.setParameter("user_type1", Users.UserType.CUSTOMER.getValue());
			query.setParameter("user_type2", Users.UserType.ADMIN.getValue());

			return (EntityUser)query.getSingleResult();

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	// get user based on specified user ID
	@Transactional
	public EntityUser getUserByUserID(String userID) {
		Session session = getSession();
		
		try {
			Query<?> query = session.createNativeQuery("SELECT * FROM user WHERE USERID = :userID and USERTYPE = :userType").addEntity(EntityUser.class);
			query.setParameter("userID", userID);
			query.setParameter("userType", Users.UserType.CUSTOMER.getValue());
			
			return (EntityUser) query.getSingleResult();
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	// get admin based on specified user ID
	@Transactional
	public EntityUser getAdminByUserID(String userID) {
		Session session = getSession();

		try {
			Query<?> query = session.createNativeQuery("SELECT * FROM user WHERE USERID = :userID and USERTYPE = :userType").addEntity(EntityUser.class);
			query.setParameter("userID", userID);
			query.setParameter("userType", Users.UserType.ADMIN.getValue());

			return (EntityUser) query.getSingleResult();

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	// add a new address
	@Transactional
	public int addAddress(InputDataAddress addressInfo) {
		try {
			Session session = getSession();

			UUID uuid = UUID.randomUUID();
			Query<?> query = session.createNativeQuery("INSERT INTO ADDRESS (STREET_NUMBER, STREET_NAME, CITY, PROVINCE, COUNTRY, ZIP_CODE, PHONE_NUMBER, ADDRESS_ID) " +
					"values (:streetNumber, :streetName, :city, :province, :country, :zip, :phone, :uuid)");
			query.setParameter("streetNumber", addressInfo.getStreeNumber());
			query.setParameter("streetName", addressInfo.getStreetName());
			query.setParameter("city", addressInfo.getCity());
			query.setParameter("province", addressInfo.getProvince());
			query.setParameter("country", addressInfo.getCountry());
			query.setParameter("zip", addressInfo.getZipCode());
			query.setParameter("phone", addressInfo.getPhoneNumber());
			query.setParameter("uuid", uuid.toString());

			int result = query.executeUpdate();

			// update user table with the address ID
			if (result == 1) {
				query = session.createNativeQuery("UPDATE USER SET ADDRESSID = :uuid WHERE USERID = :userID");
				query.setParameter("uuid", uuid.toString());
				query.setParameter("userID", addressInfo.getUserId());

				return query.executeUpdate();
			}

			return FrontEndComs.RESULT_UNKNOWN_ERROR;

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return FrontEndComs.RESULT_UNKNOWN_ERROR;
		}
	}

	// get address linked to specified user ID
	@Transactional
	public EntityAddressModel getAddress(String userID) {
		Session session = getSession();

		try {
			Query<?> query = session.createNativeQuery("SELECT A.* FROM ADDRESS A join USER U on A.ADDRESS_ID = U.ADDRESS_ID WHERE " +
					"U.USERID = :userID and U.USERTYPE = :userType").addEntity(EntityAddressModel.class);
			query.setParameter("userID", userID);
			query.setParameter("userType", Users.UserType.CUSTOMER.getValue());

			return (EntityAddressModel)query.getSingleResult();

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	// method to check if an email exists within the database
	// this means that the email is already being used for an account
	@Transactional
	public boolean emailExist(String email) {
		Session session = getSession();

		try {
			Query<?> query = session.createNativeQuery("SELECT * FROM user WHERE email = :email");
			query.setParameter("email", email);

			return query.getSingleResult() != null;

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return false;
		}
	}

}
