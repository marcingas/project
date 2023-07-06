package pl.marcin.project.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.marcin.project.model.Customer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomerRepositoryListBasedTest {
    private CustomerRepository customerRepository = new CustomerRepositoryListBased();

    @Test
    public void shouldReturnCustomerById() {
        //given
        int id = 1;
        Customer expectedCustomer = Customer.builder().id(id).build();
        customerRepository.saveCustomer(expectedCustomer);

        //when
        Customer customer = customerRepository.findCustomer(id);

        //then
        Assertions.assertEquals(id, customer.getId());
    }

    @Test
    public void shouldReturnCustomerWithId2() {
        //given
        int id = 1;
        int id2 = 2;
        Customer expectedCustomer1 = Customer.builder().id(id).build();
        Customer expectedCustomer2 = Customer.builder().id(id2).build();
        customerRepository.saveCustomer(expectedCustomer1);
        customerRepository.saveCustomer(expectedCustomer2);

        //when
        Customer customer = customerRepository.findCustomer(id2);

        //then
        Assertions.assertEquals(id2, customer.getId());
    }

    @Test
    public void shouldThrowExceptionIfNotFindCustomerById() {
        //given
        int id = 1;
        int id2 = 2;
        //when:
        Customer customer2 = Customer.builder().id(id2).build();
        customerRepository.saveCustomer(customer2);

        //then:
        assertThrows(RuntimeException.class, () -> {
            customerRepository.findCustomer(id);
        });
    }

    @Test
    public void shouldThrowExceptionMessageNoSuchCustomerById() {
        //given
        int id = 1;

        //when
        Throwable exception = assertThrows(RuntimeException.class, () -> {
            customerRepository.findCustomer(id);
        });

        //then
        Assertions.assertEquals("There is no such customer", exception.getMessage());
    }

    @Test
    public void shouldReturnListOfAllCustomers() {
        //given
        int id = 1;
        int id2 = 2;
        Customer customer1 = Customer.builder().id(id).build();
        Customer customer2 = Customer.builder().id(id2).build();
        customerRepository.saveCustomer(customer1);
        customerRepository.saveCustomer(customer2);
        //when
        List<Customer> list = customerRepository.getAllCustomers();
        //then
        Assertions.assertEquals(List.of(customer1, customer2), list);
    }

    @Test
    public void shouldReturnTrueIfDeleted() {
        //given
        int id = 1;
        Customer customer1 = Customer.builder().id(id).build();
        customerRepository.saveCustomer(customer1);
        //when
        boolean ifDeleted = customerRepository.deleteCustomer(1);
        //then
        Assertions.assertEquals(true, ifDeleted);
    }

    @Test
    public void shouldReturnFalseIfNotDeleted() {
        //given
        int id = 1;
        Customer customer1 = Customer.builder().id(id).build();
        customerRepository.saveCustomer(customer1);
        //when
        boolean ifDeleted = customerRepository.deleteCustomer(2);
        //then
        Assertions.assertEquals(false, ifDeleted);
    }

    @Test
    public void shouldReturnCustomerId() {
        //given
        int id = 1;
        Customer customer1 = Customer.builder()
                .id(id)
                .name("Jan")
                .surname("Kowalski")
                .address("Kraków")
                .build();
        //when
        int returnedId = customerRepository.saveCustomer(customer1);
        //then
        Assertions.assertEquals(id, returnedId);
    }

    @Test
    public void shouldEqualToSavedCustomer() {
        //given
        int id = 1;
        Customer customer1 = Customer.builder()
                .id(id)
                .name("Jan")
                .surname("Kowalski")
                .address("Kraków")
                .build();
        //when
        customerRepository.saveCustomer(customer1);
        //then
        Assertions.assertEquals(customer1, customerRepository.findCustomer(1));
    }

    @Test
    public void shouldEqualToUpdatedCustomer() {
        //given
        int id = 1;
        Customer customer1 = Customer.builder().id(id).build();
        customerRepository.saveCustomer(customer1);

        //when
        Customer updatedCustomer = Customer.builder()
                .id(1)
                .name("Jacek")
                .surname("Placek")
                .address("Pułtusk")
                .build();
        customerRepository.updateCustomer(id, updatedCustomer);
        //then
        Assertions.assertEquals(updatedCustomer, customerRepository.findCustomer(id));
    }

    @Test
    public void shouldThrowException() {
        //given
        int id = 1;
        Customer customer1 = Customer.builder().id(id).build();
        customerRepository.saveCustomer(customer1);

        //when
        Customer updatedCustomer = Customer.builder()
                .id(2)
                .name("Jacek")
                .surname("Placek")
                .address("Pułtusk")
                .build();

        //then
        assertThrows(RuntimeException.class, () -> {
            customerRepository.updateCustomer(2, updatedCustomer);
        });
    }

    @Test
    public void shouldReturnUpdatedCustomerId() {
        //given
        int id = 1;
        Customer customer = Customer.builder().id(id).name("Janko").build();
        customerRepository.saveCustomer(customer);

        //when
        Customer updatedCustomer = Customer.builder()
                .id(id)
                .name("Jan")
                .surname("Kowalski")
                .address("Kraków")
                .build();

        int returnedId = customerRepository.updateCustomer(id, updatedCustomer);
        //then
        Assertions.assertEquals(id, returnedId);
    }

}