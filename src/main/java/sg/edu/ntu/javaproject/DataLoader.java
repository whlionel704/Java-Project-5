package sg.edu.ntu.javaproject;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import sg.edu.ntu.javaproject.entity.AccountType;
import sg.edu.ntu.javaproject.entity.TransactionsType;
import sg.edu.ntu.javaproject.repository.AccountTypeRepository;
import sg.edu.ntu.javaproject.repository.TransactionsTypeRepository;

@Component
public class DataLoader {
    private AccountTypeRepository accountTypeRepository;
    private TransactionsTypeRepository transactionsTypeRepository;

    public DataLoader(AccountTypeRepository accountTypeRepository,
            TransactionsTypeRepository transactionsTypeRepository) {
        this.accountTypeRepository = accountTypeRepository;
        this.transactionsTypeRepository = transactionsTypeRepository;
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
    }
}
