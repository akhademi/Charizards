package com.project.bookstore.controller;

import com.project.bookstore.miscellaneous.*;
import com.project.bookstore.model.*;
import com.project.bookstore.service.ServiceOrder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderCtrl {

	Logger log = LoggerFactory.getLogger(BookCtrl.class);

	@Autowired
	private ServiceOrder orderService;

	// add a single item to the cart
	@PostMapping("/addSingleCartItem")
	public String addSingleItemToCart(@RequestBody String data) {
		log.debug("Entered /addSingleItemToCart with data: " +  data);

		if (StringUtils.isEmpty(data)) {
			return Util.getJsonResponse(ErrorCodes.RESULT_INVALID_DATA, null);
		}

		ObjectMapper mapper = new ObjectMapper();

		try {
			InputDataCartItem cartData = mapper.readValue(data, InputDataCartItem.class);
			return orderService.addSingleItemToCart(cartData);
		} catch (JsonProcessingException e) {
			log.error(e.getMessage(), e);
			return Util.getJsonResponse(FrontEndComs.RESULT_UNKNOWN_ERROR, null);
		}
	}

	// add multiple items to the users cart
	// NOTE: data contains the userID and a list of items to be added to their cart
	// returns success or fail
	@PostMapping("/addItemsToCart")
	public String addToCart(@RequestBody String data) {
		log.debug("Entered /addToCart with data: " +  data);
		
		if (StringUtils.isEmpty(data)) {
			return Util.getJsonResponse(ErrorCodes.RESULT_INVALID_DATA, null);
		}

		ObjectMapper mapper = new ObjectMapper();
		try {
			InputDataCart cartData = mapper.readValue(data, InputDataCart.class);
			return orderService.addItemListToCart(cartData);
		} catch (JsonProcessingException e) {
			log.error(e.getMessage(), e);
			return Util.getJsonResponse(FrontEndComs.RESULT_UNKNOWN_ERROR, null);
		}
	}

	// return a list of items in the specified user's cart
	@GetMapping("/getCart")
	public String getCart(@RequestParam (name = "userID") String userID){
		log.debug("Entered /getCartItems with data: " + userID);
		
		if (StringUtils.isEmpty(userID)) {
			return Util.getJsonResponse(ErrorCodes.RESULT_INVALID_DATA, null);
		}

		try {
			return orderService.getCartItems(userID);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return Util.getJsonResponse(FrontEndComs.RESULT_UNKNOWN_ERROR, null);
		}
	}

	// removes an item from the user's cart
	// NOTE: data contains the userID and the book to be removed from their cart
	// returns success of fail
	@PostMapping("/removeCartItem")
	public String removeCartItem(@RequestBody String data) {
		log.debug("Entered /removeFromCart with data: " + data);
		
		if (StringUtils.isEmpty(data)) {
			return Util.getJsonResponse(ErrorCodes.RESULT_INVALID_DATA, null);
		}

		ObjectMapper mapper = new ObjectMapper();
		try {
			InputDataItemRemove inputData = mapper.readValue(data, InputDataItemRemove.class);
			return orderService.removeCartItem(inputData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return Util.getJsonResponse(FrontEndComs.RESULT_UNKNOWN_ERROR, null);
		}
	}

	// confirm order
	// (requires validating cart and credit card information)
	@PostMapping("confirmOrder")
	public String confirmOrder(@RequestBody String data) {
		log.debug("Entered /confirmOrder with data: " + data);
		
		if (StringUtils.isEmpty(data)) {
			return Util.getJsonResponse(ErrorCodes.RESULT_INVALID_DATA, null);
		}

		ObjectMapper mapper = new ObjectMapper();
		
		try {
			InputDataCreditCard inputData = mapper.readValue(data, InputDataCreditCard.class);
			return orderService.confirmOrder(inputData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return Util.getJsonResponse(FrontEndComs.RESULT_UNKNOWN_ERROR, null);
		}
	}
}
