package sg.edu.ntu.javaproject.Implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import sg.edu.ntu.javaproject.Exception.AccountNotFoundException;
import sg.edu.ntu.javaproject.Exception.AccountNumberExistsException;
import sg.edu.ntu.javaproject.Exception.AccountTypeIsExistException;
import sg.edu.ntu.javaproject.Exception.AccountTypeIsNotExistException;
import sg.edu.ntu.javaproject.Exception.NullException;
import sg.edu.ntu.javaproject.entity.Account;
import sg.edu.ntu.javaproject.entity.AccountType;
import sg.edu.ntu.javaproject.repository.AccountRepository;
import sg.edu.ntu.javaproject.repository.AccountTypeRepository;
import sg.edu.ntu.javaproject.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository;
    private AccountTypeRepository accountTypeRepository;

    public AccountServiceImpl(AccountRepository accountRepository, AccountTypeRepository accountTypeRepository) {
        this.accountRepository = accountRepository;
        this.accountTypeRepository = accountTypeRepository;
    }

    @Override // TO DO - add validation: if customer id is exists in customer table
    public Account createAccount(Account account) {
        if (account.getCustomerId() == null || account.getAccountTypeId() == null
                || account.getAccountNumber() == null) {
            StringBuilder nullValue = new StringBuilder();
            if (account.getCustomerId() == null) {
                nullValue.append("CustomerID, ");
            }
            if (account.getAccountTypeId() == null) {
                nullValue.append("Account Type ID, ");
            }
            if (account.getAccountNumber() == null) {
                nullValue.append("Account Number");
            }
            throw new NullException(nullValue);
        }
        if (accountRepository.existsByAccountNumber(account.getAccountNumber())) {
            throw new AccountNumberExistsException(account.getAccountNumber());
        }
        if (accountRepository.existByAccountType(account.getCustomerId(), account.getAccountTypeId())) {
            throw new AccountTypeIsExistException(account.getCustomerId(), account.getAccountTypeId());
        }
        if (account.getBalance() == null) {
            account.setBalance(0);
        }
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
        return (ArrayList<Account>) allAccounts;
    }

    @Override // TO DO: add validation if the customer id is exist in customer id
    public Account updateAccount(Integer id, Account account) {
        Account accountToUpdate = accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException(id));
        if (accountRepository.existsByAccountNumber(account.getAccountNumber())) {
            throw new AccountNumberExistsException(account.getAccountNumber());
        }
        if (account.getAccountTypeId() != null && account.getCustomerId() == null
                && accountRepository.existByAccountId(accountToUpdate.getCustomerId(), account.getAccountTypeId())) {
            throw new AccountTypeIsExistException(accountToUpdate.getCustomerId(), account.getAccountTypeId());
        }
        if (account.getAccountTypeId() != null && account.getCustomerId() != null &&
                accountRepository.existByAccountType(account.getCustomerId(), account.getAccountTypeId())) {
            throw new AccountTypeIsExistException(account.getCustomerId(), account.getAccountTypeId());
        }
        if (account.getAccountTypeId() != null && !accountTypeRepository.existsById(account.getAccountTypeId())) {
            throw new AccountTypeIsNotExistException(account.getAccountTypeId());
        }
        AccountType savedAccountType = accountTypeRepository.findById(id).get();
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
        return (ArrayList<Account>) allAccounts;
    }

}
