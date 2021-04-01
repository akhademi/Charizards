package com.project.bookstore.miscellaneous;

public class ErrorCodes {

	public static final int RESPONSE_SUCCESS = 0;
	public static final int RESPONSE_FAIL = 1;
  
	public static final int RESULT_UNKNOWN_ERROR = -10;
  
	// Error Codes
	public static final int RESULT_INVALID_DATA = -100;
	public static final int INVALID_USER_SIGNUP_DATA = -1;
	public static final int RESULT_USER_ALREADY_EXISTS = -2;
	public static final int RESULT_INVALID_CREDENTIALS = -3;
	public static final int RESULT_USER_DOES_NOT_EXIST = -4;

	public static final int REVIEW_MESSAGE_LENGTH = 250; // max 250 character review
	public static final int ORDER_MAX_SUBMIT_ATTEMPTS = 3; // max 250 character review
  
  }