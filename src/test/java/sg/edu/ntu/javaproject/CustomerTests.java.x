package sg.edu.ntu.javaproject;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import sg.edu.ntu.javaproject.controller.CustomerController;
//import sg.edu.ntu.javaproject.entity.Account;
import sg.edu.ntu.javaproject.entity.Customers;
import sg.edu.ntu.javaproject.service.AccountService;
import sg.edu.ntu.javaproject.service.CustomerService;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)

public class CustomerTests {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CustomerService customerService;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private CustomerController customerController;

    @Test
    public void testCreateCustomer() throws Exception {
        Customers customer = new Customers();
        customer.setCustomer_name("Test Customer");

        when(customerService.createCustomers(customer)).thenReturn(customer);

        mockMvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"name\": \"Test Customer\" }"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test Customer"));
    }

    @Test
    public void testGetAllCustomers() throws Exception {
        ArrayList<Customers> customersList = new ArrayList<>();
        Customers customer1 = new Customers();
        customer1.setCustomer_name("Customer 1");
        Customers customer2 = new Customers();
        customer2.setCustomer_name("Customer 2");

        customersList.add(customer1);
        customersList.add(customer2);

        when(customerService.getAllCustomers()).thenReturn(customersList);

        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Customer 1"))
                .andExpect(jsonPath("$[1].name").value("Customer 2"));
    }

    @Test
    public void testGetCustomerById() throws Exception {
        Customers customer = new Customers();
        customer.setCustomer_name("Test Customer");
        // Set other fields as needed
        customer.setCustomer_id(1);

        when(customerService.getCustomerById(1)).thenReturn(customer);

        mockMvc.perform(get("/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customer_name").value("Test Customer"));
    }
    
    @Test
    public void testDeleteCustomerById() throws Exception {
        Integer customerId = 1;
        doNothing().when(customerService).deleteCustomerById(customerId);

        mockMvc.perform(delete("/customers/{customerId}", customerId))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        Customers customer = new Customers();
        customer.setCustomer_name("Updated Customer");
        // Set other fields as needed

        when(customerService.updateCustomer(1, customer)).thenReturn(customer);

        mockMvc.perform(put("/customers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"customer_name\": \"Updated Customer\" }"))
                .andExpect(status().isOk());
    }
}