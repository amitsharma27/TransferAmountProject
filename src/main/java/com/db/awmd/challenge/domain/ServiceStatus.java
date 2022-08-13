package com.db.awmd.challenge.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@JsonInclude
@AllArgsConstructor
public class ServiceStatus implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 7998216387458564862L;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private int statusCode;
    private String status;
    private String message;

}
