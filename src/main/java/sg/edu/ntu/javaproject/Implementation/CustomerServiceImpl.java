package sg.edu.ntu.javaproject.Implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import sg.edu.ntu.javaproject.Exception.CustomerNotFoundException;

import sg.edu.ntu.javaproject.entity.Customers;
import sg.edu.ntu.javaproject.repository.CustomerRepository;
import sg.edu.ntu.javaproject.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customers createCustomers(Customers customer) {
        return customerRepository.save(customer);
    }

    @Override
    public ArrayList<Customers> getAllCustomers() {
        // TODO Auto-generated method stub
        List<Customers> allCustomers = customerRepository.findAll();
        // throw new UnsupportedOperationException("Unimplemented method
        // 'getAllCustomers'");
        return (ArrayList<Customers>) allCustomers;
    }

    @Override
    public Customers getCustomerById(Integer customer_id) {
        return customerRepository.findById(customer_id).orElseThrow(() -> new CustomerNotFoundException(customer_id));

    }

    @Override
    public void deleteCustomerById(Integer customer_id) {
        if (customerRepository.existsById(customer_id)) {
            throw new CustomerNotFoundException(customer_id);
        }
        customerRepository.deleteById(customer_id);

    }

    @Override
    public Customers updateCustomer(Integer customer_id, Customers customer) {
        Customers customerToUpdate = customerRepository.findById(customer_id)
                .orElseThrow(() -> new CustomerNotFoundException(customer_id));

        if (customerToUpdate.getCustomerName() != null) {
            customerToUpdate.setCustomerName(customer.getCustomerName());
        }
        if (customerToUpdate.getCustomerEmail() != null) {
            customerToUpdate.setCustomerEmail(customer.getCustomerEmail());
        }
        if (customerToUpdate.getCustomerContact() != null) {
            customerToUpdate.setCustomerContact(customer.getCustomerContact());
        }
        if (customerToUpdate.getCustomerAddress() != null) {
            customerToUpdate.setCustomerAddress(customer.getCustomerAddress());
        }

        return customerRepository.save(customerToUpdate);

    }

}