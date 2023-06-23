package pl.marcin.project.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.marcin.project.model.Customer;

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

}