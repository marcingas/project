package pl.marcin.project.entityService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.marcin.project.entity.CustomerEntity;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class CustomerEntityServiceTest {
    CustomerEntity expectedCustomer = new CustomerEntity("Janko", "Muzykant");
    CustomerEntity expectedCustomer2 = new CustomerEntity("Polko", "Podolski");
    Long notAddedCustomerId = 100L;


    @Autowired
    private CustomerEntityService customerEntityService;


    @Test
    public void shouldReturnOneIfAddedCustomer() {

        //when
        customerEntityService.addCustomer(expectedCustomer);

        //then
        Assertions.assertEquals(1, customerEntityService.getAllCustomers().size());

    }

    @Test
    public void shouldThrowExceptionWhenCustomerNotFound() {


        //when
        customerEntityService.addCustomer(expectedCustomer);

        //then
        Assertions.assertThrows(RuntimeException.class,
                () -> customerEntityService.getCustomer(notAddedCustomerId));
    }

    @Test
    public void shouldReturnUpdatedNameOfCustomer() {

        //given
        CustomerEntity updatedCustomer = new CustomerEntity("Polko", "Muzykant");


        //when
        customerEntityService.updateCustomer(updatedCustomer);

        //then
        Assertions.assertEquals("Polko",
                customerEntityService.getCustomer(updatedCustomer.getCustomer_id()).getName());
    }

    @Test
    public void shouldReturnZeroIfDeleted() {

        //given
        customerEntityService.addCustomer(expectedCustomer);

        //when
        customerEntityService.deleteCustomer(expectedCustomer.getCustomer_id());


        //then
        Assertions.assertEquals(0, customerEntityService.getAllCustomers().size());
    }

}


