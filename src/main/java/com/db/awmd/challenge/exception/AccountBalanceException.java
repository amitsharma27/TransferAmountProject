package com.db.awmd.challenge.exception;

import java.io.Serializable;

public class AccountBalanceException extends RuntimeException implements Serializable{

	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccountBalanceException(String message) {
	    super(message);
	  }
}
