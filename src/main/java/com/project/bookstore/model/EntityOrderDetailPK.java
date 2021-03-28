package com.project.bookstore.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

@SuppressWarnings("serial")
public class EntityOrderDetailPK implements Serializable {
	private int orderID;
	private int bookID;

	@Column(name = "ORDER_ID")
	@Id
	public int getOrderId() {
		return orderID;
	}

	@Column(name = "BID")
	@Id
	public int getBid() {
		return bookID;
	}

	public void setOrderId(int orderId) {
		this.orderID = orderId;
	}

	public void setBid(int bid) {
		this.bookID = bid;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		EntityOrderDetailPK that = (EntityOrderDetailPK) o;

		if (orderID != that.orderID)
			return false;
		if (bookID != that.bookID)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = orderID;
		result = 31 * result + bookID;
		return result;
	}
}
