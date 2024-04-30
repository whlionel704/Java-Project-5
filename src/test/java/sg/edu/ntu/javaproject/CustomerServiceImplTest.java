package sg.edu.ntu.javaproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

//import java.security.Principal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.core.Authentication;

import sg.edu.ntu.javaproject.Exception.CustomerNotFoundException;
import sg.edu.ntu.javaproject.Implementation.CustomerServiceImpl;
import sg.edu.ntu.javaproject.entity.Customers;
import sg.edu.ntu.javaproject.repository.CustomerRepository;

@SpringBootTest
public class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    CustomerServiceImpl customerService;
    BCryptPasswordEncoder passwordEncoder;

    public CustomerServiceImplTest() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Test
    public void createCustomerTest() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        CustomerServiceImpl customerService = new CustomerServiceImpl(customerRepository, passwordEncoder);
        Customers customer = Customers.builder().customerName("testCustomer").customerEmail("customermail.com")
                .customerAddress("customer address").customerRole(1).password("123456").build();
        when((customerRepository.save(customer))).thenReturn(customer);
        Customers savedCustomer = customerService.createCustomers(customer);
        assertEquals(customer, savedCustomer, "the saved customer should be the same as the new customer");
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    public void getCustomerTest() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        CustomerServiceImpl customerService = new CustomerServiceImpl(customerRepository, passwordEncoder);
        Customers customer = Customers.builder().customerName("testCustomer").customerEmail("customermail.com")
                .customerAddress("customer address").customerRole(1).password("123456").build();
        Integer customerId = 1;
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        Customers getCustomer = customerService.getCustomerByIdNoPassword(customerId);
        assertEquals(customer, getCustomer);
    }

    @Test
    public void customerNotFoundTest() {
        Integer customerId = 1;
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());
        assertThrows(CustomerNotFoundException.class, () -> customerService.getCustomerByIdNoPassword(customerId));
    }

    @Test
    public void customerPasswordEncodedTest() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        CustomerServiceImpl customerService = new CustomerServiceImpl(customerRepository, passwordEncoder);
        Customers customer = Customers.builder().customerName("testCustomer").customerEmail("customermail.com")
                .customerAddress("customer address").customerRole(1).password("123456").build();
        String plainPassword = "123456";
        when((customerRepository.save(customer))).thenReturn(customer);
        Customers savedCustomer = customerService.createCustomers(customer);
        assertNotEquals(savedCustomer.getPassword(), plainPassword);
    }
}
