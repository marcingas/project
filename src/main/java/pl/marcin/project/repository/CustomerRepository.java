package pl.marcin.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.marcin.project.model.Cup;
import pl.marcin.project.model.Customer;

import java.util.List;

public interface CustomerRepository {
    void saveCustomer(Customer customer);
    void updateCustomer(int customerId, Customer customer);
    void deleteCustomer(int cupId);
    List<Customer> getAllCustomers();
    Customer findCustomer(int id);
}