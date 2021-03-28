package com.project.bookstore.model;

import org.springframework.util.StringUtils;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "user")
public class EntityUser {

	@Id
	@Column
	private String userID;
	@Basic
	@Column
	private Integer userType;
	@Basic
	@Column
	private String firstName;
	@Basic
	@Column
	private String lastName;
	@Basic
	@Column
	private String email;
	@Basic
	@Column
	private String password;

	@Basic
	@Column
	private String address_ID;

	public EntityUser(String user_id, String first_name, String last_name, String email, String password,
			Integer user_type, String address_id) {
		this.userID = user_id;
		this.firstName = first_name;
		this.lastName = last_name;
		this.email = email;
		this.password = password;
		this.userType = user_type;
		this.address_ID = address_id;
	}

	public EntityUser() {
	}

	public String getUser_id() {
		return userID;
	}

	public String getFirst_name() {
		return firstName;
	}

	public String getLast_name() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public Integer getUser_type() {
		return userType;
	}

	public String getAddress_id() {
		return address_ID;
	}

	public void setUser_id(String user_id) {
		this.userID = user_id;
	}

	public void setFirst_name(String first_name) {
		this.firstName = first_name;
	}

	public void setLast_name(String last_name) {
		this.lastName = last_name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUser_type(Integer user_type) {
		this.userType = user_type;
	}

	public void setAddress_id(String address_id) {
		this.address_ID = address_id;
	}

	@Override
	public String toString() {
		return "EntityUser{" + "user_id='" + userID + '\'' + ", first_name='" + firstName + '\'' + ", last_name='"
				+ lastName + '\'' + ", email='" + email + '\'' + ", password='" + password + '\'' + ", user_type="
				+ userType + '}';
	}

	public boolean isValid() {
		return !StringUtils.isEmpty(email) && !StringUtils.isEmpty(firstName) && !StringUtils.isEmpty(lastName)
				&& !StringUtils.isEmpty(password) && email.length() > 0 && email.length() <= 40
				&& password.length() >= 6 && password.length() <= 80 && firstName.length() > 0
				&& firstName.length() < 30 && lastName.length() > 0 && lastName.length() < 30;
	}
}
