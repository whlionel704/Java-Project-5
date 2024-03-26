package sg.edu.ntu.javaproject.controller;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import sg.edu.ntu.javaproject.entity.Customers;
import sg.edu.ntu.javaproject.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
@Slf4j
public class CustomerController {
    private CustomerService customerService;
    private ObjectMapper objectMapper;

    public CustomerController(CustomerService customerService, ObjectMapper objectMapper) {
        this.customerService = customerService;
        this.objectMapper = objectMapper;
    }

    @PostMapping("")
    public ResponseEntity<Customers> createCustomer(@RequestBody Customers customer) throws JsonProcessingException {
        Customers newcustomer = customerService.createCustomers(customer);
        String customerJson = objectMapper.writeValueAsString(newcustomer);
        log.info("New customer created : " + customerJson);
        return new ResponseEntity<>(newcustomer, HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<ArrayList<Customers>> getAllCustomers() throws JsonProcessingException {
        ArrayList<Customers> allCustomers = customerService.getAllCustomers();
        String customerJson = objectMapper.writeValueAsString(allCustomers);
        log.info("Retrieved all customers : " + customerJson);
        return new ResponseEntity<>(allCustomers, HttpStatus.OK);
    }

    @GetMapping({ "/{id}", "/{id}/" })
    public ResponseEntity<Customers> getCustomerById(@PathVariable Integer id) throws JsonProcessingException {
        Customers customer = customerService.getCustomerById(id);
        String customerJson = objectMapper.writeValueAsString(customer);
        log.info("Retrieved Customer By Id : " + customerJson);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @DeleteMapping({ "/{id}", "/{id}/" })
    public ResponseEntity<Customers> deleteCustomerById(@PathVariable Integer customer_id)
            throws JsonProcessingException {
        customerService.deleteCustomerById(customer_id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @PutMapping({ "/{id}", "/{id}/" })
    public ResponseEntity<Customers> updateCustomer(@PathVariable Integer id, @RequestBody Customers customer)
            throws JsonProcessingException {
        Customers customers = customerService.updateCustomer(id, customer);

        return new ResponseEntity<>(HttpStatus.OK);

    }
}