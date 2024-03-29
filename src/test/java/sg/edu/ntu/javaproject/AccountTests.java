package sg.edu.ntu.javaproject;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import sg.edu.ntu.javaproject.entity.Account;
import sg.edu.ntu.javaproject.service.AccountService;

import java.util.ArrayList;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AccountTests {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private AccountService accountService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateAccount() throws Exception {
        Account account = new Account();
        account.setAccountNumber(123456789);
        account.setBalance(1000);
        account.setAccountTypeId(1);
        account.setCustomerId(1);
        account.setCreatedDate(new Date());
        account.setUpdatedDate(new Date());

        when(accountService.createAccount(any(Account.class))).thenReturn(account);

        mockMvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(account)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.accountNumber").value(account.getAccountNumber()))
                .andExpect(jsonPath("$.balance").value(account.getBalance()))
                .andExpect(jsonPath("$.accountTypeId").value(account.getAccountTypeId()))
                .andExpect(jsonPath("$.customerId").value(account.getCustomerId()));
    }

    @Test
    public void testGetAllAccounts() throws Exception {
        ArrayList<Account> accounts = new ArrayList<>();
        Account account1 = new Account();
        account1.setAccountId(1);
        account1.setAccountNumber(123456789);
        account1.setBalance(1000);
        account1.setAccountTypeId(1);
        account1.setCustomerId(1);
        account1.setCreatedDate(new Date());
        account1.setUpdatedDate(new Date());
        accounts.add(account1);

        when(accountService.getAllAccounts()).thenReturn(accounts);

        mockMvc.perform(get("/accounts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].accountId").value(account1.getAccountId()))
                .andExpect(jsonPath("$[0].accountNumber").value(account1.getAccountNumber()))
                .andExpect(jsonPath("$[0].balance").value(account1.getBalance()))
                .andExpect(jsonPath("$[0].accountTypeId").value(account1.getAccountTypeId()))
                .andExpect(jsonPath("$[0].customerId").value(account1.getCustomerId()));
    }

    @Test
    public void testGetAccountById() throws Exception {
        int accountId = 1;
        Account account = new Account();
        account.setAccountId(accountId);
        account.setAccountNumber(123456789);
        account.setBalance(1000);
        account.setAccountTypeId(1);
        account.setCustomerId(1);
        account.setCreatedDate(new Date());
        account.setUpdatedDate(new Date());

        when(accountService.getAccountById(accountId)).thenReturn(account);

        mockMvc.perform(get("/accounts/{id}", accountId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.accountId").value(account.getAccountId()))
                .andExpect(jsonPath("$.accountNumber").value(account.getAccountNumber()))
                .andExpect(jsonPath("$.balance").value(account.getBalance()))
                .andExpect(jsonPath("$.accountTypeId").value(account.getAccountTypeId()))
                .andExpect(jsonPath("$.customerId").value(account.getCustomerId()));
    }

    @Test
    public void testSearchByCustomerId() throws Exception {
        int customerId = 1;
        ArrayList<Account> accounts = new ArrayList<>();
        Account account1 = new Account();
        account1.setAccountId(1);
        account1.setAccountNumber(123456789);
        account1.setBalance(1000);
        account1.setAccountTypeId(1);
        account1.setCustomerId(customerId);
        account1.setCreatedDate(new Date());
        account1.setUpdatedDate(new Date());
        accounts.add(account1);

        when(accountService.getAccountByCustomerId(customerId)).thenReturn(accounts);

        mockMvc.perform(get("/accounts/searchByCustomerId/{customerId}", customerId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].accountId").value(account1.getAccountId()))
                .andExpect(jsonPath("$[0].accountNumber").value(account1.getAccountNumber()))
                .andExpect(jsonPath("$[0].balance").value(account1.getBalance()))
                .andExpect(jsonPath("$[0].accountTypeId").value(account1.getAccountTypeId()))
                .andExpect(jsonPath("$[0].customerId").value(account1.getCustomerId()));
    }

    @Test
    public void testDeleteAccountById() throws Exception {
        int accountId = 1;

        mockMvc.perform(delete("/accounts/{id}", accountId))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testUpdateAccountById() throws Exception {
        int accountId = 1;
        Account account = new Account();
        account.setAccountId(accountId);
        account.setAccountNumber(123456789);
        account.setBalance(1000);
        account.setAccountTypeId(1);
        account.setCustomerId(1);
        account.setCreatedDate(new Date());
        account.setUpdatedDate(new Date());

        when(accountService.updateAccount(accountId, account)).thenReturn(account);

        mockMvc.perform(put("/accounts/{id}", accountId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(account)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.accountId").value(account.getAccountId()))
                .andExpect(jsonPath("$.accountNumber").value(account.getAccountNumber()))
                .andExpect(jsonPath("$.balance").value(account.getBalance()))
                .andExpect(jsonPath("$.accountTypeId").value(account.getAccountTypeId()))
                .andExpect(jsonPath("$.customerId").value(account.getCustomerId()));
    }
}