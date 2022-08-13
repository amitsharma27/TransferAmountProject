package com.db.awmd.challenge.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude
public class AmountTransferResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8262358258442863371L;

	
    private String message;
	
    private int statusCode;
    
    private long transactionId;

	public AmountTransferResponse(String message, int statusCode) {
		super();
		this.message = message;
		this.statusCode = statusCode;
	}
	
	public AmountTransferResponse()
	{}
	
}
