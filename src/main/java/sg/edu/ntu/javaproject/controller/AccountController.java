package sg.edu.ntu.javaproject.controller;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import sg.edu.ntu.javaproject.entity.Account;
import sg.edu.ntu.javaproject.service.AccountService;

@RestController
@RequestMapping("/accounts")
@Slf4j
public class AccountController {
    private AccountService accountService;
    private ObjectMapper objectMapper;

    public AccountController(AccountService accountService, ObjectMapper objectMapper) {
        this.accountService = accountService;
        this.objectMapper = objectMapper;
    }

    @PostMapping({ "", "/" })
    public ResponseEntity<Account> createAccount(@Valid @RequestBody Account account) throws JsonProcessingException {
        Account newAccount = accountService.createAccount(account);
        String accountJson = objectMapper.writeValueAsString(newAccount);
        log.info("new account created: " + accountJson);
        return new ResponseEntity<>(newAccount, HttpStatus.CREATED);
    }

    @GetMapping({ "", "/" })
    public ResponseEntity<ArrayList<Account>> getAllAccounts() throws JsonProcessingException {
        ArrayList<Account> allAccounts = accountService.getAllAccounts();
        String allAccountsJson = objectMapper.writeValueAsString(allAccounts);
        log.info("retreiving all accounts");
        log.info("account list: " + allAccountsJson);
        return new ResponseEntity<>(allAccounts, HttpStatus.OK);
    }

    @GetMapping({ "/{accountId}", "/{accountId}/" })
    public ResponseEntity<Account> getAccountById(@PathVariable Integer accountId) throws JsonProcessingException {
        Account accountById = accountService.getAccountById(accountId);
        String accountJson = objectMapper.writeValueAsString(accountById);
        log.info("search account by account id " + accountId);
        log.info("account details: " + accountJson);
        return new ResponseEntity<>(accountById, HttpStatus.OK);
    }

    // Lionel: Hendry may I ask u if we are searching for an account by the user id
    // or are we searching for an account id by the user?
    @GetMapping("/searchByCustomerId/{accountId}")
    public ResponseEntity<ArrayList<Account>> searchByCustomerId(@PathVariable Integer accountId)
            throws JsonProcessingException {
        ArrayList<Account> accountList = accountService.getAccountByCustomerId(accountId);
        String accountJson = objectMapper.writeValueAsString(accountList);
        log.info("search accounts   by customer id: " + accountId);
        log.info("account list: " + accountJson);
        return new ResponseEntity<>(accountList, HttpStatus.OK);
    }

    @DeleteMapping({ "/{accountId}", "/{accountId}/" })
    public ResponseEntity<Account> deleteAccountById(@PathVariable Integer accountId) {
        accountService.deleteAccountById(accountId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping({ "/{accountId}", "/{accountId}/" })
    public ResponseEntity<Account> updateAccountById(@PathVariable Integer accountId, @RequestBody Account account)
            throws JsonProcessingException {
        Account updatedAccount = accountService.updateAccount(accountId, account);
        String accountJson = objectMapper.writeValueAsString(updatedAccount);
        log.info("updating account id: " + accountId);
        log.info("new account details: " + accountJson);
        return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
    }

}
