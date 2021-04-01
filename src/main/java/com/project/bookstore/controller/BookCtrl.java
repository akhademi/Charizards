package com.project.bookstore.controller;

import com.project.bookstore.miscellaneous.*;
import com.project.bookstore.model.EntityBookModel;
import com.project.bookstore.model.EntityReview;
import com.project.bookstore.model.InputDataReview;
import com.project.bookstore.service.*;

import com.fasterxml.jackson.databind.ObjectMapper;

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

	// returns a list of the next 10 books starting from the pageno offset
	@GetMapping("/getAllBooks")
	public List<EntityBookModel> getAllBooks(@RequestParam(required = false, defaultValue = "1") Integer pageno) {
		try {
			return bookService.getAllBooks(pageno);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	// returns a list of the next 10 books in a specified category starting from the pageno offset
	@GetMapping("/findByCategory")
	public List<EntityBookModel> findBooksByCategory(@RequestParam String category, @RequestParam(required = false, defaultValue = "1") Integer pageno) {
		try {
			return bookService.getBooksByCategory(category, pageno);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	// returns a list of all the categories
	@GetMapping("/getAllCategory")
	public List<String> getAllCategory() {
		try {
			return bookService.getAllCategory();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	// returns a JSON string of a book, specified by the book ID (bid)
	@GetMapping("/getProductInfo")
	public String getBookInfo(@RequestParam(name = "bid") int bid) {
		log.debug(String.format("Entered getProductInfo for bid: %s", bid));
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			JSONObject json = new JSONObject();
			EntityBookModel book = bookService.getBookInfo(bid);
			
			// if book not found, return an error code
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

	// searches for all books with titles matching the search title
	// returns a list of the books resulting from the search
	@GetMapping("/searchByTitle")
	public List<EntityBookModel> searchBooksByTitle(@RequestParam(name = "title") String title) {
		log.debug(String.format("Entered searchBooksByTitle for title: %s", title));
		
		try {
			return bookService.searchBooksByTitle(title);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new ArrayList<>();
		}
	}

	// returns a list of reviews for a specified book
	@GetMapping("/getReviews")
	public List<EntityReview> getReviewsForBook(@RequestParam(name = "bid") int bid) {
		try {
			return reviewService.getReviewsForBook(bid);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	// add a review for a book
	@PostMapping("/addReview")
	public String addReview(@RequestBody String data) {
		log.debug(String.format("Entered addReview for data: %s", data));

		try {
			ObjectMapper mapper = new ObjectMapper();
			InputDataReview inputData = mapper.readValue(data, InputDataReview.class);
			
			return reviewService.addReview(inputData);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return Util.getJsonResponse(FrontEndComs.RESULT_UNKNOWN_ERROR, null);
		}
	}


}
