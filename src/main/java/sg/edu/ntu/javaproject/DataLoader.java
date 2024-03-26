package sg.edu.ntu.javaproject;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import sg.edu.ntu.javaproject.entity.Account;
import sg.edu.ntu.javaproject.entity.AccountType;
import sg.edu.ntu.javaproject.entity.TransactionsType;
import sg.edu.ntu.javaproject.repository.AccountRepository;
import sg.edu.ntu.javaproject.repository.AccountTypeRepository;
import sg.edu.ntu.javaproject.repository.TransactionsTypeRepository;

@Component
public class DataLoader {
    private AccountTypeRepository accountTypeRepository;
    private TransactionsTypeRepository transactionsTypeRepository;
    private AccountRepository accountRepository;

    public DataLoader(AccountTypeRepository accountTypeRepository,
            TransactionsTypeRepository transactionsTypeRepository, AccountRepository accountRepository) {
        this.accountTypeRepository = accountTypeRepository;
        this.transactionsTypeRepository = transactionsTypeRepository;
        this.accountRepository = accountRepository;

    }

    @PostConstruct
    public void loadData() {
        accountTypeRepository.deleteAll();
        accountTypeRepository.save(new AccountType("savings"));
        accountTypeRepository.save(new AccountType("current"));
        transactionsTypeRepository.deleteAll();
        transactionsTypeRepository.save(new TransactionsType("deposit"));
        transactionsTypeRepository.save(new TransactionsType("withdraw"));
        transactionsTypeRepository.save(new TransactionsType("transfer"));
        accountRepository.deleteAll();
        accountRepository.save(new Account(121, 100, 1, 1));
        accountRepository.save(new Account(221, 100, 2, 1));
        accountRepository.save(new Account(122, 1000, 1, 2));
        accountRepository.save(new Account(222, 1001, 2, 2));
    }
}
