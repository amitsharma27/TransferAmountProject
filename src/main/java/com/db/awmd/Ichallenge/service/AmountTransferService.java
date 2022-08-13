package com.db.awmd.Ichallenge.service;

import java.math.BigDecimal;

import com.db.awmd.challenge.domain.AmountTransferResponse;

public interface AmountTransferService {
	
	  AmountTransferResponse fundTransfer(Long fromAccount,  Long toAccount,BigDecimal amount) ;

	
	  

}
