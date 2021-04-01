package com.project.bookstore.service;

import com.project.bookstore.miscellaneous.*;
import com.project.bookstore.model.*;
import com.project.bookstore.repository.UserRepo;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ServiceUser {

	@Autowired
	UserRepo userRepository;
	@Autowired
	PasswordEncoder encoder;

	public String singupUser(EntityUser EntityUser) {
		JSONObject json = new JSONObject();

		if (!EntityUser.isValid()) {
			return Util.getJsonResponse(ErrorCodes.INVALID_USER_SIGNUP_DATA, null);
		}

		if (userRepository.emailExist(EntityUser.getEmail())) {
			json.put("status", FrontEndComs.RESPONSE_FAIL);
			json.put("message", "User already exists. Please try logging in.");
			return json.toString();
		}

		EntityUser.setPassword(encoder.encode(EntityUser.getPassword()));
		if (userRepository.signupUser(EntityUser) == 1) {
			json.put("status", FrontEndComs.RESPONSE_SUCCESS);
			json.put("message", "Sign up successful for user: " + EntityUser.getEmail());
			return json.toString();
		}

		return Util.getJsonResponse(FrontEndComs.RESULT_UNKNOWN_ERROR, null);
	}

	public String loginUser(InputDataUserLogin data) {
		JSONObject json = new JSONObject();

		EntityUser user = userRepository.getUser(data.getEmail());
		if (user == null) { // if user not found
			return Util.getJsonResponse(ErrorCodes.RESULT_USER_DOES_NOT_EXIST, data.getEmail());
		}
		
		if (!encoder.matches(data.getPassword(), user.getPassword())) { // if user found but incorrect password is entered
			return Util.getJsonResponse(ErrorCodes.RESULT_INVALID_CREDENTIALS, user.getUserID());
		}
		
		json.put("userType", user.getUserType());
		json.put("firstName", user.getFirstName());
		json.put("email", user.getEmail());
		json.put("lastName", user.getLastName());
		json.put("userId", user.getUserID());
		json.put("status", FrontEndComs.RESPONSE_SUCCESS);
		
		return json.toString();
	}

	public boolean isUserExist(String userId) {
		return userRepository.getUserByUserID(userId) != null;
	}

	public boolean isUserAdmin(String adminUserId) { return userRepository.getAdminByUserID(adminUserId) != null; }

	public String addUserAddress(InputDataAddress data) {
		if (!this.isUserExist(data.getUserId())){
			return Util.getJsonResponse(ErrorCodes.RESULT_USER_DOES_NOT_EXIST, data.getUserId());
		}

		if (userRepository.addAddress(data) != 1) {
			Util.getJsonResponse(FrontEndComs.RESULT_UNKNOWN_ERROR, data.getUserId());
		}
		
		JSONObject json = new JSONObject();
		json.put("status", FrontEndComs.RESPONSE_SUCCESS);
		json.put("message", "Address successfully saved.");
		
		return json.toString(4);
	}

	public String getAddress(String userId) {
		if (!this.isUserExist(userId)) {
			return Util.getJsonResponse(ErrorCodes.RESULT_USER_DOES_NOT_EXIST, userId);
		}

		EntityAddressModel address = userRepository.getAddress(userId);
		JSONObject json = new JSONObject();
		
		if (address == null) {
			json.put("status", FrontEndComs.RESPONSE_FAIL);
			json.put("message", "No saved address. Please add one.");
			return json.toString(4);
		}

		json = new JSONObject(address);
		return json.toString(4);

	}

}