package pl.marcin.project.entityService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.marcin.project.database.AddressEntityRepository;
import pl.marcin.project.entity.AddressEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressEntityServiceTest {
    @Mock
    private AddressEntityRepository addressEntityRepository;
    @InjectMocks
    private AddressEntityService addressEntityService;


    @Test
    void getAddresses() {
        //given
        List<AddressEntity> mockList = new ArrayList<>();
        mockList.add(new AddressEntity(1L, "Kowalowa", 12, "Kraków", "34-444"));
        mockList.add(new AddressEntity(2L, "Kowalowa", 13, "Kraków", "34-444"));
        mockList.add(new AddressEntity(3L, "Kowalowa", 14, "Kraków", "34-444"));
        when(addressEntityRepository.findAll()).thenReturn(mockList);

        //when
        List<AddressEntity> result = addressEntityService.getAddresses();

        //then
        assertEquals(mockList.size(), result.size());
        verify(addressEntityRepository, times(1)).findAll();
    }

    @Test
    void getAddress() {
        //given
        AddressEntity addressEntity = new AddressEntity(1L,
                "Krakowska", 2, "Krakow", "30-300");
        when(addressEntityRepository.findById(addressEntity.getAddressId())).thenReturn(Optional.of(addressEntity));

        //when
        AddressEntity result = addressEntityService.getAddress(addressEntity.getAddressId());

        //then
        assertEquals(addressEntity, result);
        verify(addressEntityRepository, times(1)).findById(addressEntity.getAddressId());
    }

    @Test
    void getAddressThrowException() {
        //given
        AddressEntity addressEntity = new AddressEntity(1L,
                "Krakowska", 2, "Krakow", "30-300");
        Long notExistingId = 6L;
        when(addressEntityRepository.findById(notExistingId)).thenReturn(Optional.empty());

        //when then
        assertThrows(RuntimeException.class, () -> addressEntityService.getAddress(notExistingId));
    }

    @Test
    void addAddress() {
        //given
        AddressEntity addressEntity = new AddressEntity(1L,
                "Krakowska", 2, "Krakow", "30-300");
        when(addressEntityRepository.save(addressEntity)).thenReturn(addressEntity);

        //when
        AddressEntity result = addressEntityService.addAddress(addressEntity);

        //then
        assertEquals(addressEntity, result);
        verify(addressEntityRepository).save(addressEntity);
    }

    @Test
    void updateAddress() {
        //given
        AddressEntity existingAddress = new AddressEntity(1L,
                "Krakowska", 2, "Krakow", "30-300");
        AddressEntity updatedAddress = new AddressEntity(1L,
                "Zabierzowska", 5, "Krakow", "31-300");

        when(addressEntityRepository.findById(existingAddress.getAddressId())).thenReturn(Optional.of(existingAddress));
        when(addressEntityRepository.save(updatedAddress)).thenReturn(updatedAddress);

        //when
        AddressEntity result = addressEntityService.updateAddress(updatedAddress);

        //then
        assertEquals(updatedAddress, result);
        verify(addressEntityRepository).findById(existingAddress.getAddressId());
        verify(addressEntityRepository).save(updatedAddress);
    }

    @Test
    void deleteAddress() {
        //given
        AddressEntity existingAddress = new AddressEntity(1L,
                "Krakowska", 2, "Krakow", "30-300");
        when(addressEntityRepository.findById(existingAddress.getAddressId())).thenReturn(Optional.of(existingAddress));

        //when
        addressEntityService.deleteAddress(existingAddress.getAddressId());

        //then
        verify(addressEntityRepository).findById(existingAddress.getAddressId());
        verify(addressEntityRepository).delete(existingAddress);
    }
}