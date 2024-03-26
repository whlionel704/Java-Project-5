package sg.edu.ntu.javaproject.Implementation;


import java.util.ArrayList;
import java.util.List;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import sg.edu.ntu.javaproject.Exception.AccountNotFoundException;
import sg.edu.ntu.javaproject.Exception.AccountNumberExistsException;
import sg.edu.ntu.javaproject.Exception.AccountTypeIsExistException;
import sg.edu.ntu.javaproject.Exception.AccountTypeIsNotExistException;
import sg.edu.ntu.javaproject.Exception.NullException;
import sg.edu.ntu.javaproject.entity.Account;
import sg.edu.ntu.javaproject.entity.AccountType;
import sg.edu.ntu.javaproject.entity.Customers;
import sg.edu.ntu.javaproject.repository.AccountRepository;
import sg.edu.ntu.javaproject.repository.AccountTypeRepository;
import sg.edu.ntu.javaproject.repository.CustomerRepository;
import sg.edu.ntu.javaproject.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository;
    private AccountTypeRepository accountTypeRepository;
    private CustomerRepository customerRepository;
    // private SecurityContextHolder securityContextHolder;
    // private Optional<SecurityContextHolder> securityContextHolder;

    public AccountServiceImpl(AccountRepository accountRepository, AccountTypeRepository accountTypeRepository,
            CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.accountTypeRepository = accountTypeRepository;
        this.customerRepository = customerRepository;
        // this.securityContextHolder = securityContextHolder;
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
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String username = authentication.getName();
        Customers customer = customerRepository.findByCustomerEmail(username);

        if (customer.getCustomerRole() == 1) {
            List<Account> allAccounts = accountRepository.findAll();
            for (Account account : allAccounts) {
                AccountType savedAccountType = accountTypeRepository.findById(account.getAccountTypeId()).get();
                account.setAccountTypeName(savedAccountType.getAccountTypeName());
            }
            return (ArrayList<Account>) allAccounts;
        } else {
            List<Account> customerAccounts = accountRepository.findByCustomerId(customer.getCustomerId());
            for (Account account : customerAccounts) {
                AccountType savedAccountType = accountTypeRepository.findById(account.getAccountTypeId()).get();
                account.setAccountTypeName(savedAccountType.getAccountTypeName());
            }
            return (ArrayList<Account>) customerAccounts;

        }
    }

    @Override // TO DO: add validation if the customer id is exist in customer id
    public Account updateAccount(Integer id, Account account) {
        Account accountToUpdate = accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException(id));
        if (accountRepository.existsByAccountNumber(account.getAccountNumber())) {
            throw new AccountNumberExistsException(account.getAccountNumber());
        }
        if (account.getAccountTypeId() != null && account.getAccountTypeId() != accountToUpdate.getAccountTypeId()
                && account.getCustomerId() == null
                && accountRepository.existByAccountType(accountToUpdate.getCustomerId(), account.getAccountTypeId())) {
            throw new AccountTypeIsExistException(accountToUpdate.getCustomerId(), account.getAccountTypeId());
        }
        if (account.getAccountTypeId() != null && account.getAccountTypeId() != accountToUpdate.getAccountTypeId()
                && account.getCustomerId() != null &&
                accountRepository.existByAccountType(account.getCustomerId(), account.getAccountTypeId())) {
            throw new AccountTypeIsExistException(account.getCustomerId(), account.getAccountTypeId());
        }
        if (account.getAccountTypeId() != null && !accountTypeRepository.existsById(account.getAccountTypeId())) {
            throw new AccountTypeIsNotExistException(account.getAccountTypeId());
        }
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
