package com.db.awmd.challenge.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.db.awmd.challenge.domain.ServiceStatus;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
//This class will throw exception at controller level 
	 @ExceptionHandler(value = AccountBalanceException.class)
	   public ResponseEntity<Object> exception(AccountBalanceException exception) {
		 ServiceStatus serviceStatus=new ServiceStatus(LocalDateTime.now(),1001,"Failed",exception.getMessage());
	      return new ResponseEntity<>(serviceStatus, HttpStatus.OK);
	   }


}
