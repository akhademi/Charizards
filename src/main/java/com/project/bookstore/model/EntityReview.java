package com.project.bookstore.model;

import javax.persistence.*;

@Entity
@Table(name = "REVIEW", schema = "JRV77878", catalog = "")
public class EntityReview {
	private int reviewId;
	private int bookID;
	private String userID;
	private Double stars;
	private String message;

	@Id
	@Column(name = "REVIEW_ID")
	public int getReviewId() {
		return reviewId;
	}

	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}

	@Basic
	@Column(name = "BID")
	public int getBid() {
		return bookID;
	}

	@Basic
	@Column(name = "USER_ID")
	public String getUserId() {
		return userID;
	}

	@Basic
	@Column(name = "STARS")
	public Double getStars() {
		return stars;
	}

	@Basic
	@Column(name = "MESSAGE")
	public String getMessage() {
		return message;
	}

	public void setBid(int bid) {
		this.bookID = bid;
	}

	public void setUserId(String userId) {
		this.userID = userId;
	}

	public void setStars(Double stars) {
		this.stars = stars;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		EntityReview that = (EntityReview) o;

		if (reviewId != that.reviewId)
			return false;
		if (bookID != that.bookID)
			return false;
		if (userID != null ? !userID.equals(that.userID) : that.userID != null)
			return false;
		if (stars != null ? !stars.equals(that.stars) : that.stars != null)
			return false;
		if (message != null ? !message.equals(that.message) : that.message != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = reviewId;
		result = 31 * result + bookID;
		result = 31 * result + (userID != null ? userID.hashCode() : 0);
		result = 31 * result + (stars != null ? stars.hashCode() : 0);
		result = 31 * result + (message != null ? message.hashCode() : 0);
		return result;
	}
}
