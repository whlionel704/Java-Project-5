package sg.edu.ntu.javaproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.ntu.javaproject.entity.Account;

import java.util.ArrayList;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    boolean existsByAccountNumber(Integer accountNumber);

    ArrayList<Account> findByCustomerId(Integer customerId);

    @Query(value = "select case when count(a.id) > 0 then true else false end from Account a where a.customerId = :customerId and a.accountTypeId = :accountTypeId")
    boolean existByAccountType(@Param("customerId") Integer customerId, @Param("accountTypeId") Integer accountTypeId);

    @Query(value = "select case when count(a.id) > 0 then true else false end from Account a where a.id = :id and a.accountTypeId = :accountTypeId")
    boolean existByAccountId(@Param("id") Integer accountId, @Param("accountTypeId") Integer accountTypeId);

}
