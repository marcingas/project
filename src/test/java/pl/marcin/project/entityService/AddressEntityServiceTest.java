package pl.marcin.project.entityService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.marcin.project.database.AddressEntityRepository;
import pl.marcin.project.database.CustomerEntityRepository;
import pl.marcin.project.entity.AddressEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressEntityServiceTest {
    @Mock
    private AddressEntityRepository addressEntityRepository;
    @InjectMocks
    private AddressEntityService addressEntityService;


    @Test
    void getAddresses() {
        List<AddressEntity> mockList = new ArrayList<>();
        mockList.add(new AddressEntity(1L, "Kowalowa", 12, "Kraków", "34-444"));
        mockList.add(new AddressEntity(2L, "Kowalowa", 13, "Kraków", "34-444"));
        mockList.add(new AddressEntity(3L, "Kowalowa", 14, "Kraków", "34-444"));
        when(addressEntityRepository.findAll()).thenReturn(mockList);

        List<AddressEntity> result = addressEntityService.getAddresses();
        assertEquals(mockList.size(), result.size());
        verify(addressEntityRepository, times(1)).findAll();
    }

    @Test
    void getAddress() {
        AddressEntity addressEntity = new AddressEntity(1L,
                "Krakowska", 2, "Krakow", "30-300");
        when(addressEntityRepository.findById(addressEntity.getAddressId())).thenReturn(Optional.of(addressEntity));
        AddressEntity result = addressEntityService.getAddress(addressEntity.getAddressId());
        assertEquals(addressEntity, result);
        verify(addressEntityRepository, times(1)).findById(addressEntity.getAddressId());
    }

    @Test
    void getAddressThrowException() {
        AddressEntity addressEntity = new AddressEntity(1L,
                "Krakowska", 2, "Krakow", "30-300");
        Long notExistingId = 6L;
        when(addressEntityRepository.findById(notExistingId)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> addressEntityService.getAddress(notExistingId));
    }

    @Test
    void addAddress() {
    }

    @Test
    void updateAddress() {
    }

    @Test
    void deleteAddress() {
    }
}