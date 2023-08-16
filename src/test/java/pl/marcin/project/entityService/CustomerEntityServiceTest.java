package pl.marcin.project.entityService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.marcin.project.entity.AddressEntity;
import pl.marcin.project.entity.CustomerEntity;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class CustomerEntityServiceTest {
    AddressEntity addressEntity = new AddressEntity(1L, "Kowalowa", 12, "Kraków", "30-215");
    CustomerEntity expectedCustomer = new CustomerEntity(1L, "Janko", "Muzykant", addressEntity);
    CustomerEntity expectedCustomer2 = new CustomerEntity(2L, "Polko", "Podolski", addressEntity);
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
        AddressEntity addressEntity = new AddressEntity(1L, "Kowalowa", 12, "Kraków", "30-215");
        CustomerEntity updatedCustomer = new CustomerEntity(1L, "Janko", "Muzykant", addressEntity);


        //when
        customerEntityService.updateCustomer(updatedCustomer);

        //then
        Assertions.assertEquals("Polko",
                customerEntityService.getCustomer(updatedCustomer.getCustomerId()).getName());
    }

    @Test
    public void shouldReturnZeroIfDeleted() {

        //given
        customerEntityService.addCustomer(expectedCustomer);

        //when
        customerEntityService.deleteCustomer(expectedCustomer.getCustomerId());


        //then
        Assertions.assertEquals(0, customerEntityService.getAllCustomers().size());
    }

}


