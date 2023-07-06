package pl.marcin.project.entityService;

import org.junit.jupiter.api.Assertions;
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

    @Autowired
    private CustomerEntityService customerEntityService;

    @Test
    public void shouldReturnCustomers() {

        //given

        CustomerEntity customer = new CustomerEntity("Janko", "Muzykant");

        //when

        customerEntityService.addCustomer(customer);

        //then

        Assertions.assertEquals(1, customerEntityService.getAllCustomers().size());
    }

    @Test
    public void shouldReturnTwoCustomers() {

        //given

        CustomerEntity expectedCustomer1 = new CustomerEntity("Janko", "Muzykant");
        CustomerEntity expectedCustomer2 = new CustomerEntity("Poul", "Krokiet");

        //when

        customerEntityService.addCustomer(expectedCustomer1);
        customerEntityService.addCustomer(expectedCustomer2);

        //then

        Assertions.assertEquals(2, customerEntityService.getAllCustomers().size());
    }

    @Test
    public void shouldThrowExceptionWhenCustomerNotFound() {

        //given

        CustomerEntity addedCustomer = new CustomerEntity("Janko", "Muzykant");
        CustomerEntity notAddedCustomer = new CustomerEntity("Piotrko", "Muzykant");

        //when

        customerEntityService.addCustomer(addedCustomer);

        //then
        Assertions.assertThrows(RuntimeException.class,
                () -> customerEntityService.getCustomer(notAddedCustomer.getCustomer_id()));
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

        CustomerEntity onlyCustomer = new CustomerEntity("Janko", "Muzykant");
        customerEntityService.addCustomer(onlyCustomer);

        //when
        customerEntityService.deleteCustomer(onlyCustomer.getCustomer_id());


        //then

        Assertions.assertEquals(0, customerEntityService.getAllCustomers().size());
    }

}


