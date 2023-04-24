package pl.marcin.project.departments;

import pl.marcin.project.model.Customer;
import pl.marcin.project.repository.CustomerRepository;

import java.util.List;

public class Shop {
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
}
