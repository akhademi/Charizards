package com.project.bookstore.miscellaneous;

import com.project.bookstore.controller.UserCtrl;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;

import javax.xml.bind.DatatypeConverter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.MessageDigest;

public class Util {

	static Logger log = LoggerFactory.getLogger(UserCtrl.class);


	public static String getJsonResponse(int statusCode, @Nullable String userID) {
		JSONObject json = new JSONObject();

		switch (statusCode) {
		case ErrorCodes.INVALID_USER_SIGNUP_DATA:
			json.put("status", FrontEndComs.RESPONSE_FAIL);
			json.put("message", "User info is invalid. Please try again.");
			break;
		case ErrorCodes.RESULT_USER_ALREADY_EXISTS:
			json.put("status", FrontEndComs.RESPONSE_FAIL);
			json.put("message", "User with this email already exists. Please enter a different email or try logging in.");
			break;
		case ErrorCodes.RESULT_INVALID_CREDENTIALS:
			json.put("status", FrontEndComs.RESPONSE_FAIL);
			json.put("message", "Invalid email or password. Please try again.");
			break;
		case ErrorCodes.RESULT_USER_DOES_NOT_EXIST:
			json.put("status", FrontEndComs.RESPONSE_FAIL);
			json.put("message", "User does not exist. Please enter a valid email or signup.");
			break;
		case ErrorCodes.RESULT_INVALID_DATA:
			json.put("status", FrontEndComs.RESPONSE_FAIL);
			json.put("message", "Invalid data. Please try again.");
			break;
		case FrontEndComs.RESULT_UNKNOWN_ERROR:
			json.put("status", FrontEndComs.RESPONSE_FAIL);
			json.put("message", "Unknown error. Please try again.");
			break;
		}

		if(userID != null) {
			json.put("user", userID);
		}

		return json.toString(4);
	}

	/**
	 * Hashing with SHA1
	 *
	 * @param input String to hash
	 * @return String hashed
	 */
	@Deprecated
	public static String sha1(String input) {
		String sha1 = null;
		try {
			MessageDigest msdDigest = MessageDigest.getInstance("SHA-1");
			msdDigest.update(input.getBytes("UTF-8"), 0, input.length());
			sha1 = DatatypeConverter.printHexBinary(msdDigest.digest());
		} catch (Exception e) {
			log.error("Error hashing password.");
		}
		
		return sha1;
	}


	public static double roundDouble(double value) {
		BigDecimal bd = BigDecimal.valueOf(value);
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}


}
