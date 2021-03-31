package com.project.bookstore.miscellaneous;

public class OrderState {

	// defines the possible states of an order (IN_CART, ORDERED, and DENIED)
	public enum OrderStatus{
		IN_CART(0),
		ORDERED(1),
		DENIED(2);

		private final int value;
		
		OrderStatus(int value) {
			this.value = value;
		}
		public int getValue() {
			return value;
		}
	}
}
