package com.project.bookstore.controller;

import com.project.bookstore.miscellaneous.*;
import com.project.bookstore.service.ServiceAdmin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminCtrl {

	@Autowired
	private ServiceAdmin adminService;

	Logger log = LoggerFactory.getLogger(BookCtrl.class);

	// generate table of books sold and their info (ie. price, quantity sold)
	@GetMapping("/generateReport")
	public String generateReport(@RequestParam String userID) {
		if (StringUtils.isEmpty(userID)) {
			return Util.getJsonResponse(FrontEndComs.RESULT_UNKNOWN_ERROR, userID);
		}
		
		try {
			return adminService.generateReport(userID);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return Util.getJsonResponse(FrontEndComs.RESULT_UNKNOWN_ERROR, "admin error");
		}
	}

	// return top 10 sold books
	@GetMapping("/getTopSold")
	public String getTop10(@RequestParam String userID) {
		if (StringUtils.isEmpty(userID)) {
			return Util.getJsonResponse(FrontEndComs.RESULT_UNKNOWN_ERROR, userID);
		}
		
		try {
			return adminService.getTopSoldBooks(userID);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return Util.getJsonResponse(FrontEndComs.RESULT_UNKNOWN_ERROR, "admin error");
		}
	}
}
