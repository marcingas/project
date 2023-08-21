package pl.marcin.project.entityService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.marcin.project.database.CustomerEntityRepository;
import pl.marcin.project.entity.AddressEntity;
import pl.marcin.project.entity.CustomerEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerEntityServiceTest {
    @Mock
    private CustomerEntityRepository customerEntityRepository;
    @InjectMocks
    private CustomerEntityService customerEntityService;

    @Test
    public void shouldReturnCustomerEntity() {
        AddressEntity addressEntity = new AddressEntity(1L, "Kowalowa",
                12, "Kraków", "30-215");
        CustomerEntity expectedCustomer = new CustomerEntity(1L, "Janko",
                "Muzykant", addressEntity);
        when(customerEntityRepository.save(expectedCustomer)).thenReturn(expectedCustomer);

        //when
        CustomerEntity customerEntity = customerEntityService.addCustomer(expectedCustomer);

        //then
        assertEquals(customerEntity, expectedCustomer);

    }

    @Test
    public void shouldThrowExceptionWhenCustomerNotFound() {
        AddressEntity addressEntity = new AddressEntity(1L, "Kowalowa",
                12, "Kraków", "30-215");
        CustomerEntity expectedCustomer = new CustomerEntity(1L, "Janko",
                "Muzykant", addressEntity);
        Long notAddedCustomerId = 2L;
        when(customerEntityRepository.findById(expectedCustomer.getCustomerId())).thenReturn(Optional.of(expectedCustomer));


        //when
        customerEntityService.addCustomer(expectedCustomer);

        //then
        Assertions.assertThrows(RuntimeException.class,
                () -> customerEntityService.getCustomer(notAddedCustomerId));
    }

    @Test
    public void shouldReturnUpdatedNameOfCustomer() {
        AddressEntity addressEntity = new AddressEntity(1L, "Kowalowa",
                12, "Kraków", "30-215");
        CustomerEntity addedCustomer = new CustomerEntity(1L, "Janko",
                "Muzykant", addressEntity);
        CustomerEntity updatedCustomer = new CustomerEntity(1L, "Polko", "Muzykant", addressEntity);
        Long addedCustomerId = 1L;
        when(customerEntityRepository.save(any())).then(returnsFirstArg());

        when(customerEntityRepository.findById(addedCustomerId)).thenReturn(Optional.of(updatedCustomer));

        //when
        customerEntityService.addCustomer(addedCustomer);
        customerEntityService.updateCustomer(updatedCustomer);

        //then
        assertEquals("Polko",
                customerEntityService.getCustomer(addedCustomerId).getName());
    }

    @Test
    public void shouldReturnZeroIfDeleted() {

        //given
//        customerEntityService.addCustomer(expectedCustomer);

        //when
//        customerEntityService.deleteCustomer(expectedCustomer.getCustomerId());


        //then
        assertEquals(0, customerEntityService.getAllCustomers().size());
    }

    @Test
    void getAllCustomers() {
        List<CustomerEntity> mockList = new ArrayList<>();
        mockList.add(new CustomerEntity());
        mockList.add(new CustomerEntity());
        mockList.add(new CustomerEntity());
        when(customerEntityRepository.findAll()).thenReturn(mockList);
        List<CustomerEntity> result = customerEntityService.getAllCustomers();
        assertEquals(mockList.size(), result.size());
        verify(customerEntityRepository, times(1)).findAll();
    }

    @Test
    void getCustomer() {
        CustomerEntity customerEntity = new CustomerEntity(1L, "Jan", "Kowalski",
                new AddressEntity());

        when(customerEntityRepository.findById(customerEntity.getCustomerId())).thenReturn(Optional.of(customerEntity));
        CustomerEntity result = customerEntityService.getCustomer(customerEntity.getCustomerId());

        assertEquals(customerEntity, result);
        verify(customerEntityRepository, times(1)).findById(customerEntity.getCustomerId());
    }

    @Test()
    void getCustomerNotPresentException() {
        CustomerEntity customerEntity = new CustomerEntity(1L, "Jan", "Kowalski",
                new AddressEntity());
        Long nonExistentId = 3L;
        when(customerEntityRepository.findById(customerEntity.getCustomerId())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> customerEntityService.getCustomer(nonExistentId));
    }
}


