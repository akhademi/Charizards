package com.project.bookstore.controller;

import com.project.bookstore.miscellaneous.*;
import com.project.bookstore.model.*;
import com.project.bookstore.service.ServiceUser;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserCtrl {

	Logger log = LoggerFactory.getLogger(UserCtrl.class);

	@Autowired
	ServiceUser userService;

	// used to signup a new user
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(@RequestBody String data) {
		log.debug(String.format("Entered /signup with the following data: %s", data));

		try {
			ObjectMapper mapper = new ObjectMapper();
			InputDataUserSignup inputData = mapper.readValue(data, InputDataUserSignup.class);
			EntityUser user = new EntityUser(null, inputData.getFirstName(), inputData.getLastName(),
					inputData.getEmail(), inputData.getPassword(), 0, null);
			
			return userService.singupUser(user);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return Util.getJsonResponse(FrontEndComs.RESULT_UNKNOWN_ERROR, null);
		}

	}

	// login to an existing account
	// ADMIN LOGIN INFO
	// email: admin @admin.com
	// password: admin 123
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@RequestBody String data) {
		log.debug(String.format("Entered /login for: %s", data));

		try {
			ObjectMapper mapper = new ObjectMapper();
			InputDataUserLogin inputLogin = mapper.readValue(data, InputDataUserLogin.class);
			
			return userService.loginUser(inputLogin);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return Util.getJsonResponse(FrontEndComs.RESULT_UNKNOWN_ERROR, null);
		}
	}

	// add address to a user account
	@RequestMapping(value = "/addAddress", method = RequestMethod.POST)
	public String addAddress(@RequestBody String data) {
		log.debug(String.format("Entered /addAddress for: %s", data));

		try {
			ObjectMapper mapper = new ObjectMapper();
			InputDataAddress inputAddress = mapper.readValue(data, InputDataAddress.class);
			
			return userService.addUserAddress(inputAddress);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return Util.getJsonResponse(FrontEndComs.RESULT_UNKNOWN_ERROR, null);
		}
	}

	// get address of a specified user
	@RequestMapping(value = "/getAddress", method = RequestMethod.GET)
	public String getAddress(@RequestParam String userID){
		log.debug(String.format("Entered /getAddress for: %s", userID));

		try {
			return userService.getAddress(userID);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return Util.getJsonResponse(FrontEndComs.RESULT_UNKNOWN_ERROR, null);
		}
	}
}