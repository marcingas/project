package pl.marcin.project.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.marcin.project.model.Address;
import pl.marcin.project.model.Customer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomerRepositoryListBasedTest {
    private int expectedCustomerId = 1;
    private int expectedCustomerId2 = 2;
    private int notExistingCustomerId = 3;
    private Customer expectedCustomer = Customer.builder().id(expectedCustomerId)
            .name("John").surname("Kowalski").address(
                    new Address(1, "krakowska", 1, "Krakow", "34-200")).build();
    private Customer expectedCustomer2 = Customer.builder().id(expectedCustomerId2)
            .name("Poul").surname("Krakowski").address(new Address(1, "krakowska", 1, "Krakow", "34-200")).build();
    private CustomerRepository customerRepository = new CustomerRepositoryListBased();

    @Test
    public void shouldReturnCustomerById() {
        //given

        customerRepository.saveCustomer(expectedCustomer);

        //when

        Customer customer = customerRepository.findCustomer(expectedCustomerId);

        //then

        Assertions.assertEquals(expectedCustomerId, customer.getId());
    }

    @Test
    public void shouldReturnCustomerWithId2() {

        //given

        customerRepository.saveCustomer(expectedCustomer);
        customerRepository.saveCustomer(expectedCustomer2);

        //when

        Customer customer = customerRepository.findCustomer(expectedCustomerId2);

        //then

        Assertions.assertEquals(expectedCustomerId2, customer.getId());
    }

    @Test
    public void shouldThrowExceptionIfNotFindCustomerById() {

        //given

        customerRepository.saveCustomer(expectedCustomer);

        //when

        var exception = assertThrows(RuntimeException.class, () -> {
            customerRepository.findCustomer(notExistingCustomerId);
        });
        //then

        Assertions.assertEquals("There is no such customer", exception.getMessage());
    }


    @Test
    public void shouldReturnListOfAllCustomers() {
        //given

        customerRepository.saveCustomer(expectedCustomer);
        customerRepository.saveCustomer(expectedCustomer2);

        //when

        List<Customer> list = customerRepository.getAllCustomers();

        //then

        Assertions.assertEquals(List.of(expectedCustomer, expectedCustomer2), list);
    }

    @Test
    public void shouldReturnTrueIfDeletedFalseIfNot() {

        //given

        customerRepository.saveCustomer(expectedCustomer);

        //when

        boolean ifDeleted = customerRepository.deleteCustomer(expectedCustomerId);

        boolean ifNotDeleted = customerRepository.deleteCustomer(notExistingCustomerId);

        //then

        Assertions.assertTrue(ifDeleted);
        Assertions.assertFalse(ifNotDeleted);
    }

    @Test
    public void shouldReturnCustomerIdAndEqualToCustomer() {

        //given

        int returnedId = customerRepository.saveCustomer(expectedCustomer);

        //then

        Assertions.assertEquals(expectedCustomerId, returnedId);
        Assertions.assertEquals(expectedCustomer, customerRepository.findCustomer(1));
    }

    @Test
    public void shouldEqualToUpdatedCustomerAndReturnedId() {
        //given

        customerRepository.saveCustomer(expectedCustomer);

        //when

        Customer updatedCustomer = Customer.builder()
                .id(expectedCustomerId)
                .name("Jacek")
                .surname("Placek")
                .address(new Address(1, "krakowska", 1, "Krakow", "34-200"))
                .build();

        int returnedId = customerRepository.updateCustomer(expectedCustomerId, updatedCustomer);

        //then

        Assertions.assertEquals(updatedCustomer, customerRepository.findCustomer(expectedCustomerId));
        Assertions.assertEquals(expectedCustomerId, returnedId);
    }

    @Test
    public void shouldThrowException() {
        //given

        customerRepository.saveCustomer(expectedCustomer);

        //when

        Customer updatedCustomer = Customer.builder()
                .id(expectedCustomerId)
                .name("Jacek")
                .surname("Placek")
                .address(new Address(1, "krakowska", 1, "Krakow", "34-200"))
                .build();

        //then

        assertThrows(RuntimeException.class, () -> {
            customerRepository.updateCustomer(notExistingCustomerId, updatedCustomer);
        });
    }
}