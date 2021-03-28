package com.project.bookstore.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "ORDER", schema = "JRV77878", catalog = "")
public class EntityOrder {
	private int orderID;
	private String userID;
	private Date orderDate;
	private Integer status;
	private Integer submitAttempts;

	@OneToMany(mappedBy = "order")
	private List<OrderDetailEntity> orderDetails;

	@Id
	@Column(name = "ORDER_ID")
	public int getOrderId() {
		return orderID;
	}

	@Basic
	@Column(name = "USER_ID")
	public String getUserId() {
		return userID;
	}

	@Basic
	@Column(name = "ORDER_DATE")
	public Date getOrderDate() {
		return orderDate;
	}

	@Basic
	@Column(name = "STATUS")
	public Integer getStatus() {
		return status;
	}

	@Basic
	@Column(name = "SUBMIT_ATTEMPTS")
	public Integer getSubmit_attempts() {
		return submitAttempts;
	}

	public void setOrderId(int orderId) {
		this.orderID = orderId;
	}

	public void setUserId(String userId) {
		this.userID = userId;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setSubmit_attempts(Integer submit_attempts) {
		this.submitAttempts = submit_attempts;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		EntityOrder that = (EntityOrder) o;

		if (orderID != that.orderID)
			return false;
		if (userID != null ? !userID.equals(that.userID) : that.userID != null)
			return false;
		if (orderDate != null ? !orderDate.equals(that.orderDate) : that.orderDate != null)
			return false;
		if (status != null ? !status.equals(that.status) : that.status != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = orderID;
		result = 31 * result + (userID != null ? userID.hashCode() : 0);
		result = 31 * result + (orderDate != null ? orderDate.hashCode() : 0);
		result = 31 * result + (status != null ? status.hashCode() : 0);
		return result;
	}
}
