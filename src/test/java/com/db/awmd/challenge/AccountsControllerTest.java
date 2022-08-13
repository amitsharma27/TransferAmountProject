package com.db.awmd.challenge;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.AmountTransferResponse;
import com.db.awmd.challenge.exception.AccountBalanceException;
import com.db.awmd.challenge.service.AccountsService;
import com.db.awmd.challenge.service.AmountTransferServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class AccountsControllerTest {

  private MockMvc mockMvc;

  @Autowired
  private AccountsService accountsService;
  
  

  @Autowired
  private WebApplicationContext webApplicationContext;
  
  @Mock
  AmountTransferServiceImpl amountTransferServiceImpl;

  @Before
  public void prepareMockMvc() {
    this.mockMvc = webAppContextSetup(this.webApplicationContext).build();

    // Reset the existing accounts before each test.
    accountsService.getAccountsRepository().clearAccounts();
  }

  @Test
  public void createAccount() throws Exception {
    this.mockMvc.perform(post("/v1/accounts").contentType(MediaType.APPLICATION_JSON)
      .content("{\"accountId\":\"Id-123\",\"balance\":1000}")).andExpect(status().isCreated());

    Account account = accountsService.getAccount("Id-123");
    assertThat(account.getAccountId()).isEqualTo("Id-123");
    assertThat(account.getBalance()).isEqualByComparingTo("1000");
  }

  @Test
  public void createDuplicateAccount() throws Exception {
    this.mockMvc.perform(post("/v1/accounts").contentType(MediaType.APPLICATION_JSON)
      .content("{\"accountId\":\"Id-123\",\"balance\":1000}")).andExpect(status().isCreated());

    this.mockMvc.perform(post("/v1/accounts").contentType(MediaType.APPLICATION_JSON)
      .content("{\"accountId\":\"Id-123\",\"balance\":1000}")).andExpect(status().isBadRequest());
  }

  @Test
  public void createAccountNoAccountId() throws Exception {
    this.mockMvc.perform(post("/v1/accounts").contentType(MediaType.APPLICATION_JSON)
      .content("{\"balance\":1000}")).andExpect(status().isBadRequest());
  }

  @Test
  public void createAccountNoBalance() throws Exception {
    this.mockMvc.perform(post("/v1/accounts").contentType(MediaType.APPLICATION_JSON)
      .content("{\"accountId\":\"Id-123\"}")).andExpect(status().isBadRequest());
  }

  @Test
  public void createAccountNoBody() throws Exception {
    this.mockMvc.perform(post("/v1/accounts").contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isBadRequest());
  }

  @Test
  public void createAccountNegativeBalance() throws Exception {
    this.mockMvc.perform(post("/v1/accounts").contentType(MediaType.APPLICATION_JSON)
      .content("{\"accountId\":\"Id-123\",\"balance\":-1000}")).andExpect(status().isBadRequest());
  }

  @Test
  public void createAccountEmptyAccountId() throws Exception {
    this.mockMvc.perform(post("/v1/accounts").contentType(MediaType.APPLICATION_JSON)
      .content("{\"accountId\":\"\",\"balance\":1000}")).andExpect(status().isBadRequest());
  }

  @Test
  public void getAccount() throws Exception {
    String uniqueAccountId = "Id-" + System.currentTimeMillis();
    Account account = new Account(uniqueAccountId, new BigDecimal("123.45"));
    this.accountsService.createAccount(account);
    this.mockMvc.perform(get("/v1/accounts/" + uniqueAccountId))
      .andExpect(status().isOk())
      .andExpect(
        content().string("{\"accountId\":\"" + uniqueAccountId + "\",\"balance\":123.45}"));
  }
  
  
  @Test
  public void test_amountTransfer() throws Exception {
	  Mockito.when(amountTransferServiceImpl.fundTransfer(123L, 456L, new BigDecimal(1000.0))).thenReturn
	  (new AmountTransferResponse("Success",200));
   MvcResult result= this.mockMvc.perform(post("/v1/accounts/transfer").contentType(MediaType.APPLICATION_JSON)
      .content("{\n"
      		+ "\"fromAccount\":123,\n"
      		+ "\"toAccount\":456,\n"
      		+ "\"amount\":1000\n"
      		+ "\n"
      		+ "}")).andReturn();
   
   int status = result.getResponse().getStatus();
   assertEquals(200, status);
  }
  
  @Test
  public void test_amountTransfer_NegativeBalanceExp() throws Exception {
	  Mockito.when(amountTransferServiceImpl.fundTransfer(123L, 456L, new BigDecimal(0))).
	  thenThrow(new AccountBalanceException("Incorrect Balance"));
    this.mockMvc.perform(post("/v1/accounts/transfer").contentType(MediaType.APPLICATION_JSON)
      .content("{\n"
      		+ "\"fromAccount\":123,\n"
      		+ "\"toAccount\":456,\n"
      		+ "\"amount\":0\n"
      		+ "\n"
      		+ "}")).andExpect(result -> assertTrue(result.getResolvedException() instanceof AccountBalanceException));
  }
  @Test
  public void test_amountTransfer_InsufficientBalExp() throws Exception {
	  Mockito.when(amountTransferServiceImpl.fundTransfer(123L, 456L, new BigDecimal(1000.0))).
	  thenThrow(new AccountBalanceException("Insufficient Balance"));
    this.mockMvc.perform(post("/v1/accounts/transfer").contentType(MediaType.APPLICATION_JSON)
      .content("{\n"
      		+ "\"fromAccount\":123,\n"
      		+ "\"toAccount\":456,\n"
      		+ "\"amount\":1000\n"
      		+ "\n"
      		+ "}")).andExpect(result -> assertTrue(result.getResolvedException() instanceof AccountBalanceException));
  }
  
}
