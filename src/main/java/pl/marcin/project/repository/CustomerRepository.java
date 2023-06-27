package pl.marcin.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.marcin.project.model.Cup;
import pl.marcin.project.model.Customer;
import pl.marcin.project.model.Purchase;

import java.util.List;

public interface CustomerRepository {
    void saveCustomer(Customer customer);

    int updateCustomer(int customerId, Customer customer);

    void deleteCustomer(int cupId);
    List<Customer> getAllCustomers();
    Customer findCustomer(int id);
    void updatePurchaseHistory(Purchase purchase,Customer customer);
}
