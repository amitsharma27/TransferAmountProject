package com.db.awmd.challenge.repository;

import java.math.BigDecimal;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.exception.DuplicateAccountIdException;

public interface AccountsRepository {

  void createAccount(Account account) throws DuplicateAccountIdException;

  Account getAccount(String accountId);
  
  Account getAccount(Long accountNO);

  void clearAccounts();
  
  void updateBalance(String accountId,BigDecimal amount);//new methos added for updating balance ,can create wrapper for updating both account in one call
}
