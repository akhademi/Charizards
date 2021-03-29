package com.project.bookstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.bookstore.common.Util;
import com.project.bookstore.common.WConstants;
import com.project.bookstore.service.AdminService;
import org.glassfish.jersey.internal.guava.UnmodifiableIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminCtrl {

	@Autowired
	private AdminService adminService;

	// call BookController class to create book log
	// waiting until other members finish that class
	Logger log;

	@GetMapping("/generateReport")
	public String generateReport(@RequestParam String userId) {
		if (StringUtils.isEmpty(userId)) {
			return Util.getJsonResponse(WConstants.RESULT_UNKNOWN_ERROR, userId);
		}
		try {
			return adminService.generateReport(userId);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return Util.getJsonResponse(WConstants.RESULT_UNKNOWN_ERROR, "admin error");
		}
	}

	@GetMapping("/getTopSold")
	public String getTop10(@RequestParam String userId) {
		if (StringUtils.isEmpty(userId)) {
			return Util.getJsonResponse(WConstants.RESULT_UNKNOWN_ERROR, userId);
		}
		try {
			return adminService.getTopSoldBooks(userId);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return Util.getJsonResponse(WConstants.RESULT_UNKNOWN_ERROR, "admin error");
		}
	}
}
