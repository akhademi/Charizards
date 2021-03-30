package com.project.bookstore.repository;

import com.project.bookstore.model.*;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReviewRepo {
	
	// return reviews of specified book
	public List<EntityReview> getReviews(int bid) {
		return null;
	}
	
	// add review for specified book
	public int addReview(int bid, String userID, double rating, String message) {
		return 0;
	}
}
