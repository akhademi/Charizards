package com.project.bookstore.controller;

import com.project.bookstore.miscellaneous.*;
import com.project.bookstore.model.EntityBookModel;
import com.project.bookstore.service.ServiceBook;
import com.project.bookstore.service.ServiceOrder;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
public class PartnerCtrl {

	Logger log = LoggerFactory.getLogger(BookCtrl.class);

	@Autowired
	private ServiceBook bookService;
	@Autowired
	private ServiceOrder orderService;

	// returns a JSON string of a book, specified by the book ID (bid)
	@GetMapping(value = "/getProductInfo", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getBookInfo(@RequestParam(name = "bid") int bid) {
		log.debug(String.format("Entered getProductInfo method (REST) for bid: %s", bid));

		JSONObject json = new JSONObject();
		EntityBookModel book = bookService.getBookInfo(bid);

		if (book == null) {
			json.put("status", FrontEndComs.RESPONSE_FAIL);
			json.put("message", "Book not found. Please enter a valid book/product ID.");
			return json.toString(4);
		}

		return new JSONObject(book).toString(4);
	}

	// 
	@GetMapping(value = "/getOrdersByPartNumber", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getOrdersByPartNumber(@RequestParam(name = "bid") int bid) {
		log.debug(String.format("Entered getOrdersByPartNumber method (REST) for bid: %s", bid));
		
		try {
			JSONObject json = new JSONObject();
			EntityBookModel book = bookService.getBookInfo(bid);
			
			if (book == null) {
				json.put("status", FrontEndComs.RESPONSE_FAIL);
				json.put("message", "Order not found. Please enter a valid book/product ID.");
				return json.toString(4);
			}

			return orderService.getOrdersByBid(bid);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return Util.getJsonResponse(FrontEndComs.RESULT_UNKNOWN_ERROR, null);
		}
	}
}
