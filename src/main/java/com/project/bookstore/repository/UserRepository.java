package com.project.bookstore.repository;

import com.project.bookstore.common.WConstants;
import com.project.bookstore.controller.UserController;
import com.project.bookstore.model.EntityAddressModel;
import com.project.bookstore.model.InputDataAddress;
import com.project.bookstore.model.EntityUser;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.UUID;

@Repository
public class UserRepository {

  Logger log = LoggerFactory.getLogger(UserController.class);

  @Autowired
  private EntityManager entityManager;

  private Session getSession(){
    return entityManager.unwrap(Session.class);
  }

  @Transactional
  public int signupUser(EntityUser EntityUser){
    Session session = getSession();
    try{
      Query<?> query = session.createNativeQuery("insert into user(USER_ID, FIRST_NAME, LAST_NAME, USER_TYPE, EMAIL, PASSWORD" +
              ") VALUES (:user_id, :f_name, :l_name, :user_type, :email, :password)");
      query.setParameter("user_id", UUID.randomUUID().toString());
      query.setParameter("f_name", EntityUser.getFirst_name());
      query.setParameter("l_name", EntityUser.getLast_name());
      query.setParameter("user_type", WConstants.UserType.USER.getValue()); // todo: replace this with the correct user_type based on visitor, user, partner...
      query.setParameter("email", EntityUser.getEmail());
      query.setParameter("password", (EntityUser.getPassword()));
      return query.executeUpdate();
    } catch (Exception e){
      log.error(e.getMessage(), e);
      return WConstants.RESULT_UNKNOWN_ERROR;
    }
  }

  @Transactional
  public boolean isUserEmailExist(String email){
    Session session = getSession();
    try{
      Query<?> query = session.createNativeQuery("select * from user where email = :email");
      query.setParameter("email", email);
      return query.getSingleResult() != null;
    } catch (Exception e){
      log.error(e.getMessage(), e);
      return false;
    }
  }

  @Transactional
  public EntityUser findUser(String email){
    Session session = getSession();
    try{
      Query<?> query = session.createNativeQuery("select * from user where EMAIL = :email and (USER_TYPE = :user_type1 or USER_TYPE = :user_type2)").addEntity(EntityUser.class);
      query.setParameter("email", email);
      query.setParameter("user_type1", WConstants.UserType.USER.getValue());
      query.setParameter("user_type2", WConstants.UserType.ADMIN.getValue());
      return (EntityUser)query.getSingleResult();
    } catch (Exception e){
      log.error(e.getMessage(), e);
      return null;
    }
  }

  @Transactional
  public EntityUser findAdminByUserId(String userId){
    Session session = getSession();
    try{
      Query<?> query = session.createNativeQuery("select * from user where USER_ID = :userId and USER_TYPE = :user_type").addEntity(EntityUser.class);
      query.setParameter("userId", userId);
      query.setParameter("user_type", WConstants.UserType.ADMIN.getValue());
      return (EntityUser)query.getSingleResult();
    } catch (Exception e){
      log.error(e.getMessage(), e);
      return null;
    }
  }

  @Transactional
  public EntityUser findUserByUserId(String userId){
    Session session = getSession();
    try{
      Query<?> query = session.createNativeQuery("select * from user where USER_ID = :userId and USER_TYPE = :user_type").addEntity(EntityUser.class);
      query.setParameter("userId", userId);
      query.setParameter("user_type", WConstants.UserType.USER.getValue());
      return (EntityUser)query.getSingleResult();
    } catch (Exception e){
      log.error(e.getMessage(), e);
      return null;
    }
  }

  @Transactional
  public EntityAddressModel getAddress(String userId){
    Session session = getSession();
    try{
      Query<?> query = session.createNativeQuery("select A.* from ADDRESS A join USER U on A.ADDRESS_ID = U.ADDRESS_ID where " +
              "U.USER_ID = :userId and U.USER_TYPE = :userType").addEntity(EntityAddressModel.class);
      query.setParameter("userId", userId);
      query.setParameter("userType", WConstants.UserType.USER.getValue());
      return (EntityAddressModel)query.getSingleResult();
    } catch (Exception e){
      log.error(e.getMessage(), e);
      return null;
    }
  }

  @Transactional
  public int addUserAddress(InputDataAddress data){
    try{
      Session session = getSession();
      UUID uuid = UUID.randomUUID();
      Query<?> query = session.createNativeQuery("insert into ADDRESS (STREET_NO, STREET_NAME, CITY, PROVINCE, COUNTRY, ZIP, PHONE, ADDRESS_ID) " +
              "values (:streetNo, :streetName, :city, :province, :country, :zip, :phone, :uuid)");
      query.setParameter("streetNo", data.getStreetNo());
      query.setParameter("streetName", data.getStreetName());
      query.setParameter("city", data.getCity());
      query.setParameter("province", data.getProvince());
      query.setParameter("country", data.getCountry());
      query.setParameter("zip", data.getZip());
      query.setParameter("phone", data.getPhone());
      query.setParameter("uuid", uuid.toString());
      int res = query.executeUpdate();

      // update user table with the addressId
      if(res == 1){
        query = session.createNativeQuery("update USER set ADDRESS_ID = :uuid where USER_ID = :userId");
        query.setParameter("uuid", uuid.toString());
        query.setParameter("userId", data.getUserId());
        return query.executeUpdate();
      }
      return WConstants.RESULT_UNKNOWN_ERROR;
    } catch (Exception e){
      log.error(e.getMessage(), e);
      return WConstants.RESULT_UNKNOWN_ERROR;
    }
  }

}
