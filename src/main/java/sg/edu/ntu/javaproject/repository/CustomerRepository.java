package sg.edu.ntu.javaproject.repository;

import org.springframework.data.jpa.repository.config.JpaRepositoryConfigExtension;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.ntu.javaproject.entity.Account;
import sg.edu.ntu.javaproject.entity.Customers;

import java.util.ArrayList;
import java.util.List;


@Repository
public interface CustomerRepository extends JpaRepository<Customers, Integer> {




    
} 
