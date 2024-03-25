package sg.edu.ntu.javaproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.ntu.javaproject.entity.Transactions;

public interface TransactionsRepository extends JpaRepository<Transactions, Integer> {

}
