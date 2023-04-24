package pl.marcin.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.marcin.project.model.Cup;
import pl.marcin.project.model.Customer;

import java.util.List;

public interface CustomerRepository {
    public String saveCustomer(Customer customer);
    String updateCustomer(int customerId, Customer customer);
    public void deleteCustomer(int cupId);
    public List<Customer> getAllCustomers();
    public Customer findCustomer(int id);
}
