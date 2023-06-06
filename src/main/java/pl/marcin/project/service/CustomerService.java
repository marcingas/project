package pl.marcin.project.service;

import lombok.extern.slf4j.Slf4j;

import pl.marcin.project.model.Customer;
import pl.marcin.project.model.Purchase;
import pl.marcin.project.repository.CustomerRepository;


import java.util.List;

@Slf4j
public class CustomerService {
    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public int addCustomer(Customer customer) {
        customerRepository.saveCustomer(customer);
        log.info("Customer " + customer.getId() + " saved");
        return customer.getId();
    }

    public int updateCustomer(int customerId, Customer customer) {
        customerRepository.updateCustomer(customerId, customer);
        log.info("Customer with id " + customerId + " updated");
        return customer.getId();
    }

    public int deleteCustomer(int customerId) {
        customerRepository.deleteCustomer(customerId);
        log.info("Customer with id " + customerId + " deleted");
        return -1;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.getAllCustomers();
    }

    public Customer getCustomer(int customerId) {
        return customerRepository.findCustomer(customerId);
    }

    public int updatePurchaseHistory(Purchase purchase, Customer customer) {
        customerRepository.updatePurchaseHistory(purchase, customer);
        log.info("Customers id: " + customer.getId() + " purchase history updated");
        return customer.getId();
    }


}
