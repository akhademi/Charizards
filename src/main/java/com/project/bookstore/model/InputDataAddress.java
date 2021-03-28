package com.project.bookstore.model;

public class InputDataAddress extends InputData {
	private int streetNumber;
	private String streetName;
	private String city;
	private String zipCode;
	private String phoneNumber;
	private String province;
	private String country;

	public InputDataAddress() {
	}

	public int getStreetNo() {
		return streetNumber;
	}

	public String getStreetName() {
		return streetName;
	}

	public String getCity() {
		return city;
	}

	public String getZip() {
		return zipCode;
	}

	public String getPhone() {
		return phoneNumber;
	}

	public String getProvince() {
		return province;
	}

	public String getCountry() {
		return country;
	}

	public void setStreetNo(int streetNo) {
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
