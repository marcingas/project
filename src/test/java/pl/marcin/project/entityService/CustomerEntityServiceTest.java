package pl.marcin.project.entityService;

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
        //given
        AddressEntity addressEntity = new AddressEntity(1L, "Kowalowa",
                12, "Kraków", "30-215");
        CustomerEntity expectedCustomer = new CustomerEntity(1L, "Janko",
                "Muzykant", addressEntity);
        when(customerEntityRepository.save(expectedCustomer)).thenReturn(expectedCustomer);

        //when
        CustomerEntity reslut = customerEntityService.addCustomer(expectedCustomer);

        //then
        assertEquals(expectedCustomer, reslut);
        verify(customerEntityRepository).save(expectedCustomer);
    }

    @Test
    public void shouldReturnUpdatedCustomer() {
        //given
        AddressEntity addressEntity = new AddressEntity();
        CustomerEntity existingCust = new CustomerEntity(1L, "Janko", "Spiewak", addressEntity);
        CustomerEntity updatedCust = new CustomerEntity(1L, "Polko", "Muzykant", addressEntity);

        when(customerEntityRepository.findById(existingCust.getCustomerId())).thenReturn(Optional.of(existingCust));
        when(customerEntityRepository.save(any(CustomerEntity.class))).thenReturn(updatedCust);

        //when
        CustomerEntity result = customerEntityService.updateCustomer(updatedCust);

        //then
        assertEquals(updatedCust, result);
        verify(customerEntityRepository).findById(existingCust.getCustomerId());
        verify(customerEntityRepository).save(updatedCust);
    }

    @Test
    public void shouldDeleteCustomer() {
        //given
        AddressEntity addressEntity = new AddressEntity();
        CustomerEntity existingCust = new CustomerEntity(1L, "Janko", "Spiewak", addressEntity);
        when(customerEntityRepository.findById(existingCust.getCustomerId())).thenReturn(Optional.of(existingCust));

        //when
        customerEntityService.deleteCustomer(existingCust.getCustomerId());

        //then
        verify(customerEntityRepository).findById(existingCust.getCustomerId());
        verify(customerEntityRepository).delete(existingCust);
    }

    @Test
    void getAllCustomers() {
        //given
        List<CustomerEntity> mockList = new ArrayList<>();
        mockList.add(new CustomerEntity());
        mockList.add(new CustomerEntity());
        mockList.add(new CustomerEntity());
        when(customerEntityRepository.findAll()).thenReturn(mockList);

        //when
        List<CustomerEntity> result = customerEntityService.getAllCustomers();

        //then
        assertEquals(mockList.size(), result.size());
        verify(customerEntityRepository, times(1)).findAll();
    }

    @Test
    void getCustomer() {
        //given
        CustomerEntity customerEntity = new CustomerEntity(1L, "Jan", "Kowalski",
                new AddressEntity());
        when(customerEntityRepository.findById(customerEntity.getCustomerId())).thenReturn(Optional.of(customerEntity));

        //when
        CustomerEntity result = customerEntityService.getCustomer(customerEntity.getCustomerId());

        //then
        assertEquals(customerEntity, result);
        verify(customerEntityRepository, times(1)).findById(customerEntity.getCustomerId());
    }

    @Test()
    void getCustomerNotPresentException() {
        //given
        CustomerEntity customerEntity = new CustomerEntity(1L, "Jan", "Kowalski",
                new AddressEntity());
        Long nonExistentId = 3L;
        when(customerEntityRepository.findById(customerEntity.getCustomerId())).thenReturn(Optional.empty());

        //when then:
        assertThrows(RuntimeException.class, () -> customerEntityService.getCustomer(nonExistentId));
    }
}


