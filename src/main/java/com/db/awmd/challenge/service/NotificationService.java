package com.db.awmd.challenge.service;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import com.db.awmd.challenge.domain.Account;

public interface NotificationService {

  void notifyAboutTransfer(Account account, String transferDescription);
  
  void notifyAboutTransferAsync(Long accountNO,BigDecimal amount);
}
