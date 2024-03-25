package sg.edu.ntu.javaproject.service;

import sg.edu.ntu.javaproject.entity.User;


public interface UserService {
    User createUser(User user);
    User deleteUser(String id);
    /*
    Customer createCustomer(Customer customer);
    Customer getCustomer(Long id);
    ArrayList<Customer> getAllCustomers();
    Customer updateCustomer(Long id, Customer customer);
    void deleteCustomer(Long id);*/
}

