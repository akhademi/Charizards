package com.project.bookstore.miscellaneous;

public class Users {

	// defines the possible types of users (CUSTOMER and ADMIN)
	public enum UserType {
		CUSTOMER(0),
		ADMIN(1);

		private final int value;
		
		UserType(final int value) {
			this.value = value;
		}
		public int getValue() {
			return value;
		}
	}
}
