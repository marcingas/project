package pl.marcin.project.repository;

import pl.marcin.project.model.Cup;
import pl.marcin.project.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerRepositoryListBased implements CustomerRepository{
    List<Customer> customers = new ArrayList<>();
    @Override
    public String saveCustomer(Customer customer) {
        customers.add(customer);
        return "Customer " + customer + " added";
    }

    @Override
    public String updateCustomer(int customerId, Customer customer) {
        for(Customer searchedCust : customers){
            if(searchedCust.getId()==customerId){
                customers.add(searchedCust);
            }else {
                throw new RuntimeException("There is no Customer with id " + customerId);
            }
        }
        return null;
    }

    @Override
    public void deleteCustomer(int customerId) {
        customers.removeIf(s -> s.getId()==customerId);

    }

    @Override
    public List<Customer> getAllCustomers() {
        return customers;
    }

    @Override
    public Customer findCustomer(int customerId) {
        for (Customer searchedCustomer : customers) {
            if (searchedCustomer.getId()==customerId) {
                return searchedCustomer;
            } else {
                throw new RuntimeException("There is no such cup");
            }
        }
        return null;
    }
}
