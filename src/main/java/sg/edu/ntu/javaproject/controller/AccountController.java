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

import lombok.extern.slf4j.Slf4j;
import sg.edu.ntu.javaproject.Exception.AccountNumberExistsException;
import sg.edu.ntu.javaproject.Exception.NullException;
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
    public ResponseEntity<?> createAccount(@RequestBody Account account) {
        try {
            Account newAccount = accountService.createAccount(account);
            String accountJson = objectMapper.writeValueAsString(newAccount);
            log.info("new account created: " + accountJson);
            return new ResponseEntity<>(newAccount, HttpStatus.CREATED);
        } catch (JsonProcessingException e) {
            log.error("Error converting account to Json: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (AccountNumberExistsException e) {
            log.error("Account number is already exists: {}", account.getAccountNumber());
            return new ResponseEntity<>(e.getMessage(),
                    HttpStatus.CONFLICT);
        } catch (NullException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping({ "", "/" })
    public ResponseEntity<ArrayList<Account>> getAllAccounts() {
        ArrayList<Account> allAccounts = accountService.getAllAccounts();
        return new ResponseEntity<>(allAccounts, HttpStatus.OK);
    }

    @GetMapping({ "/{id}", "/{id}/" })
    public ResponseEntity<Account> getAccountById(@PathVariable Integer id) {
        Account accountById = accountService.getAccountById(id);
        return new ResponseEntity<>(accountById, HttpStatus.OK);
    }

    @GetMapping("/searchByCustomerId/{id}")
    public ResponseEntity<ArrayList<Account>> searchByCustomerId(@PathVariable Integer id) {
        ArrayList<Account> accountList = accountService.getAccountByCustomerId(id);
        log.info("search accounts by customer id: " + id);
        return new ResponseEntity<>(accountList, HttpStatus.OK);
    }

    @DeleteMapping({ "/{id}", "/{id}/" })
    public ResponseEntity<Account> deleteAccountById(@PathVariable Integer id) {
        accountService.deleteAccountById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping({ "/{id}", "/{id}/" })
    public ResponseEntity<Account> updateAccountById(@PathVariable Integer id, @RequestBody Account account) {
        Account updatedAccount = accountService.updateAccount(id, account);
        return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
    }

}
