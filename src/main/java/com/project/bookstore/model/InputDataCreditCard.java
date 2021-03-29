package com.project.bookstore.model;

import org.springframework.util.StringUtils;

public class InputDataCreditCard extends InputData {
	private String name;			// name on credit card
	private String cardNumber; 		// 16 digit credit card number
	private String expiryDate; 		// expiry date of credit card in the format: 'mm/yy'
	private String cvv; 			// 3 digit security code on the back of credit card

	public InputDataCreditCard() {
	}

	public boolean isNameValid() {
		return (!name.equals("") && !StringUtils.isEmpty(name) && name.length() > 0 && name.length() < 30);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	// checks if the card number is 16 digits (all numbers)
	public boolean isNumberValid() {
		return this.cardNumber.matches("(?:[1-9][0-9]{15})|");
	}
	
	public String getNumber() {
		return cardNumber;
	}

	public void setNumber(String number) {
		this.cardNumber = number;
	}
	
	// checks if the expiry date is in the correct format (mm/yy)
	public boolean isExpiryValid() {
		return this.expiryDate.matches("(?:0[1-9]|1[0-2])/[0-9]{2}");
	}
	
	public String getExpiry() {
		return expiryDate;
	}

	public void setExpiry(String expiry) {
		this.expiryDate = expiry;
	}
	
	// checks if the CVV is 3 digits (all numbers)
	public boolean isCvvValid() {
		return this.cvv.matches("^[0-9]{3}$");
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}
	
	// checks if the name, card number, expiry date, and CVV entered is correct
	public boolean isValid() {
		return isNameValid() && isNumberValid() && isExpiryValid() && isCvvValid();
	}

}
