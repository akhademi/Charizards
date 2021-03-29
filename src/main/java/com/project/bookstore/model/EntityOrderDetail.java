package com.project.bookstore.model;

import javax.persistence.*;

@Entity
@Table(name = "ORDER_DETAIL", schema = "JRV77878", catalog = "")
@IdClass(EntityOrderDetailPK.class)
public class EntityOrderDetail {
	private int orderID;
	private int bookID;
	private Double bookPrice;
	private Integer quantity;

	@ManyToOne
	private EntityOrder entityOrder;

	public EntityOrderDetail() {
	}

	public EntityOrderDetail(int orderId, int bid, Integer quantity, Double price) {
		this.orderID = orderId;
		this.bookID = bid;
		this.quantity = quantity;
		this.bookPrice = price;
	}

	@Id
	@Column(name = "ORDER_ID")
	public int getOrderId() {
		return orderID;
	}

	@Id
	@Column(name = "BID")
	public int getBid() {
		return bookID;
	}

	@Basic
	@Column(name = "QUANTITY")
	public Integer getQuantity() {
		return quantity;
	}

	@Basic
	@Column(name = "PRICE")
	public Double getPrice() {
		return bookPrice;
	}

	public void setOrderId(int orderId) {
		this.orderID = orderId;
	}

	public void setBid(int bid) {
		this.bookID = bid;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public void setPrice(Double price) {
		this.bookPrice = price;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		EntityOrderDetail that = (EntityOrderDetail) o;

		if (orderID != that.orderID)
			return false;
		if (bookID != that.bookID)
			return false;
		if (quantity != null ? !quantity.equals(that.quantity) : that.quantity != null)
			return false;
		if (bookPrice != null ? !bookPrice.equals(that.bookPrice) : that.bookPrice != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = orderID;
		result = 31 * result + bookID;
		result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
		result = 31 * result + (bookPrice != null ? bookPrice.hashCode() : 0);
		return result;
	}
}
