package com.project.bookstore.repository;

import com.project.bookstore.model.*;

import org.springframework.stereotype.Repository;

import java.util.List;

public class UserRepo {

	public int signupUser(EntityUser user) {
		return 0;
	}
	
	public EntityUser getUser(String email) {
		return null;
	}
	
	public EntityUser getAdminByUserID(String userID) {
		return null;
	}
	
	public int addAddress(InputDataAddress addressInfo) {
		return 0;
	}
	
	public EntityAddressModel getAddress(String userID) {
		return null;
	}
	
	public boolean emailExist(String email) {
		return false;
	}
}
