package com.project.bookstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.bookstore.miscellaneous.*;
import com.project.bookstore.model.EntityBookModel;
import com.project.bookstore.model.EntityReview;
import com.project.bookstore.model.InputDataReview;
import com.project.bookstore.service.*;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookCtrl {

	Logger log = LoggerFactory.getLogger(BookCtrl.class);

	@Autowired
	private ServiceBook bookService;
	@Autowired
	private ServiceReview reviewService;

	/*
	 * @return list of next 10 books based on pageNo offset
	 * @throws Exception
	 */

	@GetMapping("/getAllBooks")
	public List<EntityBookModel> getAllBooks(@RequestParam(required = false, defaultValue = "1") Integer pageno) {
		try {
			return bookService.getAllBooks(pageno);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}


	/**
	 * @return List of Category
	 */

	@GetMapping("/findByCategory")
	public List<EntityBookModel> findBooksByCategory(@RequestParam String category, @RequestParam(required = false, defaultValue = "1") Integer pageno) {
		try {
			return bookService.getBooksByCategory(category,pageno);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	/*
	 * Controller to get all the categories
	 * @return List of all Category
	 * */

	@GetMapping("/getAllCategory")
	public List<String> getAllCategory() {
		try {
			return bookService.getAllCategory();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * @param bid - book id
	 * @return book entity as a JSON String (with indentation), otherwise error with status code and message
	 * @apiNote for both client and partners
	 */
	@GetMapping("/getProductInfo")
	public String getBookInfo(@RequestParam(name = "bid") int bid) {
		log.debug(String.format("Entered getProductInfo for bid: %s", bid));
		ObjectMapper mapper = new ObjectMapper();
		try {
			JSONObject json = new JSONObject();
			EntityBookModel book = bookService.getBookInfo(bid);
			
			if (book == null) {
				json.put("status", FrontEndComs.RESPONSE_FAIL);
				json.put("message", "Please enter a valid book/product ID.");
				return json.toString(4);
			}
			
			return mapper.writeValueAsString(book);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return Util.getJsonResponse(FrontEndComs.RESULT_UNKNOWN_ERROR, null);
		}
	}

	/**
	 * @param title: book title/seach query
	 * @return list of all books containing that title word
	 */
	@GetMapping("/searchByTitle")
	public List<EntityBookModel> searchBooksByTitle(@RequestParam(name = "title") String title){
		log.debug(String.format("Entered searchBooksByTitle for title: %s", title));
		try{
			return bookService.searchBooksByTitle(title);
		} catch (Exception e){
			log.error(e.getMessage(), e);
			return new ArrayList<>();
		}
	}

	/* get Reviews for a book */
	@GetMapping("/getReviews")
	public List<EntityReview> getReviewsForBook(@RequestParam(name = "bid") int bid){
		try{
			return reviewService.getReviewsForBook(bid);
		} catch (Exception e){
			log.error(e.getMessage(), e);
			return null;
		}
	}

	/* Add a review for a book */
	@PostMapping("/addReview")
	public String addReview(@RequestBody String data){
		log.debug(String.format("Entered addReview for data: %s", data));

		try{
			ObjectMapper mapper = new ObjectMapper();
			InputDataReview inputData = mapper.readValue(data, InputDataReview.class);
			return reviewService.addReview(inputData);
		} catch (Exception e){
			log.error(e.getMessage(), e);
			return Util.getJsonResponse(FrontEndComs.RESULT_UNKNOWN_ERROR, null);
		}
	}


}
