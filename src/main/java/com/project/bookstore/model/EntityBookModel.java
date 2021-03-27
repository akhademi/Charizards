package com.project.bookstore.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class EntityBookModel {

	@Id
	public int book_id;
	public String book_title;
	public String book_author;
	public Double book_price;
	public String book_category;
	public String format;
	public Double stars;
	public String images;

	public EntityBookModel() {
	}

	public EntityBookModel(int bid, String title, String author, String format, Double stars, Double price,
			String category, String images) {
		this.book_id = bid;
		this.book_title = title;
		this.book_author = author;
		this.book_price = price;
		this.book_category = category;
		this.format = format;
		this.stars = stars;
		this.images = images;
	}

	public int getBid() {
		return book_id;
	}

	public String getTitle() {
		return book_title;
	}

	public String getAuthor() {
		return book_author;
	}

	public String getFormat() {
		return format;
	}

	public Double getStars() {
		return stars;
	}

	public Double getPrice() {
		return book_price;
	}

	public String getCategory() {
		return book_category;
	}

	public String getImages() {
		return images;
	}

	public void setBid(int bid) {
		this.book_id = bid;
	}

	public void setTitle(String title) {
		this.book_title = title;
	}

	public void setAuthor(String author) {
		this.book_author = author;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public void setStars(Double stars) {
		this.stars = stars;
	}

	public void setPrice(Double price) {
		this.book_price = price;
	}

	public void setCategory(String category) {
		this.book_category = category;
	}

	public void setImages(String images) {
		this.images = images;
	}
}
