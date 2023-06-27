package pl.marcin.project.repository;

import pl.marcin.project.model.Cup;
import pl.marcin.project.model.Customer;
import pl.marcin.project.model.Purchase;

import java.util.ArrayList;
import java.util.List;

public class CustomerRepositoryListBased implements CustomerRepository {
    List<Customer> customers = new ArrayList<>();

    @Override
    public void saveCustomer(Customer customer) {
        customers.add(customer);
    }

    @Override
    public void updateCustomer(int customerId, Customer customer) {
        for (Customer searchedCustomer : customers) {
            if (searchedCustomer.getId() == customerId) {
                customers.add(searchedCustomer);
            } else {
                throw new RuntimeException("There is no Customer with id " + customerId);
            }
        }
    }

    @Override
    public void deleteCustomer(int customerId) {
        customers.removeIf(s -> s.getId() == customerId);

    }

    @Override
    public List<Customer> getAllCustomers() {
        return customers;
    }

    @Override
    public Customer findCustomer(int customerId) {
        for (Customer searchedCustomer : customers) {
            if (searchedCustomer.getId() == customerId) {
                return searchedCustomer;
            }
        }
        throw new RuntimeException("There is no such customer");
    }

    @Override
    public void updatePurchaseHistory(Purchase purchase, Customer customer) {
        for (Customer c : customers) {
            if (c.getId() == customer.getId()) {
                c.updatePurchaseHistory(purchase);
            }
        }
    }
}
