package sg.edu.ntu.javaproject.service;

import java.util.ArrayList;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import sg.edu.ntu.javaproject.entity.Customers;


public interface CustomerService {

    Customers createCustomers(Customers customer);

    ArrayList<Customers> getAllCustomers();

    Customers getCustomerById(Integer customer_id);

    void deleteCustomerById(Integer customer_id);

    Customers updateCustomer(Integer id, Customers customer);

    
}
 
