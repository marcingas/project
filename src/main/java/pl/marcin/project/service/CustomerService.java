package pl.marcin.project.service;

import lombok.extern.slf4j.Slf4j;
import pl.marcin.project.model.Cup;
import pl.marcin.project.model.Customer;
import pl.marcin.project.model.Purchase;
import pl.marcin.project.repository.CustomerRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
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

    public String updateCustomer(int customerId, Customer customer) {
        customerRepository.updateCustomer(customerId, customer);
        return "Customer with id " + customerId + " updated";
    }

    public String deleteCustomer(int customerId) {
        customerRepository.deleteCustomer(customerId);
        return "Customer with id " + customerId + " deleted";
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.getAllCustomers();
    }

    public Customer getCustomer(int customerId) {
        return customerRepository.findCustomer(customerId);
    }

    public String updatePurchaseHistory(Purchase purchase, Customer customer){
        customerRepository.updatePurchaseHistory(purchase,customer);
        return "History updated";
    }


}
