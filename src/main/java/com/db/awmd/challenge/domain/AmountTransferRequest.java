package com.db.awmd.challenge.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude
public class AmountTransferRequest implements Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = -3940552490703662765L;

	@NotNull
	@Min(value = 0, message = "amount must be positive.")
	private BigDecimal amount;

	@NotNull
	private Long fromAccount;

	@NotNull
	private Long toAccount;

	public AmountTransferRequest(@NotNull @Min(value = 0, message = "amount must be positive.") BigDecimal amount,
			@NotNull Long fromAccount, @NotNull Long toAccount) {
		super();
		this.amount = amount;
		this.fromAccount = fromAccount;
		this.toAccount = toAccount;
	}

	public AmountTransferRequest() {
	}

}
