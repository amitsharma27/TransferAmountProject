package com.db.awmd.challenge;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.AmountTransferResponse;
import com.db.awmd.challenge.exception.AccountBalanceException;
import com.db.awmd.challenge.repository.AccountsRepository;
import com.db.awmd.challenge.service.AmountTransferServiceImpl;
import com.db.awmd.challenge.service.NotificationService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AmountTransferServiceTest {

	@InjectMocks
	private AmountTransferServiceImpl amountTransferServiceImpl;

	@Mock
	AccountsRepository accountsRepository;

	@Mock
	NotificationService notificationService;

	@Test
	public void test_fundTransfer_success() {
		Mockito.when(accountsRepository.getAccount(Mockito.anyLong()))
				.thenReturn(new Account("123", new BigDecimal(500)));
		Mockito.when(accountsRepository.getAccount(Mockito.anyLong()))
				.thenReturn(new Account("456", new BigDecimal(500)));
		Mockito.doNothing().when(accountsRepository).updateBalance(Mockito.anyString(), Mockito.any());
		Mockito.doNothing().when(notificationService).notifyAboutTransferAsync(Mockito.anyLong(), Mockito.any());

		AmountTransferResponse response = amountTransferServiceImpl.fundTransfer(1234L, 4567L, new BigDecimal(200));
		
		
		assertThat(response.getStatusCode()).isEqualTo(200);

	}
	
	
	@Test(expected = AccountBalanceException.class)
	public void test_fundTransfer_NegativeAmountSend() {
		Mockito.when(accountsRepository.getAccount(Mockito.anyLong()))
				.thenReturn(new Account("123", new BigDecimal(500)));
		Mockito.when(accountsRepository.getAccount(Mockito.anyLong()))
				.thenReturn(new Account("456", new BigDecimal(500)));
		Mockito.doNothing().when(accountsRepository).updateBalance(Mockito.anyString(), Mockito.any());
		Mockito.doNothing().when(notificationService).notifyAboutTransferAsync(Mockito.anyLong(), Mockito.any());

		 amountTransferServiceImpl.fundTransfer(1234L, 4567L, new BigDecimal(0));

	}
	@Test(expected = AccountBalanceException.class)
	public void test_fundTransfer_InsufficientBalance() {
		Mockito.when(accountsRepository.getAccount(Mockito.anyLong()))
				.thenReturn(new Account("123", new BigDecimal(200)));
		Mockito.when(accountsRepository.getAccount(Mockito.anyLong()))
				.thenReturn(new Account("456", new BigDecimal(300)));
		Mockito.doNothing().when(accountsRepository).updateBalance(Mockito.anyString(), Mockito.any());
		Mockito.doNothing().when(notificationService).notifyAboutTransferAsync(Mockito.anyLong(), Mockito.any());

		 amountTransferServiceImpl.fundTransfer(1234L, 4567L, new BigDecimal(500));

	}
	@Test(expected = AccountBalanceException.class)
	public void test_fundTransfer_UnhandledExption() {
		Mockito.when(accountsRepository.getAccount(Mockito.anyLong()))
				.thenThrow(new AccountBalanceException("Unhandled error"));
		Mockito.when(accountsRepository.getAccount(Mockito.anyLong()))
				.thenReturn(new Account("456", new BigDecimal(500)));
		Mockito.doNothing().when(accountsRepository).updateBalance(Mockito.anyString(), Mockito.any());
		Mockito.doNothing().when(notificationService).notifyAboutTransferAsync(Mockito.anyLong(), Mockito.any());

		 amountTransferServiceImpl.fundTransfer(1234L, 4567L, new BigDecimal(500));

	}

}
