package pl.marcin.project.service;

import pl.marcin.project.model.Cup;
import pl.marcin.project.model.Customer;
import pl.marcin.project.model.Purchase;
import pl.marcin.project.repository.CustomerRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CustomerService {


    CustomerRepository customerRepository;

    public String addCustomer(Customer customer) {
        customerRepository.saveCustomer(customer);
        return "Customer " + customer.getId() + " saved";
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

    public Purchase buyCup(Purchase purchase) {//change
        purchase.getCustomer().getPurchaseHistory().add(purchase);
        return purchase;
    }

}
