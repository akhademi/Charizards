package com.project.bookstore.model;

import javax.persistence.*;

@Entity
@Table(name = "ADDRESS", schema = "JRV77878", catalog = "")
public class EntityAddressModel {
// Declaring ID for the address
	private String address_ID;
// Declaring Street #, Street Name, City, Zip Code, Phone Number 
// Province, and Country
	private Integer streetNumber;
	private String streetName;
	private String city;
	private String zipCode;
	private String phoneNumber;
	private String province;
	private String country;

	@Id
	@Column(name = "ADDRESS_ID")
	public String getAddressId() {
		return address_ID;
	}

	@Basic
	@Column(name = "STREET_NUMBER")
	public Integer getStreetNo() {
		return streetNumber;
	}

	@Basic
	@Column(name = "STREET_NAME")
	public String getStreetName() {
		return streetName;
	}

	@Basic
	@Column(name = "CITY")
	public String getCity() {
		return city;
	}

	@Basic
	@Column(name = "PROVINCE")
	public String getProvince() {
		return province;
	}

	@Basic
	@Column(name = "COUNTRY")
	public String getCountry() {
		return country;
	}

	@Basic
	@Column(name = "ZIP_CODE")
	public String getZip() {
		return zipCode;
	}

	@Basic
	@Column(name = "PHONE_NUMBER")
	public String getPhone() {
		return phoneNumber;
	}

	public void setAddressId(String addressId) {
		this.address_ID = addressId;
	}

	public void setStreetNo(Integer streetNo) {
		this.streetNumber = streetNo;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setZip(String zip) {
		this.zipCode = zip;
	}

	public void setPhone(String phone) {
		this.phoneNumber = phone;
	}

}
