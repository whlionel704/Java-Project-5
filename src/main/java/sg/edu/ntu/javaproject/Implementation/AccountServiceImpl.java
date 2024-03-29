package sg.edu.ntu.javaproject.Implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import sg.edu.ntu.javaproject.Exception.AccountNotFoundException;
import sg.edu.ntu.javaproject.Exception.AccountNumberExistsException;
import sg.edu.ntu.javaproject.Exception.AccountTypeIsExistException;
import sg.edu.ntu.javaproject.Exception.AccountTypeIsNotExistException;
import sg.edu.ntu.javaproject.Exception.CustomerNotFoundException;
//import sg.edu.ntu.javaproject.Exception.NullException;
import sg.edu.ntu.javaproject.entity.Account;
import sg.edu.ntu.javaproject.entity.AccountType;
import sg.edu.ntu.javaproject.repository.AccountRepository;
import sg.edu.ntu.javaproject.repository.AccountTypeRepository;
import sg.edu.ntu.javaproject.repository.CustomerRepository;
import sg.edu.ntu.javaproject.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository;
    private AccountTypeRepository accountTypeRepository;
    private CustomerRepository customerRepository;

    public AccountServiceImpl(AccountRepository accountRepository, AccountTypeRepository accountTypeRepository,
            CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.accountTypeRepository = accountTypeRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public Account createAccount(Account account) {
        // check if account number is already exist
        if (accountRepository.existsByAccountNumber(account.getAccountNumber())) {
            throw new AccountNumberExistsException(account.getAccountNumber());
        }
        // check if customer id is exist in customer table
        customerRepository.findById(account.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException(account.getCustomerId()));
        // check if customer id with account type id is already exist
        if (accountRepository.existByAccountType(account.getCustomerId(), account.getAccountTypeId())) {
            throw new AccountTypeIsExistException(account.getCustomerId(), account.getAccountTypeId());
        }
        if (account.getBalance() == null) {
            account.setBalance(0);
        }
        // check if account type id is exist in account type table
        if (account.getAccountTypeId() == null || !accountTypeRepository.existsById(account.getAccountTypeId())) {
            throw new AccountTypeIsNotExistException(account.getAccountTypeId());

        }
        AccountType savedAccountType = accountTypeRepository.findById(account.getAccountTypeId())
                .orElseThrow(
                        () -> new AccountTypeIsNotExistException(account.getAccountTypeId()));
        account.setAccountTypeName(savedAccountType.getAccountTypeName());

        return accountRepository.save(account);
    }

    @Override
    public void deleteAccountById(Integer id) {
        if (!accountRepository.existsById(id)) {
            throw new AccountNotFoundException(id);
        }
        accountRepository.deleteById(id);
    }

    @Override
    public Account getAccountById(Integer id) {
        return accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException(id));
    }

    @Override
    public ArrayList<Account> getAllAccounts() {
        List<Account> allAccounts = accountRepository.findAll();
        for (Account account : allAccounts) {
            AccountType savedAccountType = accountTypeRepository.findById(account.getAccountTypeId()).get();
            account.setAccountTypeName(savedAccountType.getAccountTypeName());
        }
        return (ArrayList<Account>) allAccounts;
    }

    @Override // TO DO: add validation if the customer id is exist in customer id
    public Account updateAccount(Integer id, Account account) {
        // check if account id is exist in account table
        Account accountToUpdate = accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException(id));
        // check if account number is already exist
        if (accountRepository.existsByAccountNumber(account.getAccountNumber())) {
            throw new AccountNumberExistsException(account.getAccountNumber());
        }
        // if accountype id not null and account type id different current account type
        // id and custoemr id is null
        // get custoemr id from account to update
        // check if customer id with the new account type id is already exist
        if (account.getAccountTypeId() != null && account.getAccountTypeId() != accountToUpdate.getAccountTypeId()
                && account.getCustomerId() == null
                && accountRepository.existByAccountType(accountToUpdate.getCustomerId(), account.getAccountTypeId())) {
            throw new AccountTypeIsExistException(accountToUpdate.getCustomerId(), account.getAccountTypeId());
        }
        //// if accountype id not null and account type id different current account
        //// type id and customer id not null
        // get customer id from request body
        // check if customer id with the new account type id is already exist
        if (account.getAccountTypeId() != null && account.getAccountTypeId() != accountToUpdate.getAccountTypeId()
                && account.getCustomerId() != null &&
                accountRepository.existByAccountType(account.getCustomerId(), account.getAccountTypeId())) {
            throw new AccountTypeIsExistException(account.getCustomerId(), account.getAccountTypeId());
        }
        // check if account type id is exist in account type table
        if (account.getAccountTypeId() != null && !accountTypeRepository.existsById(account.getAccountTypeId())) {
            throw new AccountTypeIsNotExistException(account.getAccountTypeId());
        }
        // if customer id not null and account type id not null
        // check if customer id with account type id is exist
        if (account.getCustomerId() != null && account.getAccountTypeId() != null
                && accountRepository.existByAccountType(account.getCustomerId(), account.getAccountTypeId())) {
            throw new AccountTypeIsExistException(account.getCustomerId(), account.getAccountTypeId());
        }
        AccountType savedAccountType = accountTypeRepository.findById(account.getAccountTypeId())
                .orElseThrow(() -> new AccountTypeIsNotExistException(account.getAccountTypeId()));
        ;
        if (account.getBalance() != null) {
            accountToUpdate.setBalance(account.getBalance());
        }
        if (account.getAccountNumber() != null) {
            accountToUpdate.setAccountNumber(account.getAccountNumber());
        }
        if (account.getAccountTypeId() != null) {
            accountToUpdate.setAccountTypeId(account.getAccountTypeId());
            accountToUpdate.setAccountTypeName(savedAccountType.getAccountTypeName());
        }
        if (account.getCustomerId() != null) {
            accountToUpdate.setCustomerId(account.getCustomerId());
        }
        return accountRepository.save(accountToUpdate);
    }

    @Override
    public ArrayList<Account> getAccountByCustomerId(Integer id) {
        List<Account> allAccounts = accountRepository.findByCustomerId(id);
        if (allAccounts.isEmpty()) {
            throw new AccountNotFoundException(id);
        }
        for (Account account : allAccounts) {
            AccountType savedAccountType = accountTypeRepository.findById(account.getAccountTypeId()).get();
            account.setAccountTypeName(savedAccountType.getAccountTypeName());
        }
        return (ArrayList<Account>) allAccounts;
    }
}
