package pl.marcin.project.repository;

import pl.marcin.project.model.Customer;
import pl.marcin.project.model.Purchase;

import java.util.ArrayList;
import java.util.List;

public class CustomerRepositoryListBased implements CustomerRepository {
    List<Customer> customers = new ArrayList<>();

    @Override
    public int saveCustomer(Customer customer) {
        customers.add(customer);
        return customer.getCustomerId();
    }

    @Override
    public int updateCustomer(int customerId, Customer customer) {
        for (var searchedCustomer : customers) {
            if (searchedCustomer.getCustomerId() == customerId) {
                searchedCustomer.setName(customer.getName());
                searchedCustomer.setSurname(customer.getSurname());
                searchedCustomer.setAddress(customer.getAddress());
                return customerId;
            }
        }
        throw new RuntimeException("There is no Customer with id " + customerId);
    }

    @Override
    public boolean deleteCustomer(int customerId) {
        return customers.removeIf(s -> s.getCustomerId() == customerId);

    }

    @Override
    public List<Customer> getAllCustomers() {
        return customers;
    }

    @Override
    public Customer findCustomer(int customerId) {
        for (Customer searchedCustomer : customers) {
            if (searchedCustomer.getCustomerId() == customerId) {
                return searchedCustomer;
            }
        }
        throw new RuntimeException("There is no such customer");
    }
}
